package org.emoflon.ibex.tgg.core.compiler.pattern.strategy.ilp;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleElement;

public class FWDILPPattern extends ILPPattern {

	public FWDILPPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.filter(e -> e.getDomainType() == DomainType.SRC || e.getBindingType() == BindingType.CONTEXT)
				.collect(Collectors.toSet());
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.FWD_ILP;
	}

}
