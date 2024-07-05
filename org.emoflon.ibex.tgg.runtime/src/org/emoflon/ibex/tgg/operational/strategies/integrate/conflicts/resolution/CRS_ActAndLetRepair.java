package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;

import delta.DeltaContainer;

public interface CRS_ActAndLetRepair extends ConflictResolutionStrategy {
	void crs_actAndLetRepair(BiConsumer<EObject, EObject> delta);
	void crs_actAndLetRepair(DeltaContainer delta);
}
