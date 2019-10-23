package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;

public abstract class ConflictResolutionStrategy {

	public ConflictResolutionStrategy(ConflResStratToken token) {
	}

	/**
	 * Applies this conflict resolution strategy to the models.
	 * 
	 * @param integrate INTEGRATE
	 * @return List of <code>Notification</code>s representing user deltas which
	 *         were reverted as part of strategy application
	 */
	public abstract List<Notification> apply(INTEGRATE integrate);

}
