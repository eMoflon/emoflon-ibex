package org.emoflon.ibex.tgg.compiler.pattern;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
	private Collection<IbexPattern> positiveInvocations = new HashSet<>();

	/**
	 * each negative pattern invocation for a pattern pat corresponds to the following text neg find pat(<<signature elements of pat separated with ",">>);
	 */
	private Collection<IbexPattern> negativeInvocations = new HashSet<>();
	
	private Map<IbexPattern, Collection<Map<TGGRuleElement, TGGRuleElement>>> invocationVariableMappings = new HashMap<>();
	private Map<IbexPattern, Collection<Map<TGGRuleElement, TGGRuleElement>>> inverseInvocationVariableMappings = new HashMap<>();
	private Map<IbexPattern, Collection<TGGRuleElement>> invocationUnmappedVariables = new HashMap<>();

	/**
	 * each signature element e of a pattern corresponds to a parameter:
	 * 
	 * pattern(e1 : <type of e1>, e2: <type of e2,...)
	 * 
	 * <type of e> is Edge if e is a TGGRuleEdge
	 */
	// protected Collection<TGGRuleElement> signatureElements;

	private Collection<TGGRuleNode> bodyNodes;

	private Collection<TGGRuleEdge> bodyEdges;

	protected IbexPattern() {
		
	}
	
	public IbexPattern(TGGRule rule) {
		this.rule = rule;
		initialize();
	}

	protected void initialize() {
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

	public abstract boolean isRelevantForSignature(TGGRuleElement e);

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
	
	public void addTGGPositiveInvocation(IbexPattern pattern) {
		positiveInvocations.add(pattern);
		addVariableMapping(pattern);
	}
	
	public void addCustomPositiveInvocation(IbexPattern pattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		positiveInvocations.add(pattern);
		addVariableMapping(pattern, mapping);
	}

	public Collection<IbexPattern> getNegativeInvocations() {
		return negativeInvocations;
	}
	
	public void addTGGNegativeInvocation(IbexPattern pattern) {
		negativeInvocations.add(pattern);
		addVariableMapping(pattern);
	}
		
	public void addTGGNegativeInvocations(Collection<IbexPattern> patterns){
		for (IbexPattern n : patterns)
			addTGGNegativeInvocation(n);
	}
	
	public void addCustomNegativeInvocation(IbexPattern pattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		negativeInvocations.add(pattern);
		addVariableMapping(pattern, mapping);
	}
	
	/**
	 * This methods adds a standard tgg mapping based on the names in both this and the invoked pattern
	 * @param invocationPattern
	 */
	protected void addVariableMapping(IbexPattern invocationPattern) {
		addVariableMapping(invocationPattern, InvocationHelper.getTGGVariableMapping(this, invocationPattern));
	}
	
	protected void addVariableMapping(IbexPattern invocationPattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		invocationVariableMappings.putIfAbsent(invocationPattern, new ArrayList<>());
		invocationVariableMappings.get(invocationPattern).add(mapping);
		createInverseMapping(invocationPattern, mapping);
	}
	
	private void createInverseMapping(IbexPattern invocationPattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		Map<TGGRuleElement, TGGRuleElement> inverseMapping = new HashMap<>();
		mapping.keySet().stream().forEach(k -> inverseMapping.put(mapping.get(k), k));
		inverseInvocationVariableMappings.putIfAbsent(invocationPattern, new ArrayList<>());
		inverseInvocationVariableMappings.get(invocationPattern).add(inverseMapping);
	}
	
	public List<TGGRuleElement> getMappedRuleElement(IbexPattern invokedPattern, TGGRuleElement mappedElement) {
		return inverseInvocationVariableMappings.get(invokedPattern).stream().map(e -> e.get(mappedElement)).collect(Collectors.toList());
	}
	
	public Collection<TGGRuleElement> getUnmappedRuleElements(IbexPattern invokedPattern) {
		return invocationUnmappedVariables.get(invokedPattern);
	}

	public boolean ignored() {
		return false;
	}

	public TGGRule getRule() {
		return rule;
	}

}
