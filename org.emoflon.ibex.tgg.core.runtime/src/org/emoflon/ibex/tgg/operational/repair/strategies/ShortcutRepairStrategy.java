package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutApplicationTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCMatch;
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

	protected final ShortcutApplicationTool shortcutApplTool;

	public ShortcutRepairStrategy(IbexOptions options, //
			IGreenInterpreter greenInterpreter, IRedInterpreter redInterpreter, //
			ShortcutPatternProvider shortcutPatternProvider) {
		// enable backward navigation for emf edges if smartemf is not enabled
		if (!options.project.usesSmartEMF())
			options.resourceHandler().getModelResourceSet().eAdapters().add(new SmartEMFCrossReferenceAdapter());

		this.shortcutApplTool = new ShortcutApplicationTool(options, greenInterpreter, redInterpreter, shortcutPatternProvider);
	}

	@Override
	public Collection<ITGGMatch> repair(RepairApplicationPoint applPoint) {
		Collection<ITGGMatch> repairedMatches = shortcutApplTool.repairAtApplicationPoint(applPoint);
		if (repairedMatches != null) {
			if (repairedMatches.size() == 1)
				logSuccessfulRepair(applPoint.getApplicationMatch(), repairedMatches.iterator().next());
			else
				logSuccessfulRepair(null, repairedMatches);
			return repairedMatches;
		}
		return null;
	}

	public RepairableMatch isRepairable(ITGGMatch repairCandidate, ITGGMatch replacingMatch) {
		RepairableMatch result = null;

		RepairApplicationPoint applPoint = new RepairApplicationPoint(repairCandidate, replacingMatch.getType());
		Map<SCMatch, OperationalShortcutRule> repMatches = shortcutApplTool.isRepairable(applPoint, replacingMatch.getRuleName());
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
		return shortcutApplTool.countDeletedElements();
	}

	protected void logSuccessfulRepair(ITGGMatch repairCandidate, ITGGMatch repairedMatch) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> //
		"  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '" + repairedMatch.getPatternName() + //
				"' (" + repairCandidate.hashCode() + " -> " + repairedMatch.hashCode() + ")");
	}

	protected void logSuccessfulRepair(ITGGMatch repairCandidate, Collection<ITGGMatch> repairedMatches) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> {
			StringBuilder b = new StringBuilder();
			b.append("  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '");
			b.append(String.join("--", repairedMatches.stream().map(m -> m.getPatternName()).toList()));
			b.append("'");
			return b.toString();
		});
	}
}
