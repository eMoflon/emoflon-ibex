package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
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
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;
import org.moflon.smartemf.runtime.notification.SmartEMFCrossReferenceAdapter;

import runtime.TGGRuleApplication;

/**
 * This class attempts to repair broken matches by using operationalized shortcut rules
 * (OSR). These OSRs detect specific situations (like certain deltas) and try to repair
 * the broken match to be either a valid match of its rule again or by transforming the
 * match to that of another rule.
 * 
 * @author lfritsche
 *
 */
public class ShortcutRepairStrategy implements AbstractRepairStrategy {

	protected final static Logger logger = Logger.getLogger(AbstractRepairStrategy.class);

	private PropagatingOperationalStrategy opStrat;
	private IbexOptions options;
	private ShortcutPatternTool scTool;
	private PatternType syncDirection;

	public ShortcutRepairStrategy(PropagatingOperationalStrategy opStrat) {
		this.opStrat = opStrat;
		this.options = opStrat.getOptions();

		// enable backward navigation for emf edges if smartemf is not enabled
		if(!options.project.usesSmartEMF())
			options.resourceHandler().getModelResourceSet().eAdapters().add(new SmartEMFCrossReferenceAdapter());
		initialize();
	}

	private void initialize() {
		Collection<ShortcutRule> shortcutRules = new OverlapUtil(opStrat.getOptions()) //
				.calculateShortcutRules(opStrat.getOptions().tgg.flattenedTGG());

		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Generated " + shortcutRules.size() + " Short-Cut Rules:");
		for (ShortcutRule scRule : shortcutRules)
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "  " + scRule.getName());

		scTool = new ShortcutPatternTool(opStrat, shortcutRules, opStrat.getShortcutPatternTypes());
		updateDirection();
		
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "");
	}

	@Override
	public Collection<ITGGMatch> chooseMatches(Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications) {
		return brokenRuleApplications.keySet() //
				.stream() //
				.filter(this::noMissingNodes) //
				.map(brokenRuleApplications::get) //
				.collect(Collectors.toList());
	}

	private boolean noMissingNodes(TGGRuleApplication ra) {
//		return TGGPatternUtil.getAllNodes(ra).stream().noneMatch(n -> n == null);
		return true;
	}

	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		updateDirection();
		ITGGMatch repairedMatch = scTool.processBrokenMatch(syncDirection, repairCandidate);
		if (repairedMatch != null)
			logSuccessfulRepair(repairCandidate, repairedMatch);
		return repairedMatch;
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

		Map<SCMatch, OperationalShortcutRule> repMatches = scTool.isRepairable(replacingMatch.getType(), repairCandidate, replacingMatch.getRuleName());
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

	private void updateDirection() {
		if (opStrat instanceof SYNC)
			syncDirection = ((SYNC) opStrat).getSyncStrategy() instanceof FWD_Strategy ? PatternType.FWD : PatternType.BWD;
		else
			syncDirection = null;
	}

	public int countDeletedElements() {
		return scTool.countDeletedElements();
	}

	private void logSuccessfulRepair(ITGGMatch repairCandidate, ITGGMatch repairedMatch) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> //
		"  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '" + repairedMatch.getPatternName() + //
				"' (" + repairCandidate.hashCode() + " -> " + repairedMatch.hashCode() + ")");
	}
}
