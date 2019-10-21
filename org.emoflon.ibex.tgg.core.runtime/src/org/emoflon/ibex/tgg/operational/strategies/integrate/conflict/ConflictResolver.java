package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

public interface ConflictResolver {
	
	ConflictResolutionStrategy resolveConflict(Conflict conflict);
	
}
