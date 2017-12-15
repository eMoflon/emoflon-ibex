package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;

import language.DomainType;
import language.TGGRule;

public class FWDRefinementPattern extends FWDPattern {

	public FWDRefinementPattern(PatternFactory factory) {
		super(factory);		
		name = factory.getRule().getName() + PatternSuffixes.FWD_REFINEMENT_INVOCATIONS;
	}
	
	protected void createPatternNetwork() {
		addPositiveInvocation(factory.create(SrcPattern.class));
		addPositiveInvocation(factory.create(CorrContextPattern.class));
		addPositiveInvocation(factory.create(TrgContextPattern.class));
		
		createMarkedInvocations(true, DomainType.SRC);

		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(FWDRefinementPattern.class));
	}
}
