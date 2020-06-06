package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.util.function.Consumer;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.HierarchicalConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.MatchConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.RelatedConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ConflictResolutionStrategy;

public class CRSHelper {

	public static <S extends ConflictResolutionStrategy, C extends S> void forEachResolve(GeneralConflict conflict,
			Class<C> conflictClass, Consumer<S> crs) {
		if (conflict instanceof HierarchicalConflict) {
			for (MatchConflict mConfl : ((HierarchicalConflict) conflict).getConflictDependency()) {
				if (mConfl instanceof RelatedConflict) {
					for (Conflict confl : ((RelatedConflict) mConfl).getRelatedConflicts()) {
						forConflict(confl, conflictClass, crs);
					}
				} else if (mConfl instanceof Conflict) {
					forConflict((Conflict) mConfl, conflictClass, crs);
				}
			}
		} else if (conflict instanceof RelatedConflict) {
			for (Conflict confl : ((RelatedConflict) conflict).getRelatedConflicts()) {
				forConflict(confl, conflictClass, crs);
			}
		} else if (conflict instanceof Conflict) {
			forConflict((Conflict) conflict, conflictClass, crs);
		}
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
