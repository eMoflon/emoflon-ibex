package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlanCreator;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class represents an operationalized shortcut rule. The pattern information are
 * stored in the shortcut rule (copied instance). Operationalization means that these
 * rules are applicable in a certain translation direction (FORWARD, BACKWARD). However,
 * the operationalize() method has to be implemented by sub classes. This class also
 * creates a SearchPlan for the operationalized pattern and created Lookup and Check
 * Operations which are used by LocalPatternSearch to execute the SearchPlan. The
 * interfaces used to implement these operations are EdgeCheck, Lookup, NACNodeCheck and
 * NodeCheck.
 * 
 * @author lfritsche
 *
 */
public abstract class OperationalShortcutRule {
	protected final static Logger logger = Logger.getLogger(OperationalShortcutRule.class);

	protected PropagatingOperationalStrategy strategy;
	protected ShortcutRule originalScRule;
	protected ShortcutRule opScRule;
	protected ACAnalysis filterNACAnalysis;

	protected SearchPlanCreator searchPlanCreator;

	protected Collection<TGGRuleElement> markedElements;

	private IGreenPattern greenPattern;

	public OperationalShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		this.strategy = strategy;
		this.originalScRule = scRule;
		this.opScRule = scRule.copy();
		this.filterNACAnalysis = filterNACAnalysis;

		this.markedElements = new HashSet<>();
		operationalize();
		createRuleApplicationNode();

