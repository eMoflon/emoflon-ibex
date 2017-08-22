package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.refinement.SrcWithRefinementsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.refinement.TrgWithRefinementsPattern;

import language.DomainType;

public class ForbidAllFilterACsWithRefinementsPattern extends ForbidAllFilterACsPattern {

	public ForbidAllFilterACsWithRefinementsPattern(DomainType domain, PatternFactory factory) {
		super(domain, factory);
	}
	
	protected void createPatternNetwork() {
		addDECPatternsAsTGGNegativeInvocations(rule, domain);
		
		switch (domain) {
		case SRC:
			addTGGPositiveInvocation(factory.create(SrcWithRefinementsPattern.class));			
			break;

		case TRG:
			addTGGPositiveInvocation(factory.create(TrgWithRefinementsPattern.class));			
			break;
			
		default:
			throw(new IllegalStateException("No handling for CORR domain"));
		}
	}
}
