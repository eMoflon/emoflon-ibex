package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

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
		ACAnalysis filterNACAnalysis = new ACAnalysis(strategy.getTGG(), strategy.getOptions());

		for (ShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			if (scRule.getOverlap().nonOperationalizablePatterns.contains(type))
				continue;

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
			ACAnalysis filterNACAnalysis, PatternType type) {
		return switch (type) {
			case FWD -> new FWDShortcutRule(strategy, scRule, filterNACAnalysis);
			case BWD -> new BWDShortcutRule(strategy, scRule, filterNACAnalysis);
			case CC -> new CCShortcutRule(strategy, scRule, filterNACAnalysis);
			case SRC -> new SRCShortcutRule(strategy, scRule, filterNACAnalysis);
			case TRG -> new TRGShortcutRule(strategy, scRule, filterNACAnalysis);
			default -> throw new RuntimeException("Shortcut Rules cannot be operationalized for " + type.toString() + " operations");
		};
	}
}
