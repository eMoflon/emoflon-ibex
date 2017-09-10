package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;


public class ConsistencyWithRefinementsPattern extends ConsistencyPattern {
	
	public ConsistencyWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	protected void createPatternNetwork() {
		createMarkedInvocations();
		addTGGPositiveInvocation(factory.create(WholeRuleWithRefinementsPattern.class));
	}
}
