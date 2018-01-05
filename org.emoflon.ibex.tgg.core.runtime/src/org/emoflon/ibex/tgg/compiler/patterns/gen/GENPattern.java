package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GENPattern extends IbexBasePattern {
	private PatternFactory factory;
	
	public GENPattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.GEN;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					   .filter(this::isSignatureNode)
					   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CONTEXT;
	}

	protected void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.create(GENRefinementPattern.class));
		
		addNegativeInvocations(factory.createPatternsForMultiplicityConstraints());
		addNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints());
		
		addNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
		addNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		// Root pattern
		return true;
	}
	
	@Override
	public PatternFactory getPatternFactory() {
		return factory;
	}
}
