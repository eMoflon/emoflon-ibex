package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.*;

public class ConstraintPattern extends IbexPattern {
	private Collection<TGGRuleElement> signatureElements = new ArrayList<>();
	private Collection<TGGRuleElement> bodyElements = new ArrayList<>();
	
	public ConstraintPattern(TGGRule rule, Collection<TGGRuleElement> signatureElements, Collection<TGGRuleElement> bodyElements) {
		this.rule = rule;
		this.signatureElements = signatureElements;
		this.bodyElements = bodyElements;
		this.initialize();
		
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return this.bodyElements.contains(e);
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return this.bodyElements.contains(n);
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return this.signatureElements.contains(e);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CONSTRAINT;
	}

}
