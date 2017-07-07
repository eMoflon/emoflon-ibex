package org.emoflon.ibex.tgg.compiler.patterns.cc.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.DomainType;

public class CCPatternWithRefinements extends CCPattern {
	
	public CCPatternWithRefinements(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(CCNoNACsPattern.class));
		
		if (PatternFactory.strategy != FilterACStrategy.NONE) {
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		}
	}

}
