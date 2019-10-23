package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DeleteConflictResStrategy;

public interface ConflictResolver {
	
	DeleteConflictResStrategy resolveDeleteConflict(DeleteConflict conflict);
	
}
