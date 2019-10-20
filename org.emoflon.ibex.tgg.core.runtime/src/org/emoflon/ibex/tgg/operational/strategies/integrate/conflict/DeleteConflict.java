package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.LinkedList;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class DeleteConflict extends Conflict {
	
	private Set<ConflictingElement> subjects;
	private LinkedList<IMatch> deletionChain;
	
	// Resolution strategies
	private final int takeSrcDropTrg = 0;
	private final int takeTrgDropSrc = 1;

	public DeleteConflict(IMatch match) {
		super(match);
	}
	
	public ConflictResolutionStrategy takeSrcDropTrg() {
		return new ConflictResolutionStrategy(takeSrcDropTrg);
	}
	
	public ConflictResolutionStrategy takeTrgDropSrc() {
		return new ConflictResolutionStrategy(takeTrgDropSrc);
	}

}
