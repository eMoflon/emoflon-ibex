package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class SrcContextPattern extends RulePartPattern {

	public SrcContextPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.SRC && e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SRC_CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return isRelevantForSignature(e);
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return isRelevantForSignature(n);
	}

	@Override
	protected boolean injectivityIsAlreadyCheckedBySubpattern(TGGRuleNode node1, TGGRuleNode node2) {
		return false;
	}

}
