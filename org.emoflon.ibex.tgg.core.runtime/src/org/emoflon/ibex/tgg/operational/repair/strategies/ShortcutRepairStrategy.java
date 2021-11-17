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
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil;
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

	private PropagatingOperationalStrategy opStrat;
	private IbexOptions options;

	protected ShortcutPatternTool scTool;

	public ShortcutRepairStrategy(PropagatingOperationalStrategy opStrat, PatternType... shortcutPatternTypes) {
		this.opStrat = opStrat;
		this.options = opStrat.getOptions();

		// enable backward navigation for emf edges if smartemf is not enabled
		if (!options.project.usesSmartEMF())
			options.resourceHandler().getModelResourceSet().eAdapters().add(new SmartEMFCrossReferenceAdapter());

		initialize(shortcutPatternTypes);
	}

	private void initialize(PatternType[] shortcutPatternTypes) {
		Collection<ShortcutRule> shortcutRules = new OverlapUtil(options) //
				.calculateShortcutRules(options.tgg.flattenedTGG());

		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Generated " + shortcutRules.size() + " Short-Cut Rules:");
		for (ShortcutRule scRule : shortcutRules)
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "  " + scRule.getName());

		scTool = new ShortcutPatternTool(opStrat, shortcutRules, new HashSet<>(Arrays.asList(shortcutPatternTypes)));
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate, PatternType type) {
		ITGGMatch repairedMatch = scTool.processBrokenMatch(type, repairCandidate);
		if (repairedMatch != null)
			logSuccessfulRepair(repairCandidate, repairedMatch);
		return repairedMatch;
	}

	public RepairableMatch isRepairable(ITGGMatch repairCandidate, ITGGMatch replacingMatch) {
		RepairableMatch result = null;

		Map<SCMatch, OperationalShortcutRule> repMatches = scTool.isRepairable(replacingMatch.getType(), repairCandidate,
				replacingMatch.getRuleName());
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
		return scTool.countDeletedElements();
	}

	protected void logSuccessfulRepair(ITGGMatch repairCandidate, ITGGMatch repairedMatch) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> //
		"  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '" + repairedMatch.getPatternName() + //
				"' (" + repairCandidate.hashCode() + " -> " + repairedMatch.hashCode() + ")");
	}
}
