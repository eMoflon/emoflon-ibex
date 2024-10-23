package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType.CONTEXT;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXRuleDelta;
import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.analysis.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.SearchPlan;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.SearchPlanCreator;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.ShortcutResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

/**
 * 
 * This class represents an operationalized shortcut rule. The pattern
 * information are stored in the shortcut rule (copied instance).
 * Operationalization means that these rules are applicable in a certain
 * translation direction (FORWARD, BACKWARD). However, the operationalize()
 * method has to be implemented by sub classes. This class also creates a
 * SearchPlan for the operationalized pattern and created Lookup and Check
 * Operations which are used by LocalPatternSearch to execute the SearchPlan.
 * The interfaces used to implement these operations are EdgeCheck, Lookup,
 * NACNodeCheck and NodeCheck.
 * 
 * @author lfritsche
 *
 */
public abstract class OperationalShortcutRule {
	protected final static Logger logger = Logger.getLogger(OperationalShortcutRule.class);

	protected final IbexOptions options;
	protected RuntimeShortcutRule rawShortcutRule;
	protected RuntimeShortcutRule operationalizedSCR;
	protected ACAnalysis filterNACAnalysis;

	protected SearchPlanCreator searchPlanCreator;

	public OperationalShortcutRule(IbexOptions options, IGreenInterpreter greenInterpreter, RuntimeShortcutRule shortcutRule, //
			ACAnalysis filterNACAnalysis, ShortcutResourceHandler scResourceHandler) {
		this.options = options;
		this.rawShortcutRule = shortcutRule;
		this.operationalizedSCR = shortcutRule.copy();
		this.operationalizedSCR.getShortcutRule().setName(this.operationalizedSCR.getShortcutRule().getName() + "_" + getOperationalisationMode());

		scResourceHandler.add(operationalizedSCR.getShortcutRule());
		initMarkerSets();
		this.filterNACAnalysis = filterNACAnalysis;

		operationalize();
		TGGRuleDerivedFieldsTool.fillDerivedTGGRuleFields(operationalizedSCR.getShortcutRule());
		convertAttributeAssignments();
		rawShortcutRule.getShortcutRule().getOperationalisations().add(operationalizedSCR.getShortcutRule());
		operationalizedSCR.getShortcutRule().setOperationalisationMode(getOperationalisationMode());

		if (operationalizedSCR.getOriginalRule() instanceof HigherOrderTGGRule hoRule)
			createOldRuleApplicationNodes(hoRule);
		else
			createOldRuleApplicationNode();

		this.searchPlanCreator = new SearchPlanCreator(options, greenInterpreter, this);
	}

	private void initMarkerSets() {
		IBeXRuleDelta toBeMarkedDelta = IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta();
		operationalizedSCR.getShortcutRule().setToBeMarked(toBeMarkedDelta);
		IBeXRuleDelta alreadyMarkedDelta = IBeXCoreModelFactory.eINSTANCE.createIBeXRuleDelta();
		operationalizedSCR.getShortcutRule().setAlreadyMarked(alreadyMarkedDelta);
	}

	abstract protected void operationalize();

	abstract public PatternType getType();

	abstract protected OperationalisationMode getOperationalisationMode();

	private void convertAttributeAssignments() {
		for (var contextNode : TGGModelUtils.getNodesByOperator(operationalizedSCR.getShortcutRule(), CONTEXT)) {
			if (skipConversion(contextNode))
				continue;

			operationalizedSCR.getShortcutRule().getPrecondition().getConditions().addAll( //
					contextNode.getAttributeAssignments().stream() //
							.map(a -> TGGModelUtils.transformAttributeAssignmentToAttributeCondition(a)) //
							.toList() //
			);
		}
	}

	private boolean skipConversion(TGGNode node) {
		if (operationalizedSCR.getPreservedNodes().contains(node)) {
			return switch (node.getDomainType()) {
			case SOURCE -> getType() == PatternType.BWD;
			case TARGET -> getType() == PatternType.FWD;
			default -> false;
			};
		}
		return false;
	}

	private void createOldRuleApplicationNode() {
		String originalRuleName = operationalizedSCR.getOriginalRule().getName();

		TGGNode oldRaNode = IBeXTGGModelFactory.eINSTANCE.createTGGNode();
		oldRaNode.setName(getProtocolNodeName());
		EClass oldRaType = (EClass) options.tgg.corrMetamodel().getEClassifier(getMarkerTypeName(originalRuleName));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);
		TGGModelUtils.setOperationType(oldRaNode, BindingType.DELETE);

