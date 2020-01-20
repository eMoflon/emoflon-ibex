package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

/**
 * A {@link DeleteConflictResStrategy} that expects an action from the user
 * respectively a model change to be performed in order to repair the models so
 * that the conflict will be resolved.
 *
 */
public class ActAndLetRepairCRS extends DeleteConflictResStrategy {

	public ActAndLetRepairCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		// TODO adrianm: implement
	}

}
