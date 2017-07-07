package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.DomainType;

public class TrgTranslationACPattern extends TranslationACPattern {

	public TrgTranslationACPattern(PatternFactory factory) {
		super(factory.getRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(TrgPattern.class));
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
