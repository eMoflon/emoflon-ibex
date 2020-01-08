package org.emoflon.ibex.tgg.operational.repair.shortcut;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.util.TGGUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

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
	
	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(SyncDirection direction) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();
		for(ShortcutRule scRule : scRules) {
			TGGRule sourceRule = scRule.getSourceRule();
			TGGRule targetRule = scRule.getTargetRule();

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that do not preserve elements or contain no interface edges
			if(TGGUtil.filterEdges(sourceRule.getEdges(), BindingType.CREATE).size() + TGGUtil.filterEdges(targetRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;
			
			if(TGGUtil.filterNodes(scRule.getMergedNodes(), DomainType.SRC).size() == 0)
				continue;
			
			if(TGGUtil.filterNodes(scRule.getMergedNodes(), DomainType.TRG).size() == 0)
				continue;
			
			InterfaceShortcutRule isr = new InterfaceShortcutRule(strategy, direction, scRule);
			Collection<OperationalShortcutRule> osRules = operationalRules.getOrDefault(sourceRule.getName(), new LinkedList<>()) ;
			osRules.add(isr);
			operationalRules.put(sourceRule.getName(), osRules);
		}
		return operationalRules;
	}
}