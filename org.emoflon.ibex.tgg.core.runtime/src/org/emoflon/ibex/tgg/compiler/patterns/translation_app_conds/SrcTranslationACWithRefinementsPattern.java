package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.DomainType;

public class SrcTranslationACWithRefinementsPattern extends TranslationACPattern {

	public SrcTranslationACWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(SrcWithRefinementsPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SRC_TRANSLATION_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}

}
