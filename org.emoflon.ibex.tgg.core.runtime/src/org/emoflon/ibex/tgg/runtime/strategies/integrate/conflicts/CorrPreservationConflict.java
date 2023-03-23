package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.strategies.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class CorrPreservationConflict extends InconsistentChangesConflict implements CRS_PreferSource, CRS_PreferTarget {

	protected Set<ITGGMatch> srcMatches;
	protected Set<ITGGMatch> trgMatches;

	public CorrPreservationConflict(ConflictContainer container) {
		super(container);

		computeSrcAndTrgMatches();
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		Set<ITGGMatch> matches = new HashSet<>();
		matches.addAll(srcMatches);
		matches.addAll(trgMatches);
		return matches;
	}

	private void computeSrcAndTrgMatches() {
		srcMatches = new HashSet<>();
		trgMatches = new HashSet<>();

		PrecedenceGraph pg = integrate().precedenceGraph();
		ITGGMatch match = getMatch();
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());

		Collection<IBeXNode> greenRuleNodes = new HashSet<>();
		greenRuleNodes.addAll(operationalRule.getCreateSource().getNodes());
		greenRuleNodes.addAll(operationalRule.getCreateTarget().getNodes());
		for (IBeXNode ruleNode : greenRuleNodes) {
			for (PrecedenceNode node : pg.getNodesTranslating(match.get(ruleNode.getName()))) {
				if (node.getMatch().getType() == PatternType.CONSISTENCY)
					continue;

				if (pg.getSourceNodes().contains(node))
					srcMatches.add(node.getMatch());
				else if (pg.getTargetNodes().contains(node))
					trgMatches.add(node.getMatch());
			}
		}
	}

	//// CRS ////

	@Override
	public void crs_preferSource() {
		for (ITGGMatch trgMatch : trgMatches) {
			RepairableMatch repairableMatch = integrate().repair().isShortcutRepairable(getMatch(), trgMatch);
			if (repairableMatch != null)
				revertRepairable(repairableMatch, DomainType.TARGET);
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_SOURCE");
		resolved = true;

		integrate().getOptions().matchDistributor().updateMatches();
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Auto-repair after resolution of " + printConflictIdentification() + ":");
		integrate().repair().shortcutRepairOneMatch(getMatch(), PatternType.FWD);
	}

	@Override
	public void crs_preferTarget() {
		for (ITGGMatch srcMatch : srcMatches) {
			RepairableMatch repairableMatch = integrate().repair().isShortcutRepairable(getMatch(), srcMatch);
			if (repairableMatch != null)
				revertRepairable(repairableMatch, DomainType.SOURCE);
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;

		integrate().getOptions().matchDistributor().updateMatches();
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Auto-repair after resolution of " + printConflictIdentification() + ":");
		integrate().repair().shortcutRepairOneMatch(getMatch(), PatternType.BWD);
	}

}
