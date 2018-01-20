package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.util.MAUtil.embedKernelConsistencyPatternNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.isComplementRule;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;

public class BWDRefinementPattern extends BWDBlackPattern {

	public BWDRefinementPattern(BlackPatternFactory factory) {
		super(factory);
		name = factory.getRule().getName() + PatternSuffixes.BWD_REFINEMENT_INVOCATIONS;
	}
	
	protected void createPatternNetwork() {
		addPositiveInvocation(factory.createBlackPattern(TrgPattern.class));
		addPositiveInvocation(factory.createBlackPattern(CorrContextPattern.class));
		addPositiveInvocation(factory.createBlackPattern(SrcContextPattern.class));

		createMarkedInvocations(true, DomainType.TRG);
		
		if (isComplementRule(factory.getRule())) { 
			TGGComplementRule compRule = (TGGComplementRule) factory.getRule();
			addPositiveInvocation(factory.getFactory(compRule.getKernel()).createBlackPattern(ConsistencyPattern.class));
		}
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).createBlackPattern(BWDRefinementPattern.class));
	}
	
	@Override
	protected void initialise(TGGRule rule) {
		super.initialise(rule);
		
		if (isComplementRule(rule)) {
			embedKernelConsistencyPatternNodes((TGGComplementRule)rule, this);
		}
	}
	

}
