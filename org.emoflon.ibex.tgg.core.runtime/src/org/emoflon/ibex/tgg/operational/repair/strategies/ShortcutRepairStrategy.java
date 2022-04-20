package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.BasicShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.smartemf.runtime.notification.SmartEMFCrossReferenceAdapter;

/**
 * This class attempts to repair broken matches by using operationalized shortcut rules (OSR). These
 * OSRs detect specific situations (like certain deltas) and try to repair the broken match to be
 * either a valid match of its rule again or by transforming the match to that of another rule.
 * 
 * @author lfritsche
 *
 */
public class ShortcutRepairStrategy implements RepairStrategy {

	protected final ShortcutPatternTool shortcutPatternTool;

	public ShortcutRepairStrategy(PropagatingOperationalStrategy opStrat, PatternType... shortcutPatternTypes) {
		IbexOptions options = opStrat.getOptions();

		// enable backward navigation for emf edges if smartemf is not enabled
		if (!options.project.usesSmartEMF())
			options.resourceHandler().getModelResourceSet().eAdapters().add(new SmartEMFCrossReferenceAdapter());

		BasicShortcutPatternProvider shortcutPatternProvider = new BasicShortcutPatternProvider( //
				options, new HashSet<>(Arrays.asList(shortcutPatternTypes)), true);

		this.shortcutPatternTool = new ShortcutPatternTool( //
				options, opStrat.getGreenInterpreter(), opStrat.getRedInterpreter(), shortcutPatternProvider);
	}

	@Override
	public ITGGMatch repair(RepairApplicationPoint applPoint) {
		// FIXME hotfix: please adapt to multiple output matches
		Collection<ITGGMatch> repairedMatches = shortcutPatternTool.repairAtApplicationPoint(applPoint);
		if (repairedMatches != null) {
			ITGGMatch repairedMatch = repairedMatches.iterator().next();
			logSuccessfulRepair(applPoint.getApplicationMatch(), repairedMatch);
			return repairedMatch;
		}
		return null;
	}

	public RepairableMatch isRepairable(ITGGMatch repairCandidate, ITGGMatch replacingMatch) {
		RepairableMatch result = null;

		RepairApplicationPoint applPoint = new RepairApplicationPoint(repairCandidate, replacingMatch.getType());
		Map<SCMatch, OperationalShortcutRule> repMatches = shortcutPatternTool.isRepairable(applPoint, replacingMatch.getRuleName());
		for (Entry<SCMatch, OperationalShortcutRule> entry : repMatches.entrySet()) {
			boolean validSCMatch = true;
			for (String paramName : replacingMatch.getParameterNames()) {
				if (!entry.getKey().getObjects().contains(replacingMatch.get(paramName))) {
					validSCMatch = false;
					break;
				}
			}
			if (validSCMatch)
				return new RepairableMatch(entry.getKey(), entry.getValue());
		}

		return result;
	}

	public class RepairableMatch {
		public final SCMatch scMatch;
		public final OperationalShortcutRule opSCR;

		RepairableMatch(SCMatch scMatch, OperationalShortcutRule opSCR) {
			this.scMatch = scMatch;
			this.opSCR = opSCR;
		}
	}

	public int countDeletedElements() {
		return shortcutPatternTool.countDeletedElements();
	}

	protected void logSuccessfulRepair(ITGGMatch repairCandidate, ITGGMatch repairedMatch) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> //
		"  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '" + repairedMatch.getPatternName() + //
				"' (" + repairCandidate.hashCode() + " -> " + repairedMatch.hashCode() + ")");
	}
}
