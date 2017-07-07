package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;

import language.DomainType;

public class SrcTranslationACPattern extends TranslationACPattern {

	public SrcTranslationACPattern(PatternFactory factory) {
		super(factory.getRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(SrcPattern.class));
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
