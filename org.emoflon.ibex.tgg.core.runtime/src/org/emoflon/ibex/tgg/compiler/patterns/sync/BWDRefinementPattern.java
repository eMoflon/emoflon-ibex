package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.DomainType;
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
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).createBlackPattern(BWDRefinementPattern.class));
	}
	

}
