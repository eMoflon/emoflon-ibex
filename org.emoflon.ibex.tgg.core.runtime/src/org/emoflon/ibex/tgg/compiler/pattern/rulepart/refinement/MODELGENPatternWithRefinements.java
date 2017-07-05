package org.emoflon.ibex.tgg.compiler.pattern.rulepart.refinement;

import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.MODELGENPattern;

import language.TGGRule;

public class MODELGENPatternWithRefinements extends MODELGENPattern {

	public MODELGENPatternWithRefinements(TGGRule rule, TGGRule flattenedRule, PatternFactory factory) {
		super(flattenedRule, factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.createMODELGENNoNACsPattern());
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints(this));
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints(this));
	}
}
