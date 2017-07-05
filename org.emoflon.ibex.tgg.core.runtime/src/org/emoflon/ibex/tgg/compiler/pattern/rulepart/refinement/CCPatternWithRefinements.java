package org.emoflon.ibex.tgg.compiler.pattern.rulepart.refinement;

import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.CCPattern;

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
