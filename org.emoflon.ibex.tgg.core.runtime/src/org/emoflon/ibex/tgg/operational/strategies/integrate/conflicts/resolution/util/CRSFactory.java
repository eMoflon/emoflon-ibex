package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AsymmetricConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.PartlyDelConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ActAndLetRepairCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.ConflictResolutionStrategy;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.DeleteRemainingCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.MergeAndPreserveCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.PreferSourceCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.PreferTargetCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.PreserveDeletionCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.RestoreCRS;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.RevokeDeletionCRS;

import delta.DeltaContainer;

public class CRSFactory {

	public static ConflictResolutionStrategy preserveDeletion(DeletePropConflict conflict) {
		return new PreserveDeletionCRS(conflict);
	}
	
	public static ConflictResolutionStrategy revokeDeletion(DeletePropConflict conflict) {
		return new RevokeDeletionCRS(conflict);
	}
	
	public static ConflictResolutionStrategy mergeAndPreserve(DeletePropConflict conflict) {
		return new MergeAndPreserveCRS(conflict);
	}
	
	public static ConflictResolutionStrategy preferSource(AttributeConflict conflict) {
		return new PreferSourceCRS(conflict);
	}
	
	public static ConflictResolutionStrategy preferTarget(AttributeConflict conflict) {
		return new PreferTargetCRS(conflict);
	}

	public static ConflictResolutionStrategy deleteRemaining(PartlyDelConflict conflict) {
		return new DeleteRemainingCRS(conflict);
	}

	public static ConflictResolutionStrategy restore(PartlyDelConflict conflict) {
		return new RestoreCRS(conflict);
	}

	public static ConflictResolutionStrategy actAndLetRepair(AsymmetricConflict conflict, BiConsumer<EObject, EObject> delta) {
		return new ActAndLetRepairCRS(conflict, delta);
	}

	public static ConflictResolutionStrategy actAndLetRepair(AsymmetricConflict conflict, DeltaContainer delta) {
		return new ActAndLetRepairCRS(conflict, delta);
	}

}
