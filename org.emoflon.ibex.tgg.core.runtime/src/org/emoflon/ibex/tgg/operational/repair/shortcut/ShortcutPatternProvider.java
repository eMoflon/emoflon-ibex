package org.emoflon.ibex.tgg.operational.repair.shortcut;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairApplicationPoint;

public interface ShortcutPatternProvider {

	Collection<OperationalShortcutRule> getOperationalShortcutRules(RepairApplicationPoint applPoint);

	LocalPatternSearch getPatternSearch(OperationalShortcutRule opShortcutRule);

}
