package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.ShortcutApplicationPointFinder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

public class HigherOrderShortcutRepairStrategy {

	private final PrecedenceGraph pg;
	private final MatchClassifier mc;
	private final HigherOrderTGGRuleFactory factory;

	private final ShortcutApplicationPointFinder scApplPointFinder;

	public HigherOrderShortcutRepairStrategy(PrecedenceGraph pg, MatchClassifier mc, TGGMatchUtilProvider mup) {
		this.pg = pg;
		this.mc = mc;
		this.factory = new HigherOrderTGGRuleFactory(pg, mup);

		this.scApplPointFinder = new ShortcutApplicationPointFinder(pg, mc);
	}

	public void repair() {
		Set<ShortcutApplicationPoint> shortcutApplPoints = scApplPointFinder.searchForShortcutApplications();

		// TODO continue
	}

}
