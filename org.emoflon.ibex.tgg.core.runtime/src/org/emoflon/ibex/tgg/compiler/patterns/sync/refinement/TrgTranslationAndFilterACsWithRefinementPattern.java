package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.sync.TrgTranslationAndFilterACsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.refinement.TrgTranslationACWithRefinementsPattern;

import language.DomainType;

public class TrgTranslationAndFilterACsWithRefinementPattern extends TrgTranslationAndFilterACsPattern {

	public TrgTranslationAndFilterACsWithRefinementPattern(PatternFactory factory) {
		super(factory);
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(TrgTranslationACWithRefinementsPattern.class));
		
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addTGGPositiveInvocation(factory.createFilterACWithRefimenentsPatterns(DomainType.TRG));
	}
}