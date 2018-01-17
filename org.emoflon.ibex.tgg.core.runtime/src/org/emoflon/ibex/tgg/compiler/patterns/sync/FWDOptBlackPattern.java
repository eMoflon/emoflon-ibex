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

public class FWDOptBlackPattern extends BasicSyncPattern {

	protected BlackPatternFactory factory;

	public FWDOptBlackPattern(BlackPatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork();
	}

	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.FWD_OPT;

		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork() {
		// Rule Patterns
		addPositiveInvocation(factory.createBlackPattern(FWDRefinementOptPattern.class));

		// FilterNACs
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.SRC, factory, optimiser);
	}

	protected boolean isSignatureNode(TGGRuleNode n) {
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
	
}
