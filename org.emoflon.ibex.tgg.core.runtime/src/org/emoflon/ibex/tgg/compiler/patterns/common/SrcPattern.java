package org.emoflon.ibex.tgg.compiler.patterns.common;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class SrcPattern extends RulePartPattern {

	public SrcPattern(PatternFactory factory) {
		super(factory.getRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.SRC;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.SRC && e.getBindingType() == BindingType.CREATE;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SRC;
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
