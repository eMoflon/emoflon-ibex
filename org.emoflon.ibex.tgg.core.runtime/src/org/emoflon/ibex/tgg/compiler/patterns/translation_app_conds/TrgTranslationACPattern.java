package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.DomainType;

public class TrgTranslationACPattern extends TranslationACPattern {

	public TrgTranslationACPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), false);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(TrgRefinementsPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRG_TRANSLATION_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}

}
