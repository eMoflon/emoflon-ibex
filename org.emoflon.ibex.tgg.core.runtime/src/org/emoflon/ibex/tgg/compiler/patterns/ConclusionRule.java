package org.emoflon.ibex.tgg.compiler.patterns;

import language.TGGRule;
import language.TGGRuleNode;

/*
 * Provides a connection between the conclusion and the premise over a shared node
 * Used for PAC strategy
 */
public class ConclusionRule {
	
	private TGGRule conclusionRule;
	private TGGRuleNode premiseConclusionNode;
	
	protected ConclusionRule(TGGRule conclusionRule, TGGRuleNode premiseConclusionNode) {
		this.conclusionRule = conclusionRule;
		this.premiseConclusionNode = premiseConclusionNode;
	}
	
	public TGGRule getConclusionRule() {
		return conclusionRule;
	}
	
	public TGGRuleNode getPremiseConclusionNode() {
		return premiseConclusionNode;
	}
}
