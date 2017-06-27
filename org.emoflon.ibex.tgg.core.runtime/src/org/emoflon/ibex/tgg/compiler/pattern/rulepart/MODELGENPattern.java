package org.emoflon.ibex.tgg.compiler.pattern.rulepart;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class MODELGENPattern extends RulePartPattern {
	
	private Collection<TGGRuleElement> signatureElements = new HashSet<TGGRuleElement>();

	public MODELGENPattern(TGGRule rule) {
		super(rule);
		signatureElements = getSignatureElements(getRule());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
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

	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}

}
