package org.emoflon.ibex.tgg.compiler.patterns.cc.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCPattern;

import language.DomainType;

public class CCPatternWithRefinements extends CCPattern {
	
	public CCPatternWithRefinements(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(CCNoNACsPattern.class));
		
		addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.SRC));
		addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.TRG));
	}

}
