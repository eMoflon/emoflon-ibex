package org.emoflon.ibex.tgg.runtime.repair.shortcut.updatepolicy;

import java.util.Collection;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;

@FunctionalInterface
public interface IShortcutRuleUpdatePolicy {

	public OperationalShortcutRule chooseOneShortcutRule(Collection<OperationalShortcutRule> shortcutRules,
			ITGGMatch brokenMatch);

}
