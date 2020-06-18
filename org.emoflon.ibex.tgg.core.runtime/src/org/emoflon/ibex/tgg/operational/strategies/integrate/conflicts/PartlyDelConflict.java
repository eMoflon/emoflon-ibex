package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.operational.strategies.integrate.FragmentProvider;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_ActAndLetRepair;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;

import delta.DeltaContainer;
import language.DomainType;

public class PartlyDelConflict extends Conflict implements CRS_ActAndLetRepair, CRS_PreferSource, CRS_PreferTarget {

	public PartlyDelConflict(ConflictContainer container) {
		super(container);
	}

	//// CRS ////

	@Override
	public void crs_actAndLetRepair(BiConsumer<EObject, EObject> delta) {
		integrate().applyDelta(delta);
		repair();
	}

	@Override
	public void crs_actAndLetRepair(DeltaContainer delta) {
		try {
			integrate().applyDelta(delta);
			repair();
		} catch (InvalidDeltaException e) {
			e.printStackTrace();
		}
	}

	private void repair() {
		try {
			FragmentProvider.APPLY_USER_DELTA.apply(integrate());
			FragmentProvider.REPAIR.apply(integrate());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void crs_preferSource() {
		restoreDomain(getBrokenMatch(), DomainType.TRG);
	}

	@Override
	public void crs_preferTarget() {
		restoreDomain(getBrokenMatch(), DomainType.SRC);
	}

}
