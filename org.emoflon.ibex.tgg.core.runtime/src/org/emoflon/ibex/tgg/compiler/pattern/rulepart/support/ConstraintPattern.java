package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;
import org.moflon.tgg.mosl.tgg.TripleGraphGrammarFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.*;

/**
 * The ConstraintPattern can be used to implement application constraints for a {@link TGGRule}.
 * It extends {@link RulePartPattern} by allowing elements in the body and signature that are not part of the rule.
 */
public class ConstraintPattern extends RulePartPattern {
	private Collection<TGGRuleElement> signatureElements = new ArrayList<>();
	private Collection<TGGRuleNode> bodyNodes = new ArrayList<>();
	private Collection<TGGRuleEdge> bodyEdges = new ArrayList<>();
	
	/**
	 * Creates a new ConstraintPattern. The body and signature are initialized with the given parameters.
	 * @param rule The {@link TGGRule} that this constraint belongs to.
	 * @param signatureElements The {@link TGGElements} that shall be part of this constraint's signature.
	 * @param bodyElements The {@link TGGElements} that shall be part of this constraint, but not part of the signature).
	 */
	public ConstraintPattern(TGGRule rule, Collection<TGGRuleElement> signatureElements, Collection<TGGRuleElement> bodyElements) {
		this.rule = rule;
		this.signatureElements = signatureElements;
		
		this.bodyNodes = bodyElements.stream()
									 .filter(e -> e instanceof TGGRuleNode)
									 .map(e -> (TGGRuleNode)e)
									 .collect(Collectors.toSet());
		
		this.bodyEdges = bodyElements.stream()
				 .filter(e -> e instanceof TGGRuleEdge)
				 .map(e -> (TGGRuleEdge)e)
				 .collect(Collectors.toSet());
		
		this.initialize();
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return this.bodyEdges.contains(e);
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return this.bodyNodes.contains(n);
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
		return this.bodyNodes;
	}

	
	@Override
	public Collection<TGGRuleEdge> getBodyEdges() {
		return this.bodyEdges;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return false;
	}


}
