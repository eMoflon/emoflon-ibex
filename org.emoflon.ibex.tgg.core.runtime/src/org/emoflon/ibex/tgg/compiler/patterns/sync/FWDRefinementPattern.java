package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;

import language.DomainType;
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

		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).createBlackPattern(FWDRefinementPattern.class));
	}
}
