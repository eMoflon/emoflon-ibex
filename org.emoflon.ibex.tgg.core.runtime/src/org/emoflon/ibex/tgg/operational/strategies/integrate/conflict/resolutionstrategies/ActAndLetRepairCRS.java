package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.io.IOException;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.delta.validation.InvalidDeltaException;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.provider.IntegrationFragmentProvider;

import delta.DeltaContainer;

/**
 * A {@link DeleteConflictResStrategy} that expects an action from the user
 * respectively a model change to be performed in order to repair the models so
 * that the conflict will be resolved.
 *
 */
public class ActAndLetRepairCRS extends DeleteConflictResStrategy {

	private BiConsumer<EObject, EObject> deltaConsumer = null;
	private DeltaContainer deltaContainer = null;

	public ActAndLetRepairCRS(DeleteConflict conflict, ConflResStratToken token, BiConsumer<EObject, EObject> delta) {
		super(conflict, token);
		this.deltaConsumer = delta;
	}

	public ActAndLetRepairCRS(DeleteConflict conflict, ConflResStratToken token, DeltaContainer delta) {
		super(conflict, token);
		this.deltaContainer = delta;
	}

	@Override
	public void apply(INTEGRATE integrate) {
		try {
			if (deltaConsumer != null)
				integrate.applyDelta(deltaConsumer);
			if (deltaContainer != null)
				integrate.applyDelta(deltaContainer);
		} catch (InvalidDeltaException e1) {
			e1.printStackTrace();
		}
		
		try {
			IntegrationFragmentProvider.APPLY_USER_DELTA.apply(integrate);
			IntegrationFragmentProvider.REPAIR.apply(integrate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
