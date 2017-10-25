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
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(TrgPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));

		createMarkedInvocations(true);
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(BWDRefinementPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.BWD_REFINEMENT_INVOCATIONS;
	}
	

}
