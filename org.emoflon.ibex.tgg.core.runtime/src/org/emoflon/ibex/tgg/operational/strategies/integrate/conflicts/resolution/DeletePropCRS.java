package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropConflict;

public abstract class DeletePropCRS extends ConflictResolutionStrategy<DeletePropConflict> {

	public DeletePropCRS(DeletePropConflict conflict) {
		super(conflict);
	}

}
