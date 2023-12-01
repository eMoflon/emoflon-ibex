package org.emoflon.ibex.tgg.operational.repair.shortcut.selectionpolicy;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;

@FunctionalInterface
public interface IShortcutRuleSelectionPolicy {

	public OperationalShortcutRule selectOneShortcutRule(Collection<OperationalShortcutRule> shortcutRules,
			ITGGMatch brokenMatch);

}
