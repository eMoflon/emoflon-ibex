package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGParamValue;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public abstract class IbexGreenPattern implements IGreenPattern {
	protected IGreenPatternFactory factory;
	protected OperationalStrategy strategy;
	protected List<TGGAttributeConstraint> sortedConstraints;
	
	public IbexGreenPattern(GreenPatternFactory factory) {
		this.factory = factory;
		this.strategy = factory.getStrategy();
		this.sortedConstraints = sortConstraints(factory.getAttributeCSPVariables(), factory.getAttributeConstraints());
	}
	
	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		try {			
			return new RuntimeTGGAttributeConstraintContainer(
					factory.getAttributeCSPVariables(), 
					sortedConstraints,
					match,
					factory.getOptions().constraintProvider());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sort attribute constraints for " + match.getPatternName() + ", " + e.getMessage(), e);
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
		EPackage corrPackage = strategy.getOptions().getCorrMetamodel();
		EClass type = (EClass) corrPackage.getEClassifier(TGGModelUtils.getMarkerTypeName(ruleName));
		
		EObject ra = EcoreUtil.create(type);
		strategy.getProtocolResource().getContents().add(ra);
		
	
		for (TGGRuleNode n : factory.getGreenSrcNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.SRC, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackSrcNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.SRC, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (TGGRuleNode n : factory.getGreenTrgNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.TRG, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackTrgNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.TRG, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (TGGRuleNode n : factory.getGreenCorrNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.CORR, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackCorrNodesInRule()) {
			String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.CORR, n.getName());
			EReference ref = (EReference) type.getEStructuralFeature(refName);			
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		match.getParameterNames().stream()
			.filter(n -> !TGGPatternUtil.isAttrNode(n))
			.forEach(n -> {
				@SuppressWarnings("unchecked")
				EcoreEMap<String, EObject> map = (EcoreEMap<String, EObject>) ra.eGet(RuntimePackage.eINSTANCE.getTGGRuleApplication_NodeMappings());
				map.put(n, (EObject) match.get(n));
			});
		strategy.setIsRuleApplicationFinal(ra);
		match.put(TGGPatternUtil.getProtocolNodeName(ruleName), ra);
	}
}
