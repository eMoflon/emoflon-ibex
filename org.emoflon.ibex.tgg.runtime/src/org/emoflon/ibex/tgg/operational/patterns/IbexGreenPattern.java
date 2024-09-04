package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

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
	private boolean optimizeMarkerCreation = false;
	private Map<TGGRuleNode, EReference> name2ref = new HashMap<>();
	
	protected List<TGGAttributeConstraint> sortedAttributeConstraints;
	
	public IbexGreenPattern(IGreenPatternFactory factory) {
		this.factory = factory;
		options = factory.getOptions();
		resourceHandler = options.resourceHandler();
		optimizeMarkerCreation = options.blackInterpreter() != null && options.blackInterpreter().getClass().getName().contains("HiPE");
		initialize(factory);
	}
	
	private void initialize(IGreenPatternFactory factory) {
		try {
			sortedAttributeConstraints = sortConstraints(factory.getAttributeCSPVariables(), factory.getAttributeConstraints());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to sort attribute constraints, " + e.getMessage(), e);
		}
	}

	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match) {
		return new RuntimeTGGAttributeConstraintContainer(
				factory.getAttributeCSPVariables(), 
				sortedAttributeConstraints,
				match,
				factory.getOptions().csp.constraintProvider());
		
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
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.SRC, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackSrcNodesInRule()) {
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.SRC, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (TGGRuleNode n : factory.getGreenTrgNodesInRule()) {
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.TRG, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackTrgNodesInRule()) {
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.TRG, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}

		for (TGGRuleNode n : factory.getGreenCorrNodesInRule()) {
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CREATE, DomainType.CORR, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}		
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		for (TGGRuleNode n : factory.getBlackCorrNodesInRule()) {
			EReference ref;
			if(name2ref.containsKey(n))
				ref = name2ref.get(n);
			else {
				String refName = TGGModelUtils.getMarkerRefName(BindingType.CONTEXT, DomainType.CORR, n.getName());
				ref = (EReference) type.getEStructuralFeature(refName);		
				name2ref.put(n, ref);
			}	
			ra.eSet(ref, (EObject) match.get(n.getName()));			
		}
		
		if(optimizeMarkerCreation) {
			resourceHandler.getProtocolResource().getContents().add(ra);
		}
		match.put(TGGPatternUtil.getProtocolNodeName(ruleName), ra);
	}
}
