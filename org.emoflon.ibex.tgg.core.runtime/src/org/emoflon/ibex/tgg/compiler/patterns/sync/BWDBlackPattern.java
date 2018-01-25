package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDBlackPattern extends BasicSyncPattern {

	public BWDBlackPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork();
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
	
	protected void createPatternNetwork() {
		// Rule Patterns
		addPositiveInvocation(factory.createBlackPattern(BWDRefinementPattern.class));

		// Marked Patterns
		createMarkedInvocations(false, DomainType.TRG);

		// FilterNACs
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.TRG, factory, optimiser);
		
		// NACs
		addNegativeInvocations(collectGeneratedNACs(factory, DomainType.TRG, DomainType.SRC));
		addNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.BWD;
	}
}
