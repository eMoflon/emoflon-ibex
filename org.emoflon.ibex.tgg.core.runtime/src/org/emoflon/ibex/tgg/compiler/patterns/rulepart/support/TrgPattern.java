package org.emoflon.ibex.tgg.compiler.patterns.rulepart.support;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.rulepart.RulePartPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TrgPattern extends RulePartPattern {

	public TrgPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.createTrgContextPattern());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.TRG;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CREATE;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRG;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return isRelevantForSignature(n) && n.getBindingType() == BindingType.CREATE;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getBindingType() == BindingType.CONTEXT && node2.getBindingType() == BindingType.CONTEXT;
	}

}
