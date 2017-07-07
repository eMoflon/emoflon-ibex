package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDPattern;

import language.TGGRule;

public class FWDForRefinementInvocationsContextPattern extends FWDPattern {

	public FWDForRefinementInvocationsContextPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);		
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(TrgContextPattern.class));

		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(FWDForRefinementInvocationsContextPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.FWD_REFINEMENT_INVOCATIONS;
	}
	

}
