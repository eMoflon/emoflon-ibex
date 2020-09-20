package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

import language.DomainType;
import language.TGGRuleNode;

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

		PrecedenceGraph pg = integrate().getPrecedenceGraph();
		ITGGMatch match = getBrokenMatch().getMatch();
		IGreenPatternFactory gFactory = integrate().getGreenFactory(match.getRuleName());

		Collection<TGGRuleNode> greenRuleNodes = new HashSet<>();
		greenRuleNodes.addAll(gFactory.getGreenSrcNodesInRule());
		greenRuleNodes.addAll(gFactory.getGreenTrgNodesInRule());
		for (TGGRuleNode ruleNode : greenRuleNodes) {
			for (PrecedenceNode node : pg.getNodesTranslating(match.get(ruleNode.getName()))) {
				if (node.getMatch().getType() == PatternType.CONSISTENCY)
					continue;

				if (pg.getSourceNodes().contains(node))
					srcMatches.add(node.getMatch());
				else if (pg.getTargetNodes().contains(node)) {
					trgMatches.add(node.getMatch());
				}
			}
		}
	}

	//// CRS ////

	@Override
	public void crs_preferSource() {
		for (ITGGMatch trgMatch : trgMatches) {
			RepairableMatch repairableMatch = integrate().getShortcutRepairStrat().isRepairable(getBrokenMatch().getMatch(), trgMatch);
			if (repairableMatch != null)
				revertRepairable(repairableMatch, DomainType.TRG);
		}

		integrate().getOptions().matchDistributor().updateMatches();
		integrate().repairOneMatch(integrate().getShortcutRepairStrat(), getBrokenMatch().getMatch(), PatternType.FWD);
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		for (ITGGMatch srcMatch : srcMatches) {
			RepairableMatch repairableMatch = integrate().getShortcutRepairStrat().isRepairable(getBrokenMatch().getMatch(), srcMatch);
			if (repairableMatch != null)
				revertRepairable(repairableMatch, DomainType.SRC);
		}

		integrate().getOptions().matchDistributor().updateMatches();
		integrate().repairOneMatch(integrate().getShortcutRepairStrat(), getBrokenMatch().getMatch(), PatternType.BWD);
		resolved = true;
	}

}
