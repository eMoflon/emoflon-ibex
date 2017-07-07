package org.emoflon.ibex.tgg.compiler.patterns.gen.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.gen.MODELGENPattern;

public class MODELGENPatternWithRefinements extends MODELGENPattern {

	public MODELGENPatternWithRefinements(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(MODELGENNoNACsPattern.class));
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints(this));
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints(this));
	}
}