		createRuleApplicationLinks(oldRaNode);

		operationalizedSCR.getNodes().add(oldRaNode);
		operationalizedSCR.getShortcutRule().getAllNodes().add(oldRaNode);
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
		TGGModelUtils.setOperationType(edge, BindingType.DELETE);
		edge.setDomainType(node.getDomainType());
		edge.setSource(raNode);
		edge.setTarget(scNode);

		operationalizedSCR.getEdges().add(edge);
		operationalizedSCR.getShortcutRule().getAllEdges().add(edge);
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
		oldRaNode.setName(getProtocolNodeName() + component.id);
		EClass oldRaType = (EClass) options.tgg.corrMetamodel().getEClassifier(getMarkerTypeName(ruleName));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);
		TGGModelUtils.setOperationType(oldRaNode, BindingType.DELETE);

		createRuleApplicationLinks(component, oldRaNode);

		operationalizedSCR.getNodes().add(oldRaNode);
		operationalizedSCR.getShortcutRule().getAllNodes().add(oldRaNode);
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
		TGGModelUtils.setOperationType(edge, BindingType.DELETE);
		edge.setDomainType(higherOrderNode.getDomainType());
		edge.setSource(raNode);
		edge.setTarget(scNode);

		operationalizedSCR.getEdges().add(edge);
		operationalizedSCR.getShortcutRule().getAllEdges().add(edge);
	}

	protected void createFilterNacs(TGGRule targetRule, DomainType domain) {
		// TODO larsF: application conditions for shortcut rules must be reworked
		// (filterNCAs)s
		Collection<FilterNACCandidate> decCandidates = filterNACAnalysis.computeFilterNACCandidates(targetRule, domain);
		for (FilterNACCandidate dec : decCandidates) {
			TGGNode decNode = operationalizedSCR.mapRuleNodeToSCNode(dec.getNodeInRule(), SCInputRule.REPLACING);
			TGGEdge edge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
			edge.setType(dec.getEdgeType());
			edge.setBindingType(BindingType.NEGATIVE);
			TGGModelUtils.setOperationType(edge, BindingType.NEGATIVE);
			edge.setDomainType(decNode.getDomainType());

			TGGNode node = IBeXTGGModelFactory.eINSTANCE.createTGGNode();
			node.setName("decNode");
			node.setDomainType(decNode.getDomainType());
			node.setType(dec.getOtherNodeType());
			node.setBindingType(BindingType.NEGATIVE);
			TGGModelUtils.setOperationType(node, BindingType.NEGATIVE);

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
			operationalizedSCR.getShortcutRule().getAllNodes().add(node);
			operationalizedSCR.getEdges().add(edge);
			operationalizedSCR.getShortcutRule().getAllEdges().add(edge);
		}
	}

	protected void transformNodes(Collection<TGGNode> filteredNodes, BindingType target) {
		filteredNodes.forEach(n -> {
			n.setBindingType(target);
			TGGModelUtils.setOperationType(n, target);
		});
	}

	protected void transformEdges(Collection<TGGEdge> filteredEdges, BindingType target) {
		filteredEdges.forEach(e -> {
			e.setBindingType(target);
			TGGModelUtils.setOperationType(e, target);
		});
	}

	protected void transformInterfaceEdges(Collection<TGGEdge> filteredEdges, BindingType target) {
		operationalizedSCR.getShortcutRule().getToBeMarked().getEdges().addAll(filteredEdges.stream() //
				.filter(e -> operationalizedSCR.getPreservedNodes().contains(e.getSource()) //
						^ operationalizedSCR.getPreservedNodes().contains(e.getTarget())) //
				.collect(Collectors.toList()));

		for (TGGEdge edge : filteredEdges) {
			if (!operationalizedSCR.getPreservedNodes().contains(edge.getSource()) && !operationalizedSCR.getPreservedNodes().contains(edge.getTarget()))
				continue;

			BindingType srcNodeBinding = ((TGGNode) edge.getSource()).getBindingType();
			BindingType trgNodeBinding = ((TGGNode) edge.getTarget()).getBindingType();
			if ((srcNodeBinding != BindingType.CONTEXT && srcNodeBinding != BindingType.RELAXED) || (trgNodeBinding != BindingType.CONTEXT && trgNodeBinding != BindingType.RELAXED))
				continue;

			edge.setBindingType(target);
			TGGModelUtils.setOperationType(edge, target);
		}
	}

	// TODO lfritsche: delete -> nac?
	protected void removeNodes(Collection<TGGNode> filterNodes) {
		Collection<IBeXEdge> edges = new HashSet<>();
		Collection<TGGCorrespondence> correspondences = new HashSet<>();
		filterNodes.forEach(n -> {
			edges.addAll(n.getIncomingEdges());
			edges.addAll(n.getOutgoingEdges());
//			correspondences.addAll(n.getIncomingCorrespondence());
//			correspondences.addAll(n.getOutgoingCorrespondence());
		});
		removeEdges(edges);
//		removeCorrespondences(correspondences);
		filterNodes.forEach(n -> {
			TGGRule eContainer = (TGGRule) n.eContainer();
			eContainer.getAllNodes().remove(n);
			eContainer.getNodes().remove(n);
		});
	}

	protected void removeCorrespondences(Collection<TGGCorrespondence> correspondences) {
		for (var correspondence : correspondences) {
			correspondence.setSource(null);
			correspondence.setTarget(null);

			TGGRule eContainer = (TGGRule) correspondence.eContainer();
			eContainer.getCorrespondenceNodes().remove(correspondence);
		}
	}

	protected void removeEdges(Collection<? extends IBeXEdge> filterEdges) {
		for (var edge : filterEdges) {
			edge.setSource(null);
			edge.setTarget(null);

			TGGRule eContainer = (TGGRule) edge.eContainer();
			eContainer.getAllEdges().remove(edge);
			eContainer.getEdges().remove(edge);
		}
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
				TGGModelUtils.setOperationType(nac, BindingType.NEGATIVE);
				nac.setType(edge.getType());
				nac.setSource(edge.getSource());
				nac.setTarget(edge.getTarget());
				nac.setName(edge.getSource().getName() + "__" + edge.getType().getName() + "__" + edge.getTarget().getName());
				operationalizedSCR.getEdges().add(nac);
			}
		}
	}

	protected void removeDomainDependentRuleComponents(DomainType domain) {
		TGGShortcutRule shortcutRule = operationalizedSCR.getShortcutRule();

		TGGModelUtils.removeInvocations(shortcutRule.getPrecondition().getInvocations(), domain);

		TGGModelUtils.removeAttributeAssignments(shortcutRule.getAttributeAssignments(), domain);
		TGGModelUtils.removeAttributeConditions(shortcutRule.getPrecondition().getConditions(), domain);

		TGGModelUtils.removeAttributeConstraints(shortcutRule.getAttributeConstraints(), domain);
		TGGModelUtils.removeAttributeConstraints(((TGGPattern) shortcutRule.getPrecondition()).getAttributeConstraints(), domain);
	}

	/**
	 * This method transforms constraint parameters of the chosen domain to be
	 * derived so that they are calculcated anew
	 * 
	 * @param rule
	 * @param domain
	 */
	protected void transformAttributeConstraintBindings(RuntimeShortcutRule runtimeSCRule, DomainType domain) {
		var preservedNodeNames = runtimeSCRule.getPreservedNodes().stream() //
				.filter(n -> n.getDomainType() == domain) //
				.map(TGGNode::getName) //
				.collect(Collectors.toSet());

		var rule = runtimeSCRule.getShortcutRule();
		for (var parameter : rule.getAttributeConstraints().getParameters()) {
			if (parameter.getExpression() instanceof IBeXAttributeValue attributeValue) {
				if (attributeValue.getNode() instanceof TGGNode tggNode) {
					if (preservedNodeNames.contains(tggNode.getName())) {
						parameter.setDerived(true);
					}
				} else
					throw new IllegalStateException("Attributeconstraints are only allowed to reference TGGNodes but detected " + attributeValue.getNode());
			}
		}
	}

	public SearchPlan createSearchPlan() {
		return searchPlanCreator.createSearchPlan();
	}

	public RuntimeShortcutRule getRawShortcutRule() {
		return rawShortcutRule;
	}

	public RuntimeShortcutRule getOperationalizedSCR() {
		return operationalizedSCR;
	}

	public String getName() {
		return getType() + "_" + operationalizedSCR.getName();
	}

	@Override
	public String toString() {
		return getType() + operationalizedSCR.toString();
	}

}
