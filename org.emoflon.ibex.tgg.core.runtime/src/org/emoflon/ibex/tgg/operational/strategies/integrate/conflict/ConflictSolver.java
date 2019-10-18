package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

public interface ConflictSolver {
	
	ConflictResolutionStrategy resolveConflict(Conflict conflict);
	
}
