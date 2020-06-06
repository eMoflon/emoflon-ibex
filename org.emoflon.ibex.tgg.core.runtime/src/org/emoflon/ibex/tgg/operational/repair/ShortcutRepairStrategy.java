package org.emoflon.ibex.tgg.operational.repair;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.FWD_Strategy;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import runtime.TGGRuleApplication;

/**
 * This class attempts to repair broken matches by using operationalized
 * shortcut rules (OSR). These OSRs detect specific situations (like certain
 * deltas) and try to repair the broken match to be either a valid match of its
 * rule again or by transforming the match to that of another rule.
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

		// enable backward navigation for emf edges
		options.resourceHandler().getResourceSet().eAdapters().add(new ECrossReferenceAdapter());
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
