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

import language.DomainType;

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

	public List<ITGGMatch> getCausingMatches() {
		return this.causingMatches;
	}

	public DomainType getDomainToBePreserved() {
		return domainToBePreserved;
	}

	//// CRS ////

	@Override
	public void crs_mergeAndPreserve() {
		for (ITGGMatch causingMatch : causingMatches)
			restoreMatch(integrate().getClassifiedBrokenMatches().get(causingMatch));

		// Do not remove elements above conflict match due to new explicit/implicit user deletion handling approach!

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by MERGE_&_PRESERVE");
		resolved = true;
	}

	@Override
	public void crs_revokeDeletion() {
		revokeDeletion();

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_DELETION");
		resolved = true;
	}

	@Override
	public void crs_revokeAddition() {
		revokeAddition();

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_ADDITION");
		resolved = true;
	}

	@Override
	public void crs_preferSource() {
		switch (domainToBePreserved) {
		case SRC:
			revokeDeletion();
			break;
		case TRG:
			revokeAddition();
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
			revokeAddition();
			break;
		case TRG:
			revokeDeletion();
		default:
			break;
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;
	}

	@Override
	public void crs_deleteCorrs() {
		Set<ITGGMatch> toBeCorrsDeleted = new HashSet<>();
		for (ListIterator<ITGGMatch> iterator = causingMatches.listIterator(causingMatches.size()); iterator.hasPrevious();) {
			ITGGMatch match = (ITGGMatch) iterator.previous();

			if (toBeCorrsDeleted.contains(match))
				continue;

			toBeCorrsDeleted.add(match);

			integrate().getPrecedenceGraph().getNode(match).forAllRequiredBy((act, pre) -> {
				if (act.getMatch().getType() != PatternType.CONSISTENCY || toBeCorrsDeleted.contains(act.getMatch()))
					return false;
				toBeCorrsDeleted.add(act.getMatch());
				return true;
			});
		}
		toBeCorrsDeleted.forEach(this::deleteCorrs);

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by DELETE_CORRS");
		resolved = true;
	}

	private Set<ITGGMatch> tmp_restored;

	protected void revokeDeletion() {
		tmp_restored = new HashSet<>();
		for (ITGGMatch match : causingMatches) {
			if (!tmp_restored.contains(match)) {
				restoreMatch(integrate().getClassifiedBrokenMatches().get(match));
				tmp_restored.add(match);
				// restore other branches based on this match below the conflict match
				if (!match.equals(getMatch()))
					restoreMatchesBasedOn(match);
			}
		}
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

	abstract protected void revokeAddition();
}
