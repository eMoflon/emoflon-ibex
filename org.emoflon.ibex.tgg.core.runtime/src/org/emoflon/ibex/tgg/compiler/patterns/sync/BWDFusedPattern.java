package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addComplementGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.addKernelGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MultiAmalgamationUtil.setFusedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDFusedPattern extends BasicSyncPattern {
	private TGGComplementRule flattenedComplementRule;
	private BlackPatternFactory factory;
	
	public BWDFusedPattern(BlackPatternFactory factory) {
		this.factory = factory;
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = setFusedName(flattenedComplementRule.getName(), flattenedComplementRule.getKernel().getName()) + PatternSuffixes.BWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelGivenDomainAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		addComplementGivenDomainAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();		
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork(BlackPatternFactory factory) {
		//TODO: [Milica] This is causing exceptions. Check it out!
//		addPositiveInvocation(factory.createBlackPattern(TrgPattern.class));
//		addPositiveInvocation(factory.createBlackPattern(CorrContextPattern.class));
//		addPositiveInvocation(factory.createBlackPattern(SrcContextPattern.class));
		
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
	
	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.BWD;
	}
}
