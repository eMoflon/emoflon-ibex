package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_ActAndLetRepair;
import org.emoflon.ibex.tgg.operational.strategies.integrate.provider.IntegrationFragmentProvider;

import delta.DeltaContainer;

public class PartlyDelConflict extends Conflict implements CRS_ActAndLetRepair {

	public PartlyDelConflict(INTEGRATE integrate, ITGGMatch match) {
		super(integrate, match);
	}

	// TODO adrianm: implement

	//// CRS ////

	@Override
	public void crs_actAndLetRepair(BiConsumer<EObject, EObject> delta) {
		integrate.applyDelta(delta);
		repair();
	}

	@Override
	public void crs_actAndLetRepair(DeltaContainer delta) {
		try {
			integrate.applyDelta(delta);
			repair();
		} catch (InvalidDeltaException e) {
			e.printStackTrace();
		}
	}

	private void repair() {
		try {
			IntegrationFragmentProvider.APPLY_USER_DELTA.apply(integrate);
			IntegrationFragmentProvider.REPAIR.apply(integrate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
