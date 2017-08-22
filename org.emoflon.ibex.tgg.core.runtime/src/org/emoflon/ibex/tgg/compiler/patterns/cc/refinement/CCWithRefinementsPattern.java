package org.emoflon.ibex.tgg.compiler.patterns.cc.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.DomainType;

public class CCWithRefinementsPattern extends CCPattern {
	
	public CCWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(CCForRefinementInvocationsPattern.class));
		
		if (PatternFactory.strategy != FilterACStrategy.NONE) {
			addTGGPositiveInvocation(factory.createFilterACWithRefimenentsPatterns(DomainType.SRC));
			addTGGPositiveInvocation(factory.createFilterACWithRefimenentsPatterns(DomainType.TRG));
		}
	}

}
