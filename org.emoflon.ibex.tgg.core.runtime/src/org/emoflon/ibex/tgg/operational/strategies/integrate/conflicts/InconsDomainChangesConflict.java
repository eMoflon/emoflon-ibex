package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_RevokeChanges;

import language.DomainType;

public class InconsDomainChangesConflict extends InconsistentChangesConflict implements CRS_RevokeChanges {

	protected final DomainType changedDomain;

	public InconsDomainChangesConflict(ConflictContainer container, DomainType changedDomain) {
		super(container);
		this.changedDomain = changedDomain;
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		return new HashSet<>();
	}

	//// CRS ////

	@Override
	public void crs_revokeChanges() {
		restoreDomain(getBrokenMatch(), changedDomain);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_CHANGES");
		resolved = true;
	}

}
