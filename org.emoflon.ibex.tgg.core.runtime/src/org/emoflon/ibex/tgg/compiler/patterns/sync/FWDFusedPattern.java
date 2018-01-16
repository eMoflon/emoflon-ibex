package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addComplementOutputAndContextNodes;
import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addKernelOutputAndContextNodes;
import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.setFusedName;

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

public class FWDFusedPattern extends BasicSyncPattern {
	private TGGComplementRule flattenedComplementRule;
	private BlackPatternFactory factory;
	
	public FWDFusedPattern(BlackPatternFactory factory) {
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = setFusedName(flattenedComplementRule.getName(), flattenedComplementRule.getKernel().getName()) + PatternSuffixes.FWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		addComplementOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();		
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).createBlackPattern(FWDBlackPattern.class));		
		MultiAmalgamationUtil.createMarkedInvocations(DomainType.SRC, flattenedComplementRule, this);
	
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.SRC, factory, optimiser);
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
