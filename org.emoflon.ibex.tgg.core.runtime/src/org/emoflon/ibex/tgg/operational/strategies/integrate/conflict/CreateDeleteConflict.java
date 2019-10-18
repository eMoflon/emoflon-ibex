package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

public class CreateDeleteConflict extends Conflict {
	
	// Resolution strategies
	private final int takeSrcDropTrg = 0;
	private final int takeTrgDropSrc = 1;

	public CreateDeleteConflict() {
		
	}
	
	public ConflictResolutionStrategy takeSrcDropTrg() {
		return new ConflictResolutionStrategy(takeSrcDropTrg);
	}
	
	public ConflictResolutionStrategy takeTrgDropSrc() {
		return new ConflictResolutionStrategy(takeTrgDropSrc);
	}

}
