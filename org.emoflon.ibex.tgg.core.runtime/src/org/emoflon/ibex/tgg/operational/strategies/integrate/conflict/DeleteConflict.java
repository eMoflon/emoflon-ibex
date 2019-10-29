package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DeleteConflictResStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.PreserveConstrChangesCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.PreserveDeletionCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.RevokeDeletionCRS;

public class DeleteConflict extends Conflict {

	private Set<ConflictingElement> subjects;
	private Set<DeletionChain> deletionChains;

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

	public DeleteConflictResStrategy preserveDeletion() {
		return new PreserveDeletionCRS(this, TOKEN);
	}

	public DeleteConflictResStrategy revokeDeletion() {
		return new RevokeDeletionCRS(this, TOKEN);
	}

	public DeleteConflictResStrategy preserveConstructiveChanges() {
		return new PreserveConstrChangesCRS(this, TOKEN);
	}

}