		this.searchPlanCreator = new SearchPlanCreator(strategy, this);
	}

	abstract protected void operationalize();

	abstract public PatternType getType();

	private void createRuleApplicationNode() {
		TGGRuleNode oldRaNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		oldRaNode.setName(getProtocolNodeName(opScRule.getOriginalRule().getName()));
		EClass oldRaType = (EClass) strategy.getOptions().tgg.corrMetamodel() //
				.getEClassifier(getMarkerTypeName(this.opScRule.getOriginalRule().getName()));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);

		BiFunction<TGGRuleNode, TGGRuleNode, EReference> createSrcRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.SRC, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference> createCorrRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.CORR, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference> createTrgRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.TRG, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference> contextSrcRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.SRC, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference> contextCorrRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.CORR, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference> contextTrgRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.TRG, n);

		createRuleApplicationLink(createSrcRef, contextSrcRef, oldRaNode, DomainType.SRC);
		createRuleApplicationLink(createCorrRef, contextCorrRef, oldRaNode, DomainType.CORR);
		createRuleApplicationLink(createTrgRef, contextTrgRef, oldRaNode, DomainType.TRG);

		opScRule.getNodes().add(oldRaNode);
	}

	private EReference getProtocolRef(TGGRuleNode protocolNode, BindingType bType, DomainType dType, TGGRuleNode node) {
		return (EReference) protocolNode.getType().getEStructuralFeature(getMarkerRefName(bType, dType, node.getName()));
	}

	private void createRuleApplicationLink(BiFunction<TGGRuleNode, TGGRuleNode, EReference> createdRef,
			BiFunction<TGGRuleNode, TGGRuleNode, EReference> contextRef, TGGRuleNode oldRaNode, DomainType dType) {
		TGGOverlap overlap = opScRule.getOverlap();
		Stream<TGGRuleNode> deletedNodes = TGGFilterUtil.filterNodes(TGGFilterUtil.filterNodes(overlap.deletions), dType).stream();
		Stream<TGGRuleNode> sourceRuleUnboundContextNodes = TGGFilterUtil.filterNodes( //
				TGGFilterUtil.filterNodes(overlap.unboundOriginalContext), dType).stream() //
				.filter(n -> opScRule.getOriginalRule().getNodes().contains(n));
		Stream<TGGRuleNode> sourceRuleCreatedMappingNodeKeys = TGGFilterUtil.filterNodes( //
				TGGFilterUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CREATE).stream();
		Stream<TGGRuleNode> sourceRuleContextMappingNodeKeys = TGGFilterUtil.filterNodes( //
				TGGFilterUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CONTEXT).stream();

		Function<TGGRuleNode, TGGRuleNode> srcToSCNode = n -> opScRule.mapRuleNodeToSCRuleNode(n, SCInputRule.ORIGINAL);

		if (createdRef != null) {
			deletedNodes.forEach(n -> createRuleApplicationEdge(createdRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
			sourceRuleCreatedMappingNodeKeys.forEach( //
					n -> createRuleApplicationEdge(createdRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
		}
		if (contextRef != null) {
			sourceRuleUnboundContextNodes.forEach( //
					n -> createRuleApplicationEdge(contextRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
			sourceRuleContextMappingNodeKeys.forEach( //
					n -> createRuleApplicationEdge(contextRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
		}
	}

	private void createRuleApplicationEdge(EReference ref, TGGRuleNode raNode, TGGRuleNode node, BindingType bType, TGGRuleNode scNode) {
		if (!opScRule.getNodes().contains(scNode))
			return;

		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + scNode.getName());
		edge.setType(ref);
		edge.setBindingType(bType);
		edge.setDomainType(node.getDomainType());
		edge.setSrcNode(raNode);
		edge.setTrgNode(scNode);

		opScRule.getEdges().add(edge);
	}

	protected void createFilterNacs(TGGRule targetRule, DomainType domain) {
		Collection<FilterNACCandidate> decCandidates = filterNACAnalysis.computeFilterNACCandidates(targetRule, domain);
		for (FilterNACCandidate dec : decCandidates) {
			TGGRuleNode decNode = opScRule.mapRuleNodeToSCRuleNode(dec.getNodeInRule(), SCInputRule.REPLACING);
			TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			edge.setType(dec.getEdgeType());
			edge.setBindingType(BindingType.NEGATIVE);
			edge.setDomainType(decNode.getDomainType());

			TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
			node.setName("decNode");
			node.setDomainType(decNode.getDomainType());
			node.setType(dec.getOtherNodeType());
			node.setBindingType(BindingType.NEGATIVE);

			switch (dec.getEDirection()) {
				case INCOMING -> {
					edge.setSrcNode(node);
					edge.setTrgNode(decNode);
				}
				case OUTGOING -> {
					edge.setSrcNode(decNode);
					edge.setTrgNode(node);
				}
			}

			edge.setName(edge.getSrcNode().getName() + "__" + dec.getEdgeType().getName() + "__" + edge.getTrgNode().getName());

			opScRule.getNodes().add(node);
			opScRule.getEdges().add(edge);
		}
	}

	protected void transformNodes(Collection<TGGRuleNode> filterNodes, BindingType target) {
		filterNodes.stream().forEach(n -> n.setBindingType(target));
	}

	protected void transformEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		filteredEdges.forEach(e -> e.setBindingType(target));
	}

	protected void transformInterfaceEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		markedElements.addAll(filteredEdges.stream()
				.filter(e -> opScRule.getPreservedNodes().contains(e.getSrcNode()) ^ opScRule.getPreservedNodes().contains(e.getTrgNode()))
				.collect(Collectors.toList()));

		for (TGGRuleEdge edge : filteredEdges) {
			if (!opScRule.getPreservedNodes().contains(edge.getSrcNode()) && !opScRule.getPreservedNodes().contains(edge.getTrgNode()))
				continue;

			BindingType srcNodeBinding = edge.getSrcNode().getBindingType();
			BindingType trgNodeBinding = edge.getTrgNode().getBindingType();
			if ((srcNodeBinding != BindingType.CONTEXT && srcNodeBinding != BindingType.RELAXED)
					|| (trgNodeBinding != BindingType.CONTEXT && trgNodeBinding != BindingType.RELAXED))
				continue;

			edge.setBindingType(target);
		}
	}

	// TODO lfritsche: delete -> nac?
	protected void removeNodes(Collection<TGGRuleNode> filterNodes) {
		filterNodes.iterator().forEachRemaining(f -> {
			opScRule.getEdges().removeAll(f.getIncomingEdges());
			opScRule.getEdges().removeAll(f.getOutgoingEdges());
			f.getIncomingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			f.getOutgoingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			EcoreUtil.delete(f);
		});
		opScRule.getNodes().removeAll(filterNodes);
	}

	protected void removeEdges(Collection<TGGRuleEdge> filterEdges) {
		filterEdges.iterator().forEachRemaining(EcoreUtil::delete);
	}

	protected void addNACforCreatedInterface(Collection<TGGRuleEdge> edges) {
		for (TGGRuleEdge edge : edges) {
			TGGRuleNode src = edge.getSrcNode();
			TGGRuleNode trg = edge.getTrgNode();

			if (opScRule.getPreservedNodes().contains(src) ^ opScRule.getPreservedNodes().contains(trg)) {
				if (edge.getBindingType() != BindingType.CREATE)
					continue;

				if (src.getBindingType() == BindingType.CREATE || trg.getBindingType() == BindingType.CREATE)
					continue;

				TGGRuleEdge nac = LanguageFactory.eINSTANCE.createTGGRuleEdge();
				nac.setDomainType(edge.getDomainType());
				nac.setBindingType(BindingType.NEGATIVE);
				nac.setType(edge.getType());
				nac.setSrcNode(edge.getSrcNode());
				nac.setTrgNode(edge.getTrgNode());
				nac.setName(edge.getSrcNode().getName() + "__" + edge.getType().getName() + "__" + edge.getTrgNode().getName());
				opScRule.getEdges().add(nac);
			}
		}
	}

	public SearchPlan createSearchPlan() {
		return searchPlanCreator.createSearchPlan();
	}

	public ShortcutRule getOriginalScRule() {
		return originalScRule;
	}

	public ShortcutRule getOpScRule() {
		return opScRule;
	}

	public String getName() {
		return getType() + "_" + opScRule.getName();
	}

	public IGreenPattern getGreenPattern() {
		if (greenPattern == null) {
			greenPattern = createGreenPattern();
		}
		return greenPattern;
	}

	private IGreenPattern createGreenPattern() {
		return new GreenSCPattern(strategy.getGreenFactories().get(opScRule.getReplacingRule().getName()), this);
	}

	@Override
	public String toString() {
		return getType() + opScRule.toString();
	}
}
