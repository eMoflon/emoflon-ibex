package org.emoflon.ibex.tgg.compiler.patterns.rulepart.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.rulepart.CCPattern;

import language.DomainType;
import language.TGGRule;

public class CCPatternWithRefinements extends CCPattern {
	
	public CCPatternWithRefinements(TGGRule rule, TGGRule flattenedRule, PatternFactory factory) {
		super(flattenedRule, factory);
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.createCCNoNACsPattern());
		
		addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.SRC));
		addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.TRG));
	}

}
