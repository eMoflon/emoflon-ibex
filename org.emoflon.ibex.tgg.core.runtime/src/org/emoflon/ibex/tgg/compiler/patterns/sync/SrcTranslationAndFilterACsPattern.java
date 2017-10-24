package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.TranslationACPattern;

import language.DomainType;

public class SrcTranslationAndFilterACsPattern extends TranslationACPattern {
	protected PatternFactory factory;

	public SrcTranslationAndFilterACsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule());
		this.factory = factory;
	}
	
	protected void createPatternNetwork() {
		super.createPatternNetwork();
		
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRANSLATION_FILTER_AC_SRC;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}
}
