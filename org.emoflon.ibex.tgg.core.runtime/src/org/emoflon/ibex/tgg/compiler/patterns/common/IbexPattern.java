package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EAttribute;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;

import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class IbexPattern {

	protected TGGRule rule;
	
	protected IbexPatternOptimiser optimiser = new IbexPatternOptimiser();

	private Collection<PatternInvocation> positiveInvocations = new ArrayList<>();
	private Collection<PatternInvocation> negativeInvocations = new ArrayList<>();
	
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
		
		// optimisation needs to be done after bodyEdges have been set initially, since "retainAsOpposite()" accesses "getBodyEdges()"
		bodyEdges = bodyEdges.stream()
				   			 .filter(e -> optimiser.retainAsOpposite(e, this))
				   			 .collect(Collectors.toSet());
	}
	
	/**
	 * Auxiliary method used by {@link #calculateBodyEdges(Collection)} to
	 * decide if e should be part of this pattern or not.
	 * 
	 * @param e
	 *            An edge of the underlying TGG rule of this pattern
	 * @return true if e should be a body edge of the pattern, otherwise e is
	 *         not part of the pattern
	 */
	protected abstract boolean isRelevantForBody(TGGRuleEdge e);

	/**
	 * Auxiliary method used by {@link #calculateBodyNodes(Collection)} to
	 * decide if n should be part of this pattern or not.
	 * 
	 * @param n
	 *            A node of the underlying TGG rule of this pattern
	 * @return true if n should be a body node of the pattern, otherwise n is
	 *         not part of the pattern
	 */
	protected abstract boolean isRelevantForBody(TGGRuleNode n);

	/**
	 * Used by {@link #getSignatureElements()} to determine the signature of
	 * this pattern. All variables in a pattern are divided into either local
	 * variables or signature variables. This distinction is based on if any
	 * external component is interested in the computed values of a certain
	 * variable or not. If this is not the case then the variable should be
	 * declared as being local to the pattern as this improves efficiency.
	 * 
	 * @param e
	 *            An edge or a node of the underlying TGG rule.
	 * @return true if e should be part of the patterns signature or not.
	 */
	public abstract boolean isRelevantForSignature(TGGRuleElement e);


	/**
	 * Each signature element e of a pattern corresponds to a parameter:
	 * 
	 * pattern(e1 : <type of e1>, e2: <type of e2,...)
	 */
	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		return rule.getNodes().stream().filter(e -> isRelevantForSignature(e)).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	private Collection<TGGRuleEdge> calculateBodyEdges(Collection<TGGRuleEdge> signatureElements) {
		ArrayList<TGGRuleEdge> result = new ArrayList<>();
		signatureElements.stream().filter(e -> isRelevantForBody(e)).forEach(e -> result.add((TGGRuleEdge) e));
		return result;
	}

	protected Collection<TGGRuleNode> calculateBodyNodes(Collection<TGGRuleNode> signatureElements) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		signatureElements.stream().filter(e -> isRelevantForBody(e)).forEach(e -> result.add((TGGRuleNode) e));
		return result;
	}

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

	public String getName() {
		return rule.getName() + getPatternNameSuffix();
	}

	abstract protected String getPatternNameSuffix();

	public Collection<TGGRuleElement> getSignatureElements() {
		return getSignatureElements(getRule());
	}

	public Collection<PatternInvocation> getPositiveInvocations() {
		return positiveInvocations;
	}
	
	public void addTGGPositiveInvocation(IbexPattern pattern) {
		PatternInvocation pi = new PatternInvocation(this, pattern, getTGGVariableMapping(this, pattern));
		positiveInvocations.add(pi);
	}
	
	public void addCustomPositiveInvocation(IbexPattern pattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		positiveInvocations.add(pi);
	}

	public Collection<PatternInvocation> getNegativeInvocations() {
		return negativeInvocations;
	}
	
	public void addTGGNegativeInvocation(IbexPattern pattern) {
		PatternInvocation pi = new PatternInvocation(this, pattern, getTGGVariableMapping(this, pattern));
		negativeInvocations.add(pi);
	}
		
	public void addTGGNegativeInvocations(Collection<IbexPattern> patterns){
		for (IbexPattern n : patterns)
			addTGGNegativeInvocation(n);
	}
	
	public void addCustomNegativeInvocation(IbexPattern pattern, Map<TGGRuleElement, TGGRuleElement> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		negativeInvocations.add(pi);
	}
	
	private Map<TGGRuleElement, TGGRuleElement> getTGGVariableMapping(IbexPattern rootPattern, IbexPattern invocationpattern) {
		Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
		Set<TGGRuleElement> rootElements = Stream.concat(rootPattern.getSignatureElements().stream(), rootPattern.getBodyNodes().stream()).collect(Collectors.toSet());
		Collection<TGGRuleElement> invocationElements = invocationpattern.getSignatureElements();

		// map invocation elements to root elements based on their name
		invocationElements.stream().forEach(iEl -> mapping.put(rootElements.stream().filter(rEl -> rEl.getName().equals(iEl.getName())).findFirst().get(), iEl));
		return mapping;
	}
	
	public boolean ignored() {
		return false;
	}

	public TGGRule getRule() {
		return rule;
	}
	
	public final static String getVarName(TGGRuleNode node, EAttribute attr) {
		return "__" + node.getName() + "__" + attr.getName() + "__";
	}
	
	public final static Optional<Pair<String, String>> getNodeAndAttrFromVarName(String varName){
		String[] node_attr = varName.split("__");
		
		if(node_attr.length != 3)
			return Optional.empty();
		
		return Optional.of(Pair.of(node_attr[1], node_attr[2]));
	}


	public final static boolean isAttrNode(String nodeName) {
		return nodeName.split("__").length == 3;
	}
}
