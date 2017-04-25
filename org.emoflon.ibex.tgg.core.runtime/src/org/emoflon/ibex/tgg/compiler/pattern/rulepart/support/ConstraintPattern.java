package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.*;

public class ConstraintPattern extends RulePartPattern {
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
	
	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return this.signatureElements;
	}
	
	@Override
	public Collection<TGGRuleNode> getBodyNodes() {
		return this.bodyElements.stream()
								.filter(e -> e instanceof TGGRuleNode)
								.map(e -> (TGGRuleNode)e)
								.collect(Collectors.toSet());
	}

	
	@Override
	public Collection<TGGRuleEdge> getBodyEdges() {
		return this.bodyElements.stream()
				.filter(e -> e instanceof TGGRuleEdge)
				.map(e -> (TGGRuleEdge)e)
				.collect(Collectors.toSet());
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return false;
	}


}
