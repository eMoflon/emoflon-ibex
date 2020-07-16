package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.operational.strategies.integrate.FragmentProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_ActAndLetRepair;

import delta.DeltaContainer;

public abstract class InconsistentChangesConflict extends Conflict implements CRS_ActAndLetRepair {

	public InconsistentChangesConflict(ConflictContainer container) {
		super(container);
	}

	@Override
	public void crs_actAndLetRepair(BiConsumer<EObject, EObject> delta) {
		integrate().applyDelta(delta);
		repair();
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
		resolved = true;
	}

	protected void repair() {
		try {
			FragmentProvider.APPLY_USER_DELTA.apply(integrate());
			FragmentProvider.REPAIR.apply(integrate());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
