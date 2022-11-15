package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.debug.LoggerConfig;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.CRS_RevokeChanges;

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
		// TODO adrianm: fix this
		restoreDomain(integrate().matchClassifier().get(getMatch()), changedDomain);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_CHANGES");
		resolved = true;
	}

}
