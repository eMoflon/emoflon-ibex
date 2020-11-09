package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.FragmentProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_ActAndLetRepair;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_DeleteCorrs;

import delta.DeltaContainer;

public abstract class InconsistentChangesConflict extends Conflict implements CRS_DeleteCorrs, CRS_ActAndLetRepair {

	public InconsistentChangesConflict(ConflictContainer container) {
		super(container);
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> matches = new HashSet<>();
		matches.add(getMatch());
		return matches;
	}

	//// CRS ////

	@Override
	public void crs_deleteCorrs() {
		Set<ITGGMatch> toBeCorrsDeleted = new HashSet<>();
		toBeCorrsDeleted.add(getMatch());
		integrate().getPrecedenceGraph().getNode(getMatch()).forAllRequiredBy((act, pre) -> {
			if (act.getMatch().getType() != PatternType.CONSISTENCY)
				return false;
			toBeCorrsDeleted.add(act.getMatch());
			return true;
		});
		toBeCorrsDeleted.forEach(this::deleteCorrs);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by DELETE_CORRS");
		resolved = true;
	}

	@Override
	public void crs_actAndLetRepair(BiConsumer<EObject, EObject> delta) {
		integrate().applyDelta(delta);
		repair();

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by ACT_&_LET_REPAIR");
		resolved = true;
	}

	@Override
	public void crs_actAndLetRepair(DeltaContainer delta) {
		try {
			integrate().applyDelta(delta);
			repair();
		} catch (InvalidDeltaException e) {
			e.printStackTrace();
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by ACT_&_LET_REPAIR");
		resolved = true;
	}

	protected void repair() {
		try {
			// TODO adrianM: fix this repair!
			FragmentProvider.APPLY_USER_DELTA.apply(integrate());
			FragmentProvider.REPAIR.apply(integrate());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
