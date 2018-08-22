package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;

import language.BindingType;
import language.DomainType;
import language.TGGRule;

public class InterfaceSCFactory {

	private Collection<ShortcutRule> scRules;

	public InterfaceSCFactory(Collection<ShortcutRule> scRules) {
		this.scRules = scRules;
	}
	
	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(SyncDirection direction) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();
		for(ShortcutRule scRule : scRules) {
			TGGRule sourceRule = scRule.getSourceRule();
			TGGRule targetRule = scRule.getTargetRule();
			
			if(TGGCollectionUtil.filterEdges(sourceRule.getEdges(), BindingType.CREATE).size() + TGGCollectionUtil.filterEdges(targetRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;
			
			if(TGGCollectionUtil.filterNodes(scRule.getEntryNodes(), DomainType.SRC).size() == 0)
				continue;
			
			if(TGGCollectionUtil.filterNodes(scRule.getEntryNodes(), DomainType.TRG).size() == 0)
				continue;
			
			InterfaceShortcutRule isr = new InterfaceShortcutRule(direction, scRule);
			Collection<OperationalShortcutRule> osRules = operationalRules.getOrDefault(sourceRule, new ArrayList<>()) ;
			osRules.add(isr);
			operationalRules.put(sourceRule.getName(), osRules);
		}
		return operationalRules;
	}
}
