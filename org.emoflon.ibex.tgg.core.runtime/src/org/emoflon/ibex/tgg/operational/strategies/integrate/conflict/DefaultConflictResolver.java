package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DeleteConflictResStrategy;

public class DefaultConflictResolver implements ConflictResolver {

	@Override
	public DeleteConflictResStrategy resolveDeleteConflict(DeleteConflict conflict) {
		return conflict.makeCompromise();
	}

	

}
