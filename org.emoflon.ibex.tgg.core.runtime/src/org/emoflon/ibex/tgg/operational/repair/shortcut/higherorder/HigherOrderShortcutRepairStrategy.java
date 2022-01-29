package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.ShortcutApplicationPointFinder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

import language.DomainType;

public class HigherOrderShortcutRepairStrategy {

	private final PrecedenceGraph pg;
	private final MatchClassifier mc;
	private final HigherOrderTGGRuleFactory ruleFactory;

	private final ShortcutApplicationPointFinder scApplPointFinder;

	public HigherOrderShortcutRepairStrategy(IbexOptions options, PrecedenceGraph pg, MatchClassifier mc, TGGMatchUtilProvider mup) {
		this.pg = pg;
		this.mc = mc;
		this.ruleFactory = new HigherOrderTGGRuleFactory(options, pg, mup);

		this.scApplPointFinder = new ShortcutApplicationPointFinder(pg, mc);
	}

	public void repair() {
		Set<ShortcutApplicationPoint> shortcutApplPoints = scApplPointFinder.searchForShortcutApplications();
		for (ShortcutApplicationPoint applPoint : shortcutApplPoints) {
			DomainType propagationDomain = switch (applPoint.propagationType) {
			case SRC -> DomainType.SRC;
			case TRG -> DomainType.TRG;
			default -> throw new RuntimeException("Unexpected propagation type: " + applPoint.propagationType);
			};
			HigherOrderTGGRule originalHigherOrderRule = ruleFactory.createHigherOrderTGGRuleFromConsMatches(applPoint.originalNodes);
			Set<HigherOrderTGGRule> replacingHigherOrderRules = applPoint.setOfReplacingNodes.stream() //
					.map(n -> ruleFactory.createHigherOrderTGGRuleFromSrcTrgNodes(n, propagationDomain)) //
					.collect(Collectors.toSet());

			// TODO continue
		}

		// TODO continue
	}

}
