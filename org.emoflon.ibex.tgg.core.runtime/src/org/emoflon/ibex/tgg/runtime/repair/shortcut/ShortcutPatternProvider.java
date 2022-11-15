package org.emoflon.ibex.tgg.runtime.repair.shortcut;

import java.util.Collection;

import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;

public interface ShortcutPatternProvider {

	Collection<OperationalShortcutRule> getOperationalShortcutRules(RepairApplicationPoint applPoint);

	LocalPatternSearch getPatternSearch(OperationalShortcutRule opShortcutRule);

}
