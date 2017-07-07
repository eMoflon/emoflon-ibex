package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.TrgTranslationACPattern;

import language.DomainType;

public class TrgTranslationAndFilterACsPattern extends TranslationAndFilterACsPattern {
	protected PatternFactory factory;

	public TrgTranslationAndFilterACsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule());
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(TrgTranslationACPattern.class));
		
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRANSLATION_FILTER_AC_TRG;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}
}
