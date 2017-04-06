package org.emoflon.ibex.tgg.compiler.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class IbexPattern {

	protected TGGRule rule;

	/**
	 * each positive pattern invocation for a pattern pat corresponds to the following text find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<IbexPattern> positiveInvocations = new ArrayList<>();

	/**
	 * each negative pattern invocation for a pattern pat corresponds to the following text neg find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<IbexPattern> negativeInvocations = new ArrayList<>();

	/**
	 * each signature element e of a pattern corresponds to a parameter:
	 * 
	 * pattern(e1 : <type of e1>, e2: <type of e2,...)
	 * 
	 * <type of e> is Edge if e is a TGGRuleEdge
	 */
	// protected Collection<TGGRuleElement> signatureElements;

	protected Collection<TGGRuleNode> bodyNodes;

	protected Collection<TGGRuleEdge> bodyEdges;

	protected IbexPattern() {
		
	}
	
	public IbexPattern(TGGRule rule) {
		this.rule = rule;
		initialize();
	}

	protected void initialize() {
		// signatureElements = getSignatureElements(rule);
		bodyNodes = calculateBodyNodes(rule.getNodes());
		bodyEdges = calculateBodyEdges(rule.getEdges());
	}

	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		return rule.getNodes().stream().filter(e -> isRelevantForSignature(e)).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private Collection<TGGRuleEdge> calculateBodyEdges(Collection<TGGRuleEdge> signatureElements) {
		ArrayList<TGGRuleEdge> result = new ArrayList<>();
		signatureElements.stream().filter(e -> isRelevantForBody(e)).forEach(e -> result.add((TGGRuleEdge) e));
		return result;
	}

	private Collection<TGGRuleNode> calculateBodyNodes(Collection<TGGRuleNode> signatureElements) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		signatureElements.stream().filter(e -> isRelevantForBody(e)).forEach(e -> result.add((TGGRuleNode) e));
		;
		return result;
	}

	protected abstract boolean isRelevantForBody(TGGRuleEdge e);

	protected abstract boolean isRelevantForBody(TGGRuleNode n);

	public Collection<TGGRuleNode> getBodyNodes() {
		return bodyNodes;
	}

	public Collection<TGGRuleCorr> getBodyCorrNodes() {
		Collection<TGGRuleCorr> corrs = new HashSet<>();
		bodyNodes.stream().filter(n -> n instanceof TGGRuleCorr).forEach(n -> corrs.add((TGGRuleCorr) n));
		return corrs;
	}

	public Collection<TGGRuleNode> getBodySrcTrgNodes() {
		Collection<TGGRuleNode> srcTrgNodes = new HashSet<TGGRuleNode>(bodyNodes);
		srcTrgNodes.removeAll(getBodyCorrNodes());
		return srcTrgNodes;
	}

	public Collection<TGGRuleEdge> getBodyEdges() {
		return bodyEdges;
	}

	protected abstract boolean isRelevantForSignature(TGGRuleElement e);

	public String getName() {
		return rule.getName() + getPatternNameSuffix();
	}

	abstract protected String getPatternNameSuffix();

	public Collection<TGGRuleElement> getSignatureElements() {
		return getSignatureElements(getRule());
	}

	public Collection<IbexPattern> getPositiveInvocations() {
		return positiveInvocations;
	}

	public Collection<IbexPattern> getNegativeInvocations() {
		return negativeInvocations;
	}

	public boolean ignored() {
		return false;
	}

	public TGGRule getRule() {
		return rule;
	}

}
