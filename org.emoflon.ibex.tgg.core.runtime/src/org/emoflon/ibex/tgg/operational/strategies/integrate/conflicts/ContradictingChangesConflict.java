package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;

import language.DomainType;

public class ContradictingChangesConflict extends InconsistentChangesConflict
		implements CRS_PreferSource, CRS_PreferTarget {

	public ContradictingChangesConflict(ConflictContainer container) {
		super(container);
	}

	//// CRS ////

	@Override
	public void crs_preferSource() {
		restoreDomain(getBrokenMatch(), DomainType.TRG);
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		restoreDomain(getBrokenMatch(), DomainType.SRC);
		resolved = true;
	}

}
