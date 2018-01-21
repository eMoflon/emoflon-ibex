package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MAUtil.embedKernelConsistencyPatternNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.isComplementRule;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;

public class FWDRefinementPattern extends FWDBlackPattern {

	public FWDRefinementPattern(BlackPatternFactory factory) {
		super(factory);		
		name = factory.getRule().getName() + PatternSuffixes.FWD_REFINEMENT_INVOCATIONS;
	}
	
	protected void createPatternNetwork() {
		addPositiveInvocation(factory.createBlackPattern(SrcPattern.class));
		addPositiveInvocation(factory.createBlackPattern(CorrContextPattern.class));
		addPositiveInvocation(factory.createBlackPattern(TrgContextPattern.class));
		
		createMarkedInvocations(true, DomainType.SRC);
		
		if (isComplementRule(factory.getRule())) { 
			TGGComplementRule compRule = (TGGComplementRule) factory.getRule();
			addPositiveInvocation(factory.getFactory(compRule.getKernel()).createBlackPattern(ConsistencyPattern.class));
		}

		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).createBlackPattern(FWDRefinementPattern.class));
	}
	
	@Override
	protected void initialise(TGGRule rule) {
		super.initialise(rule);
		
		if (isComplementRule(rule)) {
			embedKernelConsistencyPatternNodes((TGGComplementRule)rule, this);
		}
	}
}
