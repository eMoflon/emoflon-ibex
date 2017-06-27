package org.emoflon.ibex.tgg.compiler.pattern.rulepart;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class WholeRulePattern extends RulePartPattern {

	public WholeRulePattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.CORR;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.WHOLE;
	}

}
