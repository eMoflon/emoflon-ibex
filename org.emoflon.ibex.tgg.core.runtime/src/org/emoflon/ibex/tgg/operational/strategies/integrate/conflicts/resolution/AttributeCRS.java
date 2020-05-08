package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;

public abstract class AttributeCRS extends ConflictResolutionStrategy<AttributeConflict> {

	public AttributeCRS(AttributeConflict conflict) {
		super(conflict);
	}

}
