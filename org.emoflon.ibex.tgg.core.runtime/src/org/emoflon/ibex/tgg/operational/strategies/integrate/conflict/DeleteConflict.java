package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.CompromiseCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.DeleteConflictResStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.PreserveDeletionCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.RevokeDeletionCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies.ActAndLetRepairCRS;

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

	/**
	 * Creates a {@link PreserveDeletionCRS} that preserves the deletion and revokes
	 * all created elements, links or attribute changes that cause the conflict.
	 * 
	 * @return the conflict resolution strategy
	 */
	public DeleteConflictResStrategy preserveDeletion() {
		return new PreserveDeletionCRS(this, TOKEN);
	}

	/**
	 * Creates {@link RevokeDeletionCRS} that completely revokes the deletion to
	 * resolve the conflict.
	 * 
	 * @return the conflict resolution strategy
	 */
	public DeleteConflictResStrategy revokeDeletion() {
		return new RevokeDeletionCRS(this, TOKEN);
	}

	/**
	 * Creates a {@link CompromiseCRS} that revokes as few as possible deletions to
	 * resolve the conflict.
	 * 
	 * @return the conflict resolution strategy
	 */
	public DeleteConflictResStrategy makeCompromise() {
		return new CompromiseCRS(this, TOKEN);
	}

	/**
	 * Creates a {@link ActAndLetRepairCRS} that expects an action from the user
	 * respectively a model change to be performed in order to repair the models so
	 * that the conflict will be resolved.
	 * 
	 * @return the conflict resolution strategy
	 */
	public DeleteConflictResStrategy actAndLetRepairRule() {
		return new ActAndLetRepairCRS(this, TOKEN);
	}

}
