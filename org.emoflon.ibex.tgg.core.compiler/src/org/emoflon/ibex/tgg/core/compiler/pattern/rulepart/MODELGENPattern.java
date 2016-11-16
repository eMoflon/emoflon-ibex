package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MODELGENPattern extends RulePartPattern {

	public MODELGENPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.MODELGEN;
	}

}
