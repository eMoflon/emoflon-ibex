package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.SearchPlanCreator;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * 
 * This class represents an operationalized shortcut rule. The pattern information are stored in the
 * shortcut rule (copied instance). Operationalization means that these rules are applicable in a
 * certain translation direction (FORWARD, BACKWARD). However, the operationalize() method has to be
 * implemented by sub classes. This class also creates a SearchPlan for the operationalized pattern
 * and created Lookup and Check Operations which are used by LocalPatternSearch to execute the
 * SearchPlan. The interfaces used to implement these operations are EdgeCheck, Lookup, NACNodeCheck
 * and NodeCheck.
 * 
 * @author lfritsche
 *
 */
public abstract class OperationalShortcutRule {
	protected final static Logger logger = Logger.getLogger(OperationalShortcutRule.class);

	protected final IbexOptions options;
	protected ShortcutRule rawShortcutRule;
	protected ShortcutRule operationalizedSCR;
	protected ACAnalysis filterNACAnalysis;

	protected SearchPlanCreator searchPlanCreator;

	protected Collection<TGGRuleElement> markedElements;

	private IGreenPattern greenPattern;

	public OperationalShortcutRule(IbexOptions options, ShortcutRule shortcutRule, ACAnalysis filterNACAnalysis) {
		this.options = options;
		this.rawShortcutRule = shortcutRule;
		this.operationalizedSCR = shortcutRule.copy();
		this.filterNACAnalysis = filterNACAnalysis;
		this.markedElements = new HashSet<>();

		operationalize();
		if (operationalizedSCR.getOriginalRule() instanceof HigherOrderTGGRule hoRule)
			createRuleApplicationNodes(hoRule);
		else
			createRuleApplicationNode();

		this.searchPlanCreator = new SearchPlanCreator(options, this);
	}

	abstract protected void operationalize();

	abstract public PatternType getType();

	private void createRuleApplicationNode() {
		String originalRuleName = operationalizedSCR.getOriginalRule().getName();

		TGGRuleNode oldRaNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		oldRaNode.setName(getProtocolNodeName(originalRuleName));
		EClass oldRaType = (EClass) options.tgg.corrMetamodel().getEClassifier(getMarkerTypeName(originalRuleName));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);

		createRuleApplicationLinks(oldRaNode);

		operationalizedSCR.getNodes().add(oldRaNode);
	}

	private void createRuleApplicationNodes(HigherOrderTGGRule hoRule) {
		// TODO
	}

	private void createRuleApplicationLinks(TGGRuleNode oldRaNode) {
		operationalizedSCR.getOriginalRule().getNodes().forEach(n -> createRuleApplicationEdge(oldRaNode, n));
	}

	private void createRuleApplicationEdge(TGGRuleNode raNode, TGGRuleNode node) {
		TGGRuleNode scNode = operationalizedSCR.mapRuleNodeToSCNode(node, SCInputRule.ORIGINAL);
		if (scNode == null || !operationalizedSCR.getNodes().contains(scNode))
			return;

		EReference ref = getProtocolRef(raNode, node);
		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + scNode.getName());
		edge.setType(ref);
		edge.setBindingType(BindingType.DELETE);
		edge.setDomainType(node.getDomainType());
		edge.setSrcNode(raNode);
		edge.setTrgNode(scNode);

		operationalizedSCR.getEdges().add(edge);
	}

	private EReference getProtocolRef(TGGRuleNode protocolNode, TGGRuleNode node) {
		String markerRefName = getMarkerRefName(node.getBindingType(), node.getDomainType(), node.getName());
		return (EReference) protocolNode.getType().getEStructuralFeature(markerRefName);
	}

	protected void createFilterNacs(TGGRule targetRule, DomainType domain) {
		Collection<FilterNACCandidate> decCandidates = filterNACAnalysis.computeFilterNACCandidates(targetRule, domain);
		for (FilterNACCandidate dec : decCandidates) {
			TGGRuleNode decNode = operationalizedSCR.mapRuleNodeToSCNode(dec.getNodeInRule(), SCInputRule.REPLACING);
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

			operationalizedSCR.getNodes().add(node);
			operationalizedSCR.getEdges().add(edge);
		}
	}

	protected void transformNodes(Collection<TGGRuleNode> filteredNodes, BindingType target) {
		filteredNodes.forEach(n -> n.setBindingType(target));
	}

	protected void transformEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		filteredEdges.forEach(e -> e.setBindingType(target));
	}

	protected void transformInterfaceEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		markedElements.addAll(filteredEdges.stream()
				.filter(e -> operationalizedSCR.getPreservedNodes().contains(e.getSrcNode()) ^ operationalizedSCR.getPreservedNodes().contains(e.getTrgNode()))
				.collect(Collectors.toList()));

		for (TGGRuleEdge edge : filteredEdges) {
			if (!operationalizedSCR.getPreservedNodes().contains(edge.getSrcNode()) && !operationalizedSCR.getPreservedNodes().contains(edge.getTrgNode()))
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
			operationalizedSCR.getEdges().removeAll(f.getIncomingEdges());
			operationalizedSCR.getEdges().removeAll(f.getOutgoingEdges());
			f.getIncomingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			f.getOutgoingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			EcoreUtil.delete(f);
		});
		operationalizedSCR.getNodes().removeAll(filterNodes);
	}

	protected void removeEdges(Collection<TGGRuleEdge> filterEdges) {
		filterEdges.iterator().forEachRemaining(EcoreUtil::delete);
	}

	protected void addNACforCreatedInterface(Collection<TGGRuleEdge> edges) {
		for (TGGRuleEdge edge : edges) {
			TGGRuleNode src = edge.getSrcNode();
			TGGRuleNode trg = edge.getTrgNode();

			if (operationalizedSCR.getPreservedNodes().contains(src) ^ operationalizedSCR.getPreservedNodes().contains(trg)) {
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
				operationalizedSCR.getEdges().add(nac);
			}
		}
	}

	public SearchPlan createSearchPlan() {
		return searchPlanCreator.createSearchPlan();
	}

	public ShortcutRule getRawShortcutRule() {
		return rawShortcutRule;
	}

	public ShortcutRule getOperationalizedSCR() {
		return operationalizedSCR;
	}

	public String getName() {
		return getType() + "_" + operationalizedSCR.getName();
	}

	public IGreenPattern getGreenPattern() {
		if (greenPattern == null) {
			greenPattern = createGreenPattern();
		}
		return greenPattern;
	}

	private IGreenPattern createGreenPattern() {
		return new GreenSCPattern(options.patterns.greenPatternFactories().get(operationalizedSCR.getReplacingRule().getName()), this);
	}

	@Override
	public String toString() {
		return getType() + operationalizedSCR.toString();
	}
}
