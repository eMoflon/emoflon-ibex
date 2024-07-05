package org.emoflon.ibex.tgg.operational.repair.strategies;

import java.util.Objects;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public class RepairApplicationPoint {

	protected final ITGGMatch applicationMatch;
	protected final PatternType repairType;

	public RepairApplicationPoint(ITGGMatch applicationMatch, PatternType repairType) {
		this.applicationMatch = Objects.requireNonNull(applicationMatch);
		this.repairType = Objects.requireNonNull(repairType);
	}

	public ITGGMatch getApplicationMatch() {
		return applicationMatch;
	}

	public PatternType getRepairType() {
		return repairType;
	}

}
