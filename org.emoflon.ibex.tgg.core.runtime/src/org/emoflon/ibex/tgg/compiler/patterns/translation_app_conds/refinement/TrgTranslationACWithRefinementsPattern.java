package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.TranslationACPattern;

import language.DomainType;

public class TrgTranslationACWithRefinementsPattern extends TranslationACPattern {

	public TrgTranslationACWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(TrgWithRefinementsPattern.class));
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
