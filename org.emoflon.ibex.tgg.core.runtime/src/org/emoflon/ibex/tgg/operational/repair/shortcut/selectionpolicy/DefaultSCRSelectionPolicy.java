package org.emoflon.ibex.tgg.operational.repair.shortcut.selectionpolicy;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;

public class DefaultSCRSelectionPolicy implements IShortcutRuleSelectionPolicy {

	@Override
	public OperationalShortcutRule selectOneShortcutRule(Collection<OperationalShortcutRule> shortcutRules,
			ITGGMatch brokenMatch) {
		return shortcutRules.stream().findFirst().orElse(null);
	}

}
