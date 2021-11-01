package org.emoflon.ibex.tgg.operational.repair.strategies;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirectionHolder;

public class PropDirectedShortcutRepairStrategy extends ShortcutRepairStrategy implements PropagationDirectedRepairStrategy {

	private final PropagationDirectionHolder propDirHolder;

	public PropDirectedShortcutRepairStrategy(PropagatingOperationalStrategy opStrat, PatternType[] shortcutPatternTypes,
			PropagationDirectionHolder propDirHolder) {
		super(opStrat, shortcutPatternTypes);
		this.propDirHolder = propDirHolder;
	}
	
	@Override
	public ITGGMatch repair(ITGGMatch repairCandidate) {
		ITGGMatch repairedMatch = scTool.processBrokenMatch(propDirHolder.get().getPatternType(), repairCandidate);
		if (repairedMatch != null)
			logSuccessfulRepair(repairCandidate, repairedMatch);
		return repairedMatch;
	}

}
