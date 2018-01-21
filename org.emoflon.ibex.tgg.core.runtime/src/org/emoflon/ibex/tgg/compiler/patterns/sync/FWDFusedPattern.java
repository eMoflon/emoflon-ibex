package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MAUtil.addComplementGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.addKernelGivenDomainAndContextNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.getComplementCorrContextEdgesNotInKernel;
import static org.emoflon.ibex.tgg.util.MAUtil.getComplementGivenDomainAndContextEdgesNotInKernel;
import static org.emoflon.ibex.tgg.util.MAUtil.setFusedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.util.MAUtil;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWDFusedPattern extends BasicSyncPattern {
	private TGGComplementRule flattenedComplementRule;
	
	public FWDFusedPattern(BlackPatternFactory factory) {
		super(factory);
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = setFusedName(flattenedComplementRule.getName(), flattenedComplementRule.getKernel().getName()) + PatternSuffixes.FWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelGivenDomainAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		addComplementGivenDomainAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = new ArrayList<>();		

		// Add corr and trg edges that are context but not in kernel
		localEdges.addAll(getComplementGivenDomainAndContextEdgesNotInKernel(flattenedComplementRule, signatureNodes, DomainType.TRG));
		localEdges.addAll(getComplementCorrContextEdgesNotInKernel(flattenedComplementRule, signatureNodes));
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	protected void createPatternNetwork(BlackPatternFactory factory) {
		// This is FWD so everything in the source is context
		addPositiveInvocation(factory.createBlackPattern(SrcPattern.class));
		
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).createBlackPattern(FWDBlackPattern.class));		
		MAUtil.createMarkedInvocations(DomainType.SRC, flattenedComplementRule, this);
	
		if(BlackPatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.SRC, factory, optimiser);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}
}
