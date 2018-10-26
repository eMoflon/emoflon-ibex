package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;
import language.TGGParamValue;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

/**
 * 
 * This implementation of IGreenPattern handles the information of which nodes and edges are to be created by applying 
 * the also stored operationalized shortcut rule.
 * 
 * @author lfritsche
 *
 */
public class GreenSCPattern implements IGreenPattern {

	private OperationalShortcutRule oscRule;
	private OperationalStrategy strategy;
	private IGreenPatternFactory factory;
	
	
	public GreenSCPattern(IGreenPatternFactory factory, OperationalShortcutRule oscRule) {
		this.oscRule = oscRule;
		this.factory = factory;
		this.strategy = factory.getStrategy();
	}
	
	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleNode> getTrgNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.CORR, BindingType.CREATE).stream().map(n -> (TGGRuleCorr) n).collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return TGGCollectionUtil.filterNodes(oscRule.markedElements);
	}

	@Override
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return TGGCollectionUtil.filterEdges(oscRule.markedElements).stream().filter(e -> e.getBindingType() == null).collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		try {
			List<TGGAttributeConstraint> sortedConstraints = sortConstraints(factory.getAttributeCSPVariables(), factory.getAttributeConstraints());
			for(TGGParamValue pVal : factory.getAttributeCSPVariables()) {
				if(pVal instanceof TGGAttributeExpression) {
					TGGAttributeExpression attrExpr = (TGGAttributeExpression) pVal;
					attrExpr.setObjectVar(oscRule.scRule.mapTrgToSCNodeNode(attrExpr.getObjectVar().getName()));
				}
			}
			
			return new RuntimeTGGAttributeConstraintContainer(
					factory.getAttributeCSPVariables(), 
					sortedConstraints,
					match,
					factory.getOptions().constraintProvider());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sort attribute constraints for " + match.getPatternName() + ", " + e.getMessage(), e);
		}
	}

	@Override
	public boolean isToBeIgnored(IMatch match) {
		return false;
	}

	@Override
	public void createMarkers(String ruleName, IMatch match) {
		String newRAName = getProtocolNodeName(oscRule.getScRule().getTargetRule().getName());
		EObject ra = (EObject) match.get(newRAName);
		EClass type = (EClass) strategy.getOptions().getCorrMetamodel().getEClassifier(getMarkerTypeName(oscRule.getScRule().getTargetRule().getName()));

		Stream<TGGRuleNode> greenSrcNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.SRC, BindingType.CREATE).stream();
		Stream<TGGRuleNode> greenTrgNodes =	oscRule.getScRule().getTargetRuleMappings(DomainType.TRG, BindingType.CREATE).stream();
		Stream<TGGRuleNode> greenCorrNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.CORR, BindingType.CREATE).stream();
		
		greenSrcNodes.forEach(n -> addToProtocol(ra, n, match, type));
		greenTrgNodes.forEach(n -> addToProtocol(ra, n, match, type));
		greenCorrNodes.forEach(n -> addToProtocol(ra, n, match, type));
		
		Collection<TGGRuleNode> blackSrcNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.SRC, BindingType.CONTEXT);
		Collection<TGGRuleNode> blackTrgNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.TRG, BindingType.CONTEXT);
		
		blackSrcNodes.stream().forEach(n -> addToProtocol(ra, n, match, type));
		blackTrgNodes.stream().forEach(n -> addToProtocol(ra, n, match, type));
		
//		match.getParameterNames().stream()
//			.filter(n -> !IbexBasePattern.isAttrNode(n))
//			.forEach(n -> ra.getNodeMappings().put(n, (EObject) match.get(n)));

		strategy.setIsRuleApplicationFinal(ra);
		match.put(getProtocolNodeName(oscRule.getScRule().getTargetRule().getName()), ra);
	}
	
	private void addToProtocol(EObject prot, TGGRuleNode n, IMatch match, EClass protType) {
		EReference ref = getProtocolRef(protType, n.getBindingType(), n.getDomainType(), n);
		((List<EObject>) prot.eGet(ref)).add((EObject) match.get(n.getName()));
	}

	protected List<TGGAttributeConstraint> sortConstraints(List<TGGParamValue> variables, List<TGGAttributeConstraint> constraints) {
		SearchPlanAction spa = new SearchPlanAction(variables, constraints, false, getSrcTrgNodesCreatedByPattern());
		return spa.sortConstraints();
	}
	
	private EReference getProtocolRef(EClass protocolType, BindingType bType, DomainType dType, TGGRuleNode node) {
		return (EReference) protocolType.getEStructuralFeature(getMarkerRefName(bType, dType, node.getName()));
	}
	
	private EReference getProtocolRef(TGGRuleNode protocolNode, BindingType bType, DomainType dType, TGGRuleNode node) {
		return (EReference) protocolNode.getType().getEStructuralFeature(getMarkerRefName(bType, dType, node.getName()));
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TGGRuleEdge> getCorrEdges() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.CORR, BindingType.CREATE);
	}
}
