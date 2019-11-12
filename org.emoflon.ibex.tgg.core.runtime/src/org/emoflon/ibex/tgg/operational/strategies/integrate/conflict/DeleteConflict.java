package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DelayCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DeleteConflictResStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.PreserveConstrChangesCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.PreserveDeletionCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.RevokeDeletionCRS;

public class DeleteConflict extends Conflict {

	private final Set<ConflictingElement> subjects;
	private final DeletionChain deletionChain;

	DeleteConflict(INTEGRATE integrate, ITGGMatch match, Set<ConflictingElement> subjects) {
		super(match);
		this.subjects = subjects;
		this.deletionChain = new DeletionChain(integrate, match);
	}

	public Set<ConflictingElement> getSubjects() {
		return subjects;
	}

	public DeletionChain getDeletionChain() {
		return deletionChain;
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
	
	public DeleteConflictResStrategy keepConflict() {
		return new DelayCRS(this, TOKEN);
	}

}
