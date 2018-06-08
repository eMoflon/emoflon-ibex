package org.emoflon.ibex.tgg.compiler.patterns.co;

import static org.emoflon.ibex.tgg.util.MAUtil.embedKernelRuleAppAndConsistencyPatternNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.isComplementRule;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterNACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.WholeRulePattern;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;

public class COBlackPattern extends WholeRulePattern {
	
	public COBlackPattern(BlackPatternFactory factory) {
		super(factory);
	}
	
	@Override
	protected void initialise(TGGRule rule) {
		super.initialise(rule);
		
		if (isComplementRule(rule)) {
			embedKernelRuleAppAndConsistencyPatternNodes((TGGComplementRule)rule, this);
		}
	}
	
	@Override
	protected void createPatternNetwork(BlackPatternFactory factory) {
		super.createPatternNetwork(factory);
		
		if (isComplementRule(factory.getRule())) {
			TGGComplementRule compRule = (TGGComplementRule) factory.getRule();
			addPositiveInvocation(factory.getFactory(compRule.getKernel()).createBlackPattern(ConsistencyPattern.class));
		}
		
		if (factory.getOptions().getFilterNACStrategy() != FilterNACStrategy.NONE) {
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		}
	}
	
	@Override
	public String name(String ruleName) {
		return getName(ruleName);
	}
	
	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.CO;
	}
}
