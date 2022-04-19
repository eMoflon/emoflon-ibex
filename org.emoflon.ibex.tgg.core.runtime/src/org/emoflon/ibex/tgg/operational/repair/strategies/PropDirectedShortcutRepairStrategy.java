package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Collection;

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
		// FIXME hotfix: please adapt to multiple output matches
		RepairApplicationPoint applPoint = new RepairApplicationPoint(repairCandidate, propDirHolder.get().getPatternType());
		Collection<ITGGMatch> repairedMatches = scTool.repairAtApplicationPoint(applPoint);
		if (repairedMatches != null) {
			ITGGMatch repairedMatch = repairedMatches.iterator().next();
			logSuccessfulRepair(repairCandidate, repairedMatch);
			return repairedMatch;
		}
		return null;
	}

}
