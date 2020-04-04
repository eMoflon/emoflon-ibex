package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirection;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class OperationalSCFactory {

	private Collection<ShortcutRule> scRules;
	private PropagatingOperationalStrategy strategy;

	public OperationalSCFactory(PropagatingOperationalStrategy strategy, Collection<ShortcutRule> scRules) {
		this.strategy = strategy;
		this.scRules = scRules;
	}

	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(PropagationDirection direction) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();
		for (ShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that do not preserve elements or contain no interface edges
			if (TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE).size()
					+ TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;

			if (TGGFilterUtil.filterNodes(scRule.getMergedNodes(), DomainType.SRC).size() == 0)
				continue;

			if (TGGFilterUtil.filterNodes(scRule.getMergedNodes(), DomainType.TRG).size() == 0)
				continue;

			InterfaceShortcutRule isr = new InterfaceShortcutRule(strategy, direction, scRule);
			operationalRules.computeIfAbsent(originalRule.getName(), k -> new LinkedList<>()).add(isr);
		}
		return operationalRules;
	}
}
