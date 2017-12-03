package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class IbexBasePattern implements IPattern {
	protected String name;
	
	protected Collection<TGGRuleNode> signatureNodes;
	
	protected Collection<TGGRuleNode> localNodes;
	protected Collection<TGGRuleEdge> localEdges;
	
	protected Collection<PatternInvocation> positiveInvocations;
	protected Collection<PatternInvocation> negativeInvocations;
	
	protected IbexPatternOptimiser optimiser;

	private IbexBasePattern(String name) {
		this.name = name;
		
		signatureNodes = new ArrayList<>();
		
		localNodes = new ArrayList<>();
		localEdges = new ArrayList<>();
		
		positiveInvocations = new ArrayList<>();
		negativeInvocations = new ArrayList<>();
		
		optimiser = new IbexPatternOptimiser();
	}

	/* Getters and setters */

	public String getName() {
		return name;
	}

	public Collection<TGGRuleNode> getSignatureNodes() {
		return signatureNodes;
	}
	
	public Collection<TGGRuleNode> getLocalNodes() {
		return localNodes;
	}

	public Collection<TGGRuleCorr> getLocalCorrNodes() {
		return localNodes.stream()
						.filter(TGGRuleCorr.class::isInstance)
						.map(TGGRuleCorr.class::cast)
				        .collect(Collectors.toList());
	}

	public Collection<TGGRuleEdge> getLocalEdges() {
		return localEdges;
	}

	public Collection<TGGRuleNode> getBodySrcTrgNodes() {
		Collection<TGGRuleNode> srcTrgNodes = new ArrayList<>(localNodes);
		srcTrgNodes.removeAll(getLocalCorrNodes());
		return srcTrgNodes;
	}

	public Collection<PatternInvocation> getPositiveInvocations() {
		return positiveInvocations;
	}

	public Collection<PatternInvocation> getNegativeInvocations() {
		return negativeInvocations;
	}
	
	public void addPositiveInvocation(IbexBasePattern pattern, Map<TGGRuleNode, TGGRuleNode> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		positiveInvocations.add(pi);
	}

	public void addNegativeInvocation(IbexBasePattern pattern, Map<TGGRuleNode, TGGRuleNode> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		negativeInvocations.add(pi);
	}

	/* Factory method to create pattern */
	
	public static IbexBasePattern create(String name, ArrayList<TGGRuleNode> signatureNodes, ArrayList<TGGRuleNode> localNodes, ArrayList<TGGRuleEdge> localEdges) {
		// Validation
		Collection<TGGRuleNode> intersection = new ArrayList<>(signatureNodes);
		intersection.retainAll(localNodes);
		if(!intersection.isEmpty())
			throw new IllegalArgumentException("Signature and local nodes must be disjunct but have an intersection: " + intersection);
		
		// Create pattern
		IbexBasePattern pattern = new IbexBasePattern(name);
		pattern.signatureNodes.addAll(signatureNodes);
		pattern.localNodes.addAll(localNodes);
		
		// Optimise: only add one of two opposite edges
		pattern.localEdges.addAll(
			localEdges.stream()
				     .filter(e -> pattern.optimiser.retainAsOpposite(e, pattern))
				     .collect(Collectors.toList()));
		
		return pattern;
	}
}
