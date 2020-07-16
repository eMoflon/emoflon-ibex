package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeChanges;

import language.DomainType;

public class InconsDomainChangesConflict extends InconsistentChangesConflict implements CRS_RevokeChanges {

	protected final DomainType changedDomain;

	public InconsDomainChangesConflict(ConflictContainer container, DomainType changedDomain) {
		super(container);
		this.changedDomain = changedDomain;
	}

	//// CRS ////

	@Override
	public void crs_revokeChanges() {
		restoreDomain(getBrokenMatch(), changedDomain);
		resolved = true;
	}

}
