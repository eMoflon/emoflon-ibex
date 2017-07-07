package org.emoflon.ibex.tgg.compiler.patterns.sync.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.WholeRulePattern;

import language.TGGRule;

public class WholeRuleWithRefinementsPattern extends WholeRulePattern {
	
	public WholeRuleWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule(), factory);
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcPattern.class));
		addTGGPositiveInvocation(factory.create(TrgPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(WholeRuleWithRefinementsPattern.class));
	}
}
