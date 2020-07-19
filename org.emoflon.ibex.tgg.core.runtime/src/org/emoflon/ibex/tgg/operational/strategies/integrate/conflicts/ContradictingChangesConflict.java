package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

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
		integrate().repairOneMatch(integrate().getShortcutRepairStrat(), getBrokenMatch().getMatch(), PatternType.FWD);
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		restoreDomain(getBrokenMatch(), DomainType.SRC);
		integrate().repairOneMatch(integrate().getShortcutRepairStrat(), getBrokenMatch().getMatch(), PatternType.BWD);
		resolved = true;
	}

}
