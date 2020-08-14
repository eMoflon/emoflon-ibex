package org.emoflon.ibex.tgg.operational.repair.shortcut.updatepolicy;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;

public class DefaultSCRUpdatePolicy implements IShortcutRuleUpdatePolicy {

	@Override
	public OperationalShortcutRule chooseOneShortcutRule(Collection<OperationalShortcutRule> shortcutRules,
			ITGGMatch brokenMatch) {
		return shortcutRules.stream().findFirst().orElse(null);
	}

}
