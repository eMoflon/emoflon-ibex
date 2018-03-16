package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MAUtil.addComplementGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.addKernelGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.setFusedName;
import static org.emoflon.ibex.tgg.util.MAUtil.getComplementCorrContextEdgesNotInKernel;
import static org.emoflon.ibex.tgg.util.MAUtil.getComplementGivenDomainAndContextEdgesNotInKernel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterNACStrategy;
import org.emoflon.ibex.tgg.util.MAUtil;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDFusedPattern extends BasicSyncPattern {
	private TGGComplementRule flattenedComplementRule;
	
	public BWDFusedPattern(BlackPatternFactory factory) {
		super(factory);
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
		Collection<TGGRuleEdge> localEdges = new ArrayList<>();		
		
		// Add corr and src edges that are context but not in kernel
		localEdges.addAll(getComplementGivenDomainAndContextEdgesNotInKernel(flattenedComplementRule, signatureNodes, DomainType.SRC));
		localEdges.addAll(getComplementCorrContextEdgesNotInKernel(flattenedComplementRule, signatureNodes));
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork(BlackPatternFactory factory) {
		// This is BWD so everything in the target is context
		addPositiveInvocation(factory.createBlackPattern(TrgPattern.class));
		
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).createBlackPattern(BWDBlackPattern.class));
		MAUtil.createMarkedInvocations(DomainType.TRG, flattenedComplementRule, this);
		
		if(factory.getOptions().getFilterNACStrategy() != FilterNACStrategy.NONE)
			addFilterNACPatterns(DomainType.TRG, factory, optimiser);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}
	
	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.BWD;
	}
}
