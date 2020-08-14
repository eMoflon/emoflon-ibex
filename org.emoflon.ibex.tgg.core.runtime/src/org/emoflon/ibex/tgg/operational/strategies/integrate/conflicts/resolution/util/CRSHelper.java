package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.util.function.Consumer;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ConflictResolutionStrategy;

public class CRSHelper {

	public static <S extends ConflictResolutionStrategy, C extends S> void forEachResolve(
			ConflictContainer conflictContainer, Class<C> conflictClass, Consumer<S> crs) {
		for (Conflict conflict : conflictContainer.getConflicts())
			forConflict(conflict, conflictClass, crs);
		for (ConflictContainer container : conflictContainer.getSubContainers())
			forEachResolve(container, conflictClass, crs);
	}

	@SuppressWarnings("unchecked")
	private static <S extends ConflictResolutionStrategy, C extends S> void forConflict(Conflict conflict,
			Class<C> conflictClass, Consumer<S> crs) {
		if (conflictClass.isInstance(conflict)) {
			try {
				crs.accept((S) conflict);
			} catch (ClassCastException e) {
				return;
			}
		}
	}

}
