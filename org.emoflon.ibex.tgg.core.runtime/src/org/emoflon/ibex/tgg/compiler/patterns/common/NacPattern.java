package org.emoflon.ibex.tgg.compiler.patterns.common;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import language.*;

/**
 * This class can be used to implement application conditions for a
 * {@link TGGRule}. It extends {@link RulePartPattern} by allowing elements in
 * the body and signature that are not part of the rule.
 */
public class NacPattern extends RulePartPattern {
	private Collection<TGGRuleNode> signatureElements = new ArrayList<>();
	private Collection<TGGRuleNode> bodyNodes = new ArrayList<>();
	private Collection<TGGRuleEdge> bodyEdges = new ArrayList<>();
	
	private String constraintName;
	
	/**
	 * Creates a new ConstraintPattern. The body and signature are initialized
	 * with the given parameters.
	 * 
	 * @param rule
	 *            The {@link TGGRule} that this constraint belongs to.
	 * @param signatureElements
	 *            The {@link TGGElements} that shall be part of this
	 *            constraint's signature.
	 * @param bodyElements
	 *            The {@link TGGElements} that shall be part of this constraint,
	 *            but not part of the signature).
	 */
	public NacPattern(TGGRule rule, Collection<TGGRuleNode> signatureElements, Collection<TGGRuleElement> bodyElements, String name) {
		this.rule = rule;
		this.constraintName = "_" + name;
		this.signatureElements = signatureElements;
		
		this.bodyNodes = bodyElements.stream()
									 .filter(e -> e instanceof TGGRuleNode)
									 .map(e -> (TGGRuleNode)e)
									 .collect(Collectors.toSet());
		
		this.bodyEdges = bodyElements.stream()
				 .filter(e -> e instanceof TGGRuleEdge)
				 .map(e -> (TGGRuleEdge)e)
				 .collect(Collectors.toSet());
		
		// optimisation needs to be done after bodyEdges have been set initially, since "retainAsOpposite()" accesses "getBodyEdges()"
		this.bodyEdges = bodyEdges.stream()
	   			 .filter(e -> optimiser.retainAsOpposite(e, this))
	   			 .collect(Collectors.toSet());
		
		this.initialize();
	}
	
	@Override
	public String getName() {
		return rule.getName() + constraintName + getPatternNameSuffix();
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
	public boolean isRelevantForSignature(TGGRuleNode e) {
		return this.signatureElements.contains(e);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CONSTRAINT;
	}
	
	@Override
	public Collection<TGGRuleNode> getSignatureNodes() {
		return this.signatureElements;
	}
	
	@Override
	public Collection<TGGRuleNode> getLocalNodes() {
		return this.bodyNodes;
	}

	
	@Override
	public Collection<TGGRuleEdge> getLocalEdges() {
		return this.bodyEdges;
	}

	/**
	 * When invoking a NAC we know that all signature elements have already been
	 * checked for injectivity in the invoking rule.
	 */
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return signatureElements.contains(node1) && signatureElements.contains(node2);
	}
}
