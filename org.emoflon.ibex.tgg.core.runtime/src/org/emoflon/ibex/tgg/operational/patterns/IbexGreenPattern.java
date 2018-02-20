package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import runtime.RuntimePackage;
import runtime.TGGRuleApplication;

public abstract class IbexGreenPattern implements IGreenPattern {
	protected IGreenPatternFactory factory;
	protected OperationalStrategy strategy;
	
	public IbexGreenPattern(GreenPatternFactory factory) {
		this.factory = factory;
		this.strategy = factory.getStrategy();
	}
	
	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		try {
			List<TGGAttributeConstraint> sortedConstraints = sortConstraints(factory.getAttributeCSPVariables(), factory.getAttributeConstraints());
			
			return new RuntimeTGGAttributeConstraintContainer(
					factory.getAttributeCSPVariables(), 
					sortedConstraints,
					match,
					factory.getOptions().constraintProvider());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sort attribute constraints for " + this.getClass().getName() + ", " + e.getMessage(), e);
		}
	}
	
	protected List<TGGAttributeConstraint> sortConstraints(List<TGGParamValue> variables, List<TGGAttributeConstraint> constraints) {
		SearchPlanAction spa = new SearchPlanAction(variables, constraints, false, getSrcTrgNodesCreatedByPattern());
		return spa.sortConstraints();
	}

	@Override
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return Collections.emptyList();
	}
	
	@Override
	public boolean isToBeIgnored(IMatch match) {
		return false;
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		RuntimePackage runtimePackage = RuntimePackage.eINSTANCE;

		TGGRuleApplication ra = (TGGRuleApplication) EcoreUtil.create(runtimePackage.getTGGRuleApplication());
		strategy.getProtocolResource().getContents().add(ra);

		ra.setName(ruleName);

		factory.getGreenSrcNodesInRule().forEach(n -> ra.getCreatedSrc().add((EObject) match.get(n.getName())));
		factory.getGreenTrgNodesInRule().forEach(n -> ra.getCreatedTrg().add((EObject) match.get(n.getName())));
		factory.getGreenCorrNodesInRule().forEach(n -> ra.getCreatedCorr().add((EObject) match.get(n.getName())));
		
		factory.getBlackSrcNodesInRule().forEach(n -> ra.getContextSrc().add((EObject) match.get(n.getName())));
		factory.getBlackTrgNodesInRule().forEach(n -> ra.getContextTrg().add((EObject) match.get(n.getName())));
		
		match.getParameterNames().stream()
			.filter(n -> !IbexBasePattern.isAttrNode(n))
			.forEach(n -> ra.getNodeMappings().put(n, (EObject) match.get(n)));

		strategy.setIsRuleApplicationFinal(ra);
		match.put(ConsistencyPattern.getProtocolNodeName(ruleName), ra);
	}
}
