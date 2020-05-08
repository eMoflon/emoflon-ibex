package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.HierarchicalConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.RelatedConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ConflictResolutionStrategy;

public class CRSHelper {

	public static <T extends Conflict> List<ConflictResolutionStrategy> forEachResolve(GeneralConflict conflict,
			Class<T> conflictClass, Class<? extends ConflictResolutionStrategy<T>> crsClass) {
		List<ConflictResolutionStrategy> crsList = new LinkedList<>();
		if (conflict instanceof HierarchicalConflict) {

		} else if (conflict instanceof RelatedConflict) {

		} else if (conflict instanceof Conflict) {
			if (conflictClass.isInstance(conflict)) {
				try {
					crsList.add(crsClass.getConstructor(conflictClass).newInstance(conflict));
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
		return crsList;
	}

}
