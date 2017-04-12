package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class SearchEdgePattern extends RulePartPattern {
	
	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;

	public SearchEdgePattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		super(DECHelper.createCheckEdgeRule(rule, entryPoint, edgeType, eDirection));
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		if(DECHelper.isDECNode(node1) || DECHelper.isDECNode(node2)) 
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
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SEP + entryPoint.getName() +"_" + edgeType.getName() + "_" + eDirection.name().toLowerCase() +  "_SearchEdge_" + entryPoint.getDomainType().getName();
	}

}
