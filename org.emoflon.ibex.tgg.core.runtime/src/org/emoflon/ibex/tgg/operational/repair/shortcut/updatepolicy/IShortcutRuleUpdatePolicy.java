package org.emoflon.ibex.tgg.operational.repair.shortcut.updatepolicy;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;

@FunctionalInterface
public interface IShortcutRuleUpdatePolicy {

	public OperationalShortcutRule chooseOneShortcutRule(Collection<OperationalShortcutRule> shortcutRules,
			ITGGMatch brokenMatch);

}
