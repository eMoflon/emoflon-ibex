package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.TGGRule;

public class OperationalSCFactory {

	private Collection<ShortcutRule> scRules;
	private PropagatingOperationalStrategy strategy;

	public OperationalSCFactory(PropagatingOperationalStrategy strategy, Collection<ShortcutRule> scRules) {
		this.strategy = strategy;
		this.scRules = scRules;
	}

	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(PatternType type) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(strategy.getTGG(), strategy.getOptions());

		for (ShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that contain no interface edges
			if (TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE).size()
					+ TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;

			OperationalShortcutRule opSCR = createOpShortcutRule(strategy, scRule, filterNACAnalysis, type);
			operationalRules.computeIfAbsent(originalRule.getName(), k -> new LinkedList<>()).add(opSCR);
		}
		return operationalRules;
	}

	private OperationalShortcutRule createOpShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, //
			FilterNACAnalysis filterNACAnalysis, PatternType type) {
		switch (type) {
		case FWD:
			return new FWDShortcutRule(strategy, scRule, filterNACAnalysis);
		case BWD:
			return new BWDShortcutRule(strategy, scRule, filterNACAnalysis);
		case CC:
			return new CCShortcutRule(strategy, scRule, filterNACAnalysis);
		default:
			throw new RuntimeException("Shortcut Rules can only be operationalized for FWD, BWD and CC operations");
		}
	}
}
