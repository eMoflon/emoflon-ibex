package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_DeleteCorrs;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_MergeAndPreserve;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeAddition;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeDeletion;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.DomainType;
import language.TGGRuleNode;

public abstract class DeletePreserveConflict extends Conflict
		implements CRS_MergeAndPreserve, CRS_RevokeDeletion, CRS_RevokeAddition, CRS_PreferSource, CRS_PreferTarget, CRS_DeleteCorrs {

	protected final List<ITGGMatch> causingMatches;
	protected final DomainType domainToBePreserved;

	public DeletePreserveConflict(ConflictContainer container, DomainType domainToBePreserved, List<ITGGMatch> causingMatches) {
		super(container);
		this.causingMatches = causingMatches;
		this.domainToBePreserved = domainToBePreserved;
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> matches = new HashSet<>();
		matches.add(causingMatches.get(0));
		return matches;
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		Set<ITGGMatch> matches = new HashSet<>();
		matches.addAll(causingMatches);
		matches.remove(causingMatches.get(0));
		integrate().getPrecedenceGraph().getNode(getMatch()).forAllRequiredBy((act, pre) -> matches.add(act.getMatch()));
		return matches;
	}

	public DomainType getDomainToBePreserved() {
		return domainToBePreserved;
	}

	//// CRS ////

	@Override
	public void crs_mergeAndPreserve() {
		MatchAnalysis analysis = integrate().getMatchUtil().getAnalysis(getMatch());

		causingMatches.forEach(m -> restoreMatch(integrate().getClassifiedBrokenMatches().get(m)));

		analysis.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				analysis.getObject((TGGRuleNode) elt).eContents().forEach(child -> {
					if (!analysis.getObjects().contains(child))
						ModelChangeUtil.deleteElement(child, true);
				});
			}
		});

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by MERGE_&_PRESERVE");
		resolved = true;
	}

	private Set<ITGGMatch> tmp_restored;

	@Override
	public void crs_revokeDeletion() {
		tmp_restored = new HashSet<>();
		causingMatches.forEach(match -> {
			if (!tmp_restored.contains(match)) {
				restoreMatch(integrate().getClassifiedBrokenMatches().get(match));
				tmp_restored.add(match);
				restoreMatchesBasedOn(match);
			}
		});

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_DELETION");
		resolved = true;
	}

	protected void restoreMatchesBasedOn(ITGGMatch match) {
		PrecedenceGraph pg = integrate().getPrecedenceGraph();
		pg.getNode(match).getRequiredBy().forEach(n -> {
			if (n.isBroken()) {
				ITGGMatch m = n.getMatch();
				if (!tmp_restored.contains(m)) {
					restoreMatch(integrate().getClassifiedBrokenMatches().get(m));
					tmp_restored.add(m);
					restoreMatchesBasedOn(m);
				}
			}
		});
	}

	@Override
	public void crs_preferSource() {
		switch (domainToBePreserved) {
		case SRC:
			crs_revokeDeletion();
			break;
		case TRG:
			crs_revokeAddition();
		default:
			break;
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_SOURCE");
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		switch (domainToBePreserved) {
		case SRC:
			crs_revokeAddition();
			break;
		case TRG:
			crs_revokeDeletion();
		default:
			break;
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;
	}

	@Override
	public void crs_deleteCorrs() {
		Set<ITGGMatch> processed = new HashSet<>();
		for (ListIterator<ITGGMatch> iterator = causingMatches.listIterator(causingMatches.size()); iterator.hasPrevious();) {
			ITGGMatch match = (ITGGMatch) iterator.previous();

			if (processed.contains(match))
				continue;

			integrate().deleteGreenCorrs(match);
			processed.add(match);

			integrate().getPrecedenceGraph().getNode(match).forAllRequiredBy((act, pre) -> {
				if (act.getMatch().getType() != PatternType.CONSISTENCY || processed.contains(act.getMatch()))
					return false;
				integrate().deleteGreenCorrs(act.getMatch());
				processed.add(act.getMatch());
				return true;
			});
		}
	}
}
