package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addComplementOutputAndContextNodes;
import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addKernelOutputAndContextNodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ComplementBWDPattern extends BasicSyncPattern {
	private TGGComplementRule flattenedComplementRule;
	private BlackPatternFactory factory;
	
	public ComplementBWDPattern(BlackPatternFactory factory) {
		this.factory = factory;
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = flattenedComplementRule.getName() + "_" + flattenedComplementRule.getKernel().getName() + PatternSuffixes.BWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		addComplementOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();		
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).createBlackPattern(BWDBlackPattern.class));
		MultiAmalgamationUtil.createMarkedInvocations(DomainType.TRG, flattenedComplementRule, this);
		
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.TRG, factory, optimiser);
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
