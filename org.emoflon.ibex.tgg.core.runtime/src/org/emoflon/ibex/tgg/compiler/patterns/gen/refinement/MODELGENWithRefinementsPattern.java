package org.emoflon.ibex.tgg.compiler.patterns.gen.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.gen.MODELGENPattern;

public class MODELGENWithRefinementsPattern extends MODELGENPattern {

	public MODELGENWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(MODELGENForRefinementInvocationsPattern.class));
		
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints());
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints());
		
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}
}
