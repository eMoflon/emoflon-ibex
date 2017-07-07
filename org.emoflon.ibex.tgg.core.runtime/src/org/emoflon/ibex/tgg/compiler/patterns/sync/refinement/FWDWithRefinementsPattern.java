package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDPattern;

public class FWDWithRefinementsPattern extends FWDPattern {

	public FWDWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(FWDForRefinementInvocationsContextPattern.class));
		addTGGPositiveInvocation(factory.create(SrcTranslationAndFilterACsWithRefinementPattern.class));
	}

}
