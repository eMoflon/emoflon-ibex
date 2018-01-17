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

public class FWDBlackPattern extends BasicSyncPattern {
	protected BlackPatternFactory factory;

	public FWDBlackPattern(BlackPatternFactory factory) {
		this.factory = factory;
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
		addPositiveInvocation(factory.createBlackPattern(FWDRefinementPattern.class));
		
		// Marked Patterns
		createMarkedInvocations(false, DomainType.SRC);

		// FilterNACs
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.SRC, factory, optimiser);
		
		// NACs
		addNegativeInvocations(collectGeneratedNACs(factory, DomainType.SRC, DomainType.TRG));
		addNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.SRC || n.getBindingType() == BindingType.CONTEXT;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

	@Override
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}

	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.FWD;
	}
}
