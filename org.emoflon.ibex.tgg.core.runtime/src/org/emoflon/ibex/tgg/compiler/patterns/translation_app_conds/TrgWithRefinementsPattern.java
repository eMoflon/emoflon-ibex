package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TrgWithRefinementsPattern extends RulePartPattern {

	public TrgWithRefinementsPattern(PatternFactory factory) {
		super(factory.getFlattenedVersionOfRule());
		
		// Create pattern network
		addTGGPositiveInvocation(factory.create(TrgPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(TrgWithRefinementsPattern.class));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRG_REFINEMENT_INVOCATIONS;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.TRG;
	}

}
