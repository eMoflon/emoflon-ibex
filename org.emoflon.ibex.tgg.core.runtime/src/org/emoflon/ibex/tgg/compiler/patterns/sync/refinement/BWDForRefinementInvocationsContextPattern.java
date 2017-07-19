package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDPattern;

import language.TGGRule;

public class BWDForRefinementInvocationsContextPattern extends BWDPattern {

	public BWDForRefinementInvocationsContextPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);		
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));

		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(BWDForRefinementInvocationsContextPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.BWD_REFINEMENT_INVOCATIONS;
	}
	

}
