package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;

import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class SearchEdgePattern extends RulePartPattern {
	
	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;

	public SearchEdgePattern(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, PatternFactory factory) {
		super(FilterACHelper.createCheckEdgeRule(factory.getFlattenedVersionOfRule(), entryPoint, edgeType, eDirection));
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		if(FilterACHelper.isDECNode(node1) || FilterACHelper.isDECNode(node2)) 
			return false;
		
		return true;	
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return true;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return true;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleNode e) {
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return getPatternNameSuffix(entryPoint, edgeType, eDirection);
	}

	public static String getPatternNameSuffix(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		return PatternSuffixes.SEP + entryPoint.getName() +"_" + edgeType.getName() + "_" + eDirection.name().toLowerCase() +  "_SearchEdge_" + entryPoint.getDomainType().getName();
	}

}
