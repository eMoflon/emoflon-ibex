package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGParamValue;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class IbexGreenPattern implements IGreenPattern {
	protected IGreenPatternFactory factory;
	private IbexOptions options;
	private TGGResourceHandler resourceHandler;
	private boolean optimizeMarkerCreation;
	
	public IbexGreenPattern(IGreenPatternFactory factory) {
		this.factory = factory;
		options = factory.getOptions();
		resourceHandler = options.resourceHandler();
		optimizeMarkerCreation = false;
	}
	
	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match) {
		try {			
			return new RuntimeTGGAttributeConstraintContainer(
					factory.getAttributeCSPVariables(), 
					 sortConstraints(factory.getAttributeCSPVariables(), factory.getAttributeConstraints()),
					match,
					factory.getOptions().csp.constraintProvider());
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
	public boolean isToBeIgnored(ITGGMatch match) {
		return false;
	}
	
	@Override
	public void createMarkers(String ruleName, ITGGMatch match) {
		EPackage corrPackage = options.tgg.corrMetamodel();
		EClass type = (EClass) corrPackage.getEClassifier(TGGModelUtils.getMarkerTypeName(ruleName));
		
		EObject ra = EcoreUtil.create(type);
		
		if(!optimizeMarkerCreation) {
			resourceHandler.getProtocolResource().getContents().add(ra);
		}
	
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
		
		match.put(TGGPatternUtil.getProtocolNodeName(ruleName), ra);
		if(optimizeMarkerCreation) {
			resourceHandler.getProtocolResource().getContents().add(ra);
		}
	}
}
