package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.DeletionChain;

public abstract class DeletePropConflict extends Conflict {

	private final DeletionChain deletionChain;

	public DeletePropConflict(INTEGRATE integrate, ITGGMatch match) {
		super(match);
		this.deletionChain = new DeletionChain(integrate, match);
	}

	public DeletionChain getDeletionChain() {
		return deletionChain;
	}

}
