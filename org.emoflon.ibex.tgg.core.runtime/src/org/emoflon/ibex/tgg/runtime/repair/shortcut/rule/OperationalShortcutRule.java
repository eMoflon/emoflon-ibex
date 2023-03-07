package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

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
import org.emoflon.ibex.tgg.compiler.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.analysis.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.patterns.GreenPatternFactory;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.SearchPlanCreator;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

import language.LanguageFactory;

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
			createOldRuleApplicationNodes(hoRule);
		else
			createOldRuleApplicationNode();

		this.searchPlanCreator = new SearchPlanCreator(options, this);
	}

	abstract protected void operationalize();

	abstract public PatternType getType();

	private void createOldRuleApplicationNode() {
		String originalRuleName = operationalizedSCR.getOriginalRule().getName();

		TGGNode oldRaNode = IBeXTGGModelFactory.eINSTANCE.createTGGNode();
		oldRaNode.setName(getProtocolNodeName(originalRuleName));
		EClass oldRaType = (EClass) options.tgg.corrMetamodel().getEClassifier(getMarkerTypeName(originalRuleName));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);

		createRuleApplicationLinks(oldRaNode);

		operationalizedSCR.getNodes().add(oldRaNode);
	}

	private void createRuleApplicationLinks(TGGNode oldRaNode) {
		for (TGGNode node : operationalizedSCR.getOriginalRule().getNodes())
			createRuleApplicationEdge(oldRaNode, node);
	}

	private void createRuleApplicationEdge(TGGNode raNode, TGGNode node) {
		TGGNode scNode = operationalizedSCR.mapRuleNodeToSCNode(node, SCInputRule.ORIGINAL);
		if (scNode == null || !operationalizedSCR.getNodes().contains(scNode))
			return;

		EReference ref = getProtocolRef(raNode, node);
		TGGEdge edge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + scNode.getName());
		edge.setType(ref);
		edge.setBindingType(BindingType.DELETE);
		edge.setDomainType(node.getDomainType());
		edge.setSource(raNode);
		edge.setTarget(scNode);

		operationalizedSCR.getEdges().add(edge);
	}

	private EReference getProtocolRef(TGGNode protocolNode, TGGNode node) {
		String markerRefName = getMarkerRefName(node.getBindingType(), node.getDomainType(), node.getName());
		return (EReference) protocolNode.getType().getEStructuralFeature(markerRefName);
	}

	private void createOldRuleApplicationNodes(HigherOrderTGGRule originalHoRule) {
		for (HigherOrderRuleComponent component : originalHoRule.getComponents())
			createOldRuleApplicationNode(component);
	}

	private void createOldRuleApplicationNode(HigherOrderRuleComponent component) {
		String ruleName = component.rule.getName();

		TGGNode oldRaNode = IBeXTGGModelFactory.eINSTANCE.createTGGNode();
		oldRaNode.setName(getProtocolNodeName(ruleName) + component.id);
		EClass oldRaType = (EClass) options.tgg.corrMetamodel().getEClassifier(getMarkerTypeName(ruleName));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);

		createRuleApplicationLinks(component, oldRaNode);

		operationalizedSCR.getNodes().add(oldRaNode);
	}

	private void createRuleApplicationLinks(HigherOrderRuleComponent component, TGGNode oldRaNode) {
		for (TGGNode node : component.rule.getNodes())
			createRuleApplicationEdge(component, oldRaNode, node);
	}

	private void createRuleApplicationEdge(HigherOrderRuleComponent component, TGGNode raNode, TGGNode node) {
		TGGNode higherOrderNode = HigherOrderSupport.getHigherOrderElement(component, node);

		TGGNode scNode = operationalizedSCR.mapRuleNodeToSCNode(higherOrderNode, SCInputRule.ORIGINAL);
		if (scNode == null || !operationalizedSCR.getNodes().contains(scNode))
			return;

		EReference ref = getProtocolRef(raNode, node);
		TGGEdge edge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + scNode.getName());
		edge.setType(ref);
		edge.setBindingType(BindingType.DELETE);
		edge.setDomainType(higherOrderNode.getDomainType());
		edge.setSource(raNode);
		edge.setTarget(scNode);

		operationalizedSCR.getEdges().add(edge);
	}

	protected void createFilterNacs(TGGRule targetRule, DomainType domain) {
		Collection<FilterNACCandidate> decCandidates = filterNACAnalysis.computeFilterNACCandidates(targetRule, domain);
		for (FilterNACCandidate dec : decCandidates) {
			TGGNode decNode = operationalizedSCR.mapRuleNodeToSCNode(dec.getNodeInRule(), SCInputRule.REPLACING);
			TGGEdge edge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
			edge.setType(dec.getEdgeType());
			edge.setBindingType(BindingType.NEGATIVE);
			edge.setDomainType(decNode.getDomainType());

			TGGNode node = IBeXTGGModelFactory.eINSTANCE.createTGGNode();
			node.setName("decNode");
			node.setDomainType(decNode.getDomainType());
			node.setType(dec.getOtherNodeType());
			node.setBindingType(BindingType.NEGATIVE);

			switch (dec.getEDirection()) {
				case INCOMING -> {
					edge.setSource(node);
					edge.setTarget(decNode);
				}
				case OUTGOING -> {
					edge.setSource(decNode);
					edge.setTarget(node);
				}
			}

			edge.setName(edge.getSource().getName() + "__" + dec.getEdgeType().getName() + "__" + edge.getTarget().getName());

			operationalizedSCR.getNodes().add(node);
			operationalizedSCR.getEdges().add(edge);
		}
	}

	protected void transformNodes(Collection<TGGNode> filteredNodes, BindingType target) {
		filteredNodes.forEach(n -> n.setBindingType(target));
	}

	protected void transformEdges(Collection<TGGEdge> filteredEdges, BindingType target) {
		filteredEdges.forEach(e -> e.setBindingType(target));
	}

	protected void transformInterfaceEdges(Collection<TGGEdge> filteredEdges, BindingType target) {
		markedElements.addAll(filteredEdges.stream()
				.filter(e -> operationalizedSCR.getPreservedNodes().contains(e.getSource()) ^ operationalizedSCR.getPreservedNodes().contains(e.getTarget()))
				.collect(Collectors.toList()));

		for (TGGEdge edge : filteredEdges) {
			if (!operationalizedSCR.getPreservedNodes().contains(edge.getSource()) && !operationalizedSCR.getPreservedNodes().contains(edge.getTarget()))
				continue;

			BindingType srcNodeBinding = ((TGGNode) edge.getSource()).getBindingType();
			BindingType trgNodeBinding = ((TGGNode) edge.getTarget()).getBindingType();
			if ((srcNodeBinding != BindingType.CONTEXT && srcNodeBinding != BindingType.RELAXED)
					|| (trgNodeBinding != BindingType.CONTEXT && trgNodeBinding != BindingType.RELAXED))
				continue;

			edge.setBindingType(target);
		}
	}

	// TODO lfritsche: delete -> nac?
	protected void removeNodes(Collection<TGGNode> filterNodes) {
		filterNodes.iterator().forEachRemaining(f -> {
			operationalizedSCR.getEdges().removeAll(f.getIncomingEdges());
			operationalizedSCR.getEdges().removeAll(f.getOutgoingEdges());
			f.getIncomingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			f.getOutgoingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			EcoreUtil.delete(f);
		});
		operationalizedSCR.getNodes().removeAll(filterNodes);
	}

	protected void removeEdges(Collection<TGGEdge> filterEdges) {
		filterEdges.iterator().forEachRemaining(EcoreUtil::delete);
	}

	protected void addNACforCreatedInterface(Collection<TGGEdge> edges) {
		for (TGGEdge edge : edges) {
			TGGNode src = (TGGNode) edge.getSource();
			TGGNode trg = (TGGNode) edge.getTarget();

			if (operationalizedSCR.getPreservedNodes().contains(src) ^ operationalizedSCR.getPreservedNodes().contains(trg)) {
				if (edge.getBindingType() != BindingType.CREATE)
					continue;

				if (src.getBindingType() == BindingType.CREATE || trg.getBindingType() == BindingType.CREATE)
					continue;

				TGGEdge nac = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
				nac.setDomainType(edge.getDomainType());
				nac.setBindingType(BindingType.NEGATIVE);
				nac.setType(edge.getType());
				nac.setSource(edge.getSource());
				nac.setTarget(edge.getTarget());
				nac.setName(edge.getSource().getName() + "__" + edge.getType().getName() + "__" + edge.getTarget().getName());
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
		IGreenPatternFactory greenPatternFactory;
		if (operationalizedSCR.getReplacingRule() instanceof HigherOrderTGGRule hoRule)
			greenPatternFactory = new GreenPatternFactory(options, hoRule);
		else
			greenPatternFactory = options.patterns.greenPatternFactories().get(operationalizedSCR.getReplacingRule().getName());

		return new GreenSCPattern(greenPatternFactory, this);
	}

	@Override
	public String toString() {
		return getType() + operationalizedSCR.toString();
	}
}
