package org.emoflon.ibex.tgg.core.compiler.pattern;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.transform.stream.StreamSource;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class Pattern {

	
	protected String name;
	
	/**
	 * each signature element e of a pattern corresponds to a parameter:
	 * 
	 *  pattern(e1 : <type of e1>, e2: <type of e2,...)
	 *  
	 *  <type of e> is Edge if e is a TGGRuleEdge
	 */
	protected Collection<TGGRuleElement> signatureElements;
	
	/**
	 * each body node n corresponds to the following text:
	 * if n is a TGGRuleCorr:
	 *      <<Type of n>>.source(n, <<src of n>>);
	 *      <<Type of n>>.target(n, <<trg of n>>);
	 *      
	 * if n is not a TGGRuleCorr:
	 *      <<Type of n>>(n)     
	 * 		
	 */
	protected Collection<TGGRuleNode> bodyNodes = new ArrayList<>();
	
	
	/**
	 * each body edge e corresponds to the following text:
	 * 
	 *  Edge.src(e, <<src of n>>);
	 *  Edge.trg(e, <<trg of n>>);
	 *  Edge.name(e, <<type name of e>>);
	 */
	protected Collection<TGGRuleEdge> bodyEdges = new ArrayList<>();
	
	
	/**
	 * each positive pattern invocation for a pattern pat corresponds to the following text
	 * find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<Pattern> positiveInvocations = new ArrayList<>();
	
	/**
	 * each negative pattern invocation for a pattern pat corresponds to the following text
	 * neg find pat(<<signature elements of pat separated with ",">>);
	 */
	protected Collection<Pattern> negativeInvocations = new ArrayList<>();
	
	public Pattern(TGGRule rule){
		name = rule.getName() + getPatternNameSuffix();
		fillCollections(rule);		
	}

	private void fillCollections(TGGRule rule) {
		signatureElements = getRelevantElements(rule);
		bodyNodes = calculateBodyNodes(signatureElements);
		bodyEdges = calculateBodyEdges(signatureElements);		
	}


	private Collection<TGGRuleEdge> calculateBodyEdges(Collection<TGGRuleElement> signatureElements){
		ArrayList<TGGRuleEdge> result = new ArrayList<>();
		signatureElements.stream().filter(e -> e instanceof TGGRuleEdge && isRelevantForBody((TGGRuleEdge)e)).forEach(e -> result.add((TGGRuleEdge) e));
		return result;
	}


	private Collection<TGGRuleNode> calculateBodyNodes(Collection<TGGRuleElement> signatureElements) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		signatureElements.stream().filter(e -> e instanceof TGGRuleNode && isRelevantForBody((TGGRuleNode)e)).forEach(e -> result.add((TGGRuleNode) e));;
        return result;
	}

	protected Collection<TGGRuleElement> getRelevantElements(TGGRule rule){
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream()).filter(e -> isRelevantForSignature(e)).collect(Collectors.toSet());
	}

	protected abstract boolean isRelevantForSignature(TGGRuleElement e);
	protected abstract boolean isRelevantForBody(TGGRuleEdge e);
	protected abstract boolean isRelevantForBody(TGGRuleNode n);
	protected abstract String getPatternNameSuffix();
	
	public String getName() {
		return name;
	}

	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}

	public Collection<TGGRuleNode> getBodyNodes() {
		return bodyNodes;
	}

	public Collection<TGGRuleEdge> getBodyEdges() {
		return bodyEdges;
	}

	public Collection<Pattern> getPositiveInvocations() {
		return positiveInvocations;
	}

	public Collection<Pattern> getNegativeInvocations() {
		return negativeInvocations;
	}


	
	
}
