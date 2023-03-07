package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.runtime.csp.sorting.SearchPlanAction;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

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
public class GreenSCPattern implements IGreenPattern {

	private OperationalShortcutRule oscRule;
	private IbexOptions options;
	private IGreenPatternFactory factory;
	private List<TGGParamValue> cspVariables;
	private List<TGGAttributeConstraint> sortedAttributeConstraints;
	private boolean optimizeMarkerCreation = false;
	private Map<TGGRuleNode, EReference> name2ref = new HashMap<>();
	private TGGResourceHandler resourceHandler;
	
	
	public GreenSCPattern(IGreenPatternFactory factory, OperationalShortcutRule oscRule) {
		this.oscRule = oscRule;
		this.factory = factory;
		options = factory.getOptions();
		resourceHandler = options.resourceHandler();
		optimizeMarkerCreation = options.blackInterpreter() != null && options.blackInterpreter().getClass().getName().contains("HiPE");
		initialize(factory);
	}
	
	private void initialize(IGreenPatternFactory factory) {
		TGGAttributeConstraintLibrary newLibrary = EcoreUtil.copy(factory.getAttributeLibrary());
		
		cspVariables = newLibrary.getParameterValues();
		List<TGGAttributeConstraint> newAttributeConstraints = newLibrary.getTggAttributeConstraints();
		sortedAttributeConstraints = sortConstraints(cspVariables, newAttributeConstraints);
		for(TGGParamValue pVal : cspVariables) {
			if(pVal instanceof TGGAttributeExpression attrExpr) {
				attrExpr.setObjectVar(oscRule.operationalizedSCR.mapReplacingNodeNameToSCNode(attrExpr.getObjectVar().getName()));
			}
		}
	}


	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getOperationalizedSCR().getNodes(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return TGGFilterUtil.filterEdges(oscRule.getOperationalizedSCR().getEdges(), DomainType.SRC, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleNode> getTrgNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getOperationalizedSCR().getNodes(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return TGGFilterUtil.filterEdges(oscRule.getOperationalizedSCR().getEdges(), DomainType.TRG, BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return TGGFilterUtil.filterNodes(oscRule.getOperationalizedSCR().getNodes(), DomainType.CORR, BindingType.CREATE).stream().map(n -> (TGGRuleCorr) n).collect(Collectors.toList());
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
			return new RuntimeTGGAttributeConstraintContainer(
					cspVariables, 
					sortedAttributeConstraints,
					match,
					factory.getOptions().csp.constraintProvider());
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
		return TGGFilterUtil.filterEdges(oscRule.getOperationalizedSCR().getEdges(), DomainType.CORR, BindingType.CREATE);
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
