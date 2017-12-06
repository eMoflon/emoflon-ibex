package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FilterACPattern extends IbexBasePattern {
	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;
	private IPattern premise;

	public FilterACPattern(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, PatternFactory factory) {
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;

		initialise(factory);
		createPatternNetwork();
	}
	
	private void initialise(PatternFactory factory) {
		String name = factory.getRule().getName() + getPatternNameSuffix(entryPoint, edgeType, eDirection);
		premise = factory.createSearchEdgePattern(entryPoint, edgeType, eDirection);
		
		Collection<TGGRuleNode> signatureNodes = factory.getFlattenedVersionOfRule().getNodes().stream()
				   .filter(this::notInPremise)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
	
		Collection<TGGRuleNode> localNodes = new ArrayList<>(Arrays.asList(FilterACHelper.getDECNode(premise)));
		
		super.initialise(name, signatureNodes, localNodes, localEdges);		
	}

	private void createPatternNetwork() {
		addPositiveInvocation(premise);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	public boolean notInPremise(TGGRuleNode e) {
		return premise.getSignatureNodes().stream().filter(element -> element.getName().equals(e.getName())).count() != 0;
	}
	
	public static String getPatternNameSuffix(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection){
		return PatternSuffixes.SEP + entryPoint.getName() + "_" + edgeType.getName() + "_" + eDirection.toString().toLowerCase() + "_DEC_" + entryPoint.getDomainType().getName();
	}
}
