package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.HigherOrderShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutApplicationTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

public class HigherOrderShortcutRepairStrategy {

	private final IbexOptions options;
	private final PrecedenceGraph pg;

	private final ShortcutApplicationPointFinder scApplPointFinder;
	private final ShortcutPatternProvider shortcutPatternProvider;
	private final ShortcutApplicationTool shortcutApplTool;

	public HigherOrderShortcutRepairStrategy(IbexOptions options, PrecedenceGraph pg, MatchClassifier mc, TGGMatchUtilProvider mup, //
			IGreenInterpreter greenInterpreter, IRedInterpreter redInterpreter) {
		this.options = options;
		this.pg = pg;

		PatternType[] types = { PatternType.FWD, PatternType.BWD, PatternType.SRC, PatternType.TRG };
		this.scApplPointFinder = new ShortcutApplicationPointFinder(options, pg, mc);
		this.shortcutPatternProvider = new HigherOrderShortcutPatternProvider(options, pg, mup, types, true);
		this.shortcutApplTool = new ShortcutApplicationTool(options, greenInterpreter, redInterpreter, shortcutPatternProvider);
	}

	public void repair() {
		Set<ShortcutApplicationPoint> shortcutApplPoints = scApplPointFinder.searchForShortcutApplications();
		for (ShortcutApplicationPoint applPoint : shortcutApplPoints) {
			Collection<ITGGMatch> repairedMatches = repairAtApplicationPoint(applPoint);
			if (repairedMatches != null) {
				for (PrecedenceNode originalNode : applPoint.getOriginalNodes()) {
					options.matchHandler().removeBrokenRuleApplication(originalNode.getMatch().getRuleApplicationNode());
					pg.removeMatch(originalNode.getMatch());
				}
				for (ITGGMatch repairedMatch : repairedMatches) {
					options.matchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
					pg.notifyAddedMatch(repairedMatch);
					pg.notifyRemovedMatch(repairedMatch);
				}
			}
		}
	}

	private Collection<ITGGMatch> repairAtApplicationPoint(ShortcutApplicationPoint applPoint) {
		Collection<ITGGMatch> repairedMatches = shortcutApplTool.repairAtApplicationPoint(applPoint);
		if (repairedMatches != null)
			logSuccessfulRepair(applPoint.getApplicationMatch(), repairedMatches);
		return repairedMatches;
	}

	private void logSuccessfulRepair(ITGGMatch repairCandidate, Collection<ITGGMatch> repairedMatches) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> {
			StringBuilder b = new StringBuilder();
			b.append("  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '");
			b.append(String.join("--", repairedMatches.stream().map(m -> m.getPatternName()).toList()));
			b.append("'");
			return b.toString();
		});
	}

}
