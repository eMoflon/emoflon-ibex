package org.emoflon.ibex.tgg.compiler.patterns.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class WholeRulePattern extends RulePartPattern {
	protected PatternFactory factory;

	public WholeRulePattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory);
	}

	private WholeRulePattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		
		createPatternNetwork();
	}
	
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcPattern.class));
		addTGGPositiveInvocation(factory.create(TrgPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(WholeRulePattern.class));
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
		return n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.CORR;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleNode e) {
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.WHOLE;
	}

}
