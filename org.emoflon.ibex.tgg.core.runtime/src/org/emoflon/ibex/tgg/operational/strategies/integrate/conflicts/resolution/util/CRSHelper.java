package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.HierarchicalConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.MatchConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.RelatedConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ConflictResolutionStrategy;

public class CRSHelper {

	public static <T extends Conflict> List<ConflictResolutionStrategy<?>> forEachResolve(GeneralConflict conflict,
			Class<T> conflictClass, Class<? extends ConflictResolutionStrategy<T>> crsClass) {
		List<ConflictResolutionStrategy<?>> crsList = new LinkedList<>();
		if (conflict instanceof HierarchicalConflict) {
			for (MatchConflict mConfl : ((HierarchicalConflict) conflict).getConflictDependency()) {
				if(mConfl instanceof RelatedConflict) {
					for (Conflict confl : ((RelatedConflict) mConfl).getRelatedConflicts()) {
						forConflict(crsList, confl, conflictClass, crsClass);
					}
				} else if (mConfl instanceof Conflict) {
					forConflict(crsList, mConfl, conflictClass, crsClass);
				}
			}
		} else if (conflict instanceof RelatedConflict) {
			for (Conflict confl : ((RelatedConflict) conflict).getRelatedConflicts()) {
				forConflict(crsList, confl, conflictClass, crsClass);
			}
		} else if (conflict instanceof Conflict) {
			forConflict(crsList, conflict, conflictClass, crsClass);
		}
		return crsList;
	}

	private static <T extends Conflict> void forConflict(List<ConflictResolutionStrategy<?>> crsList,
			GeneralConflict conflict, Class<T> conflictClass, Class<? extends ConflictResolutionStrategy<T>> crsClass) {
		if (conflictClass.isInstance(conflict)) {
			try {
				crsList.add(crsClass.getConstructor(conflictClass).newInstance(conflict));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

}
