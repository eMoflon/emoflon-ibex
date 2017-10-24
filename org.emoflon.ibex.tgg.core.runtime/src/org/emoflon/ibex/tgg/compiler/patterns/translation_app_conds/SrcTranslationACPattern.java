package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.DomainType;

public class SrcTranslationACPattern extends TranslationACPattern {

	public SrcTranslationACPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), false);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(SrcRefinementsPattern.class));
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
