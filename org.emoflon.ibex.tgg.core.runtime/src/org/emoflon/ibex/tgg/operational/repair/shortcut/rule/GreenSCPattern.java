package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.patterns.IbexGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintLibrary;
import language.TGGAttributeExpression;
import language.TGGParamValue;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

/**
 * 
 * This implementation of IGreenPattern handles the information of which nodes and edges are to be created by applying 
 * the also stored operationalized shortcut rule.
 * 
 * @author lfritsche
 *
 */
public class GreenSCPattern extends IbexGreenPattern {

	private OperationalShortcutRule oscRule;
	private IGreenPatternFactory factory;
	
	
	public GreenSCPattern(IGreenPatternFactory factory, OperationalShortcutRule oscRule) {
		super(factory);
		this.oscRule = oscRule;
		this.factory = factory;
	}
	
	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return TGGFilterUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleNode> getTrgNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return TGGFilterUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.CORR, BindingType.CREATE).stream().map(n -> (TGGRuleCorr) n).collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return TGGFilterUtil.filterNodes(oscRule.markedElements);
	}

	@Override
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return TGGFilterUtil.filterEdges(oscRule.markedElements).stream().filter(e -> e.getBindingType() == null).collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match) {
//		try {
			TGGAttributeConstraintLibrary newLibrary = EcoreUtil.copy(factory.getAttributeLibrary());
			
			List<TGGParamValue> newAttributeCSPVariables = newLibrary.getParameterValues();
			List<TGGAttributeConstraint> newAttributeConstraints = newLibrary.getTggAttributeConstraints();
			List<TGGAttributeConstraint> sortedConstraints = sortConstraints(newAttributeCSPVariables, newAttributeConstraints);
			for(TGGParamValue pVal : newAttributeCSPVariables) {
				if(pVal instanceof TGGAttributeExpression) {
					TGGAttributeExpression attrExpr = (TGGAttributeExpression) pVal;
					attrExpr.setObjectVar(oscRule.scRule.mapTrgToSCNodeNode(attrExpr.getObjectVar().getName()));
				}
			}
			
			return new RuntimeTGGAttributeConstraintContainer(
					newAttributeCSPVariables, 
					sortedConstraints,
					match,
					factory.getOptions().constraintProvider());
	}

	@Override
	public boolean isToBeIgnored(ITGGMatch match) {
		return false;
	}

	protected List<TGGAttributeConstraint> sortConstraints(List<TGGParamValue> variables, List<TGGAttributeConstraint> constraints) {
		SearchPlanAction spa = new SearchPlanAction(variables, constraints, false, getSrcTrgNodesCreatedByPattern());
		return spa.sortConstraints();
	}
	
	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return null;
	}

	@Override
	public Collection<TGGRuleEdge> getCorrEdges() {
		return TGGFilterUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.CORR, BindingType.CREATE);
	}
}
