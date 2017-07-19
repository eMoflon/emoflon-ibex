package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDPattern;

public class BWDWithRefinementsPattern extends BWDPattern {

	public BWDWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(BWDForRefinementInvocationsContextPattern.class));
		addTGGPositiveInvocation(factory.create(TrgTranslationAndFilterACsWithRefinementPattern.class));
	}
}
