package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

public abstract class DeleteConflictResStrategy extends ConflictResolutionStrategy {
	
	protected DeleteConflict conflict;

	public DeleteConflictResStrategy(DeleteConflict conflict) {
		this.conflict = conflict;
	}

}
