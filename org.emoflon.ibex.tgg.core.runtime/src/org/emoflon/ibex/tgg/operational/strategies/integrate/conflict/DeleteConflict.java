package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class DeleteConflict extends Conflict {

	private Set<ConflictingElement> subjects;
	private Set<DeletionChain> deletionChains;

	// Resolution strategies
	private final int takeSrcDropTrg = 0;
	private final int takeTrgDropSrc = 1;

	DeleteConflict(IMatch match, Set<ConflictingElement> subjects,
			Set<DeletionChain> deletionChains) {
		super(match);
		this.subjects = subjects;
		this.deletionChains = deletionChains;
	}

	public Set<ConflictingElement> getSubjects() {
		return subjects;
	}

	public Set<DeletionChain> getDeletionChains() {
		return deletionChains;
	}

	public Set<EObject> getDeletedRootElements() {
		return deletionChains.stream() //
				.map(c -> c.getLast().getValue()) //
				.collect(Collectors.toSet());
	}
	
	/* RESOLUTION */

	public ConflictResolutionStrategy takeSrcDropTrg() {
		return new ConflictResolutionStrategy(takeSrcDropTrg);
	}

	public ConflictResolutionStrategy takeTrgDropSrc() {
		return new ConflictResolutionStrategy(takeTrgDropSrc);
	}

}
