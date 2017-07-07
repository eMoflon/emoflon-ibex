package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.sync.SrcTranslationAndFilterACsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.refinement.SrcTranslationACWithRefinementsPattern;

import language.DomainType;

public class SrcTranslationAndFilterACsWithRefinementPattern extends SrcTranslationAndFilterACsPattern {

	public SrcTranslationAndFilterACsWithRefinementPattern(PatternFactory factory) {
		super(factory);
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcTranslationACWithRefinementsPattern.class));
		
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
	}
}
