package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.TGGRule;

public class BWDRefinementPattern extends BWDPattern {

	public BWDRefinementPattern(PatternFactory factory) {
		super(factory);
		name = factory.getRule().getName() + PatternSuffixes.BWD_REFINEMENT_INVOCATIONS;
	}
	
	protected void createPatternNetwork() {
		addPositiveInvocation(factory.create(TrgPattern.class));
		addPositiveInvocation(factory.create(CorrContextPattern.class));
		addPositiveInvocation(factory.create(SrcContextPattern.class));

		createMarkedInvocations(true);
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(BWDRefinementPattern.class));
	}
	

}
