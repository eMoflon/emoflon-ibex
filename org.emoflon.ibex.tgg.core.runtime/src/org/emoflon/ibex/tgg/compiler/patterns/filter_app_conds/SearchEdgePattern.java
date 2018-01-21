package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class SearchEdgePattern extends IbexBasePattern {
	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;
	
	public SearchEdgePattern(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, BlackPatternFactory factory) {
		super(factory);
		
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
		
		initialise(factory.getFlattenedVersionOfRule());
	}

	private void initialise(TGGRule rule) {
		TGGRule checkEdgeRule = FilterACHelper.createCheckEdgeRule(rule, entryPoint, edgeType, eDirection);
		
		String name = checkEdgeRule.getName() + getPatternNameSuffix(entryPoint, edgeType, eDirection);
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>(checkEdgeRule.getNodes());
		Collection<TGGRuleEdge> localEdges = new ArrayList<>(checkEdgeRule.getEdges());
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return !(FilterACHelper.isDECNode(node1) || FilterACHelper.isDECNode(node2));
	}

	public static String getPatternNameSuffix(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		return PatternSuffixes.SEP + entryPoint.getName() +"_" + edgeType.getName() + "_" + eDirection.name().toLowerCase() +  "_SearchEdge_" + entryPoint.getDomainType().getName();
	}
}
