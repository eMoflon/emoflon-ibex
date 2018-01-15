package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GENBlackPattern extends IbexBasePattern {
	private BlackPatternFactory factory;
	
	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.GEN;
	}
	
	public GENBlackPattern(BlackPatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);
	}
	
	protected void initialise(TGGRule rule) {
		String name = getName(rule.getName());
		
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

	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(GENRefinementPattern.class));
		
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
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}
}
