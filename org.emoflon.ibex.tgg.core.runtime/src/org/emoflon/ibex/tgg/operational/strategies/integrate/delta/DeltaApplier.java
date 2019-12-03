package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.DeltaFactory;
import delta.Link;
import delta.StructuralDelta;

public class DeltaApplier {

	private OperationalStrategy opStrategy;

	private Set<AttributeDelta> invertAttributeDeltas;

	public DeltaApplier(OperationalStrategy opStrategy) {
		this.opStrategy = opStrategy;

		invertAttributeDeltas = new HashSet<>();
	}

	public void apply(DeltaContainer deltas) {
		deltas.getDeltas().forEach(delta -> apply(delta));
	}

	private void apply(Delta delta) {
		delta.getAttributeDeltas().forEach(attrDelta -> applyAttributeDelta(attrDelta));
		StructuralDelta strDelta = delta.getStructuralDelta();
		if(strDelta != null)
			applyStructuralDelta(strDelta);
	}

	private void applyAttributeDelta(AttributeDelta attrDelta) {
		storeOldValue(attrDelta);
		attrDelta.getObject().eSet(attrDelta.getAttribute(), attrDelta.getNewValue());
	}

	private void storeOldValue(AttributeDelta attrDelta) {
		AttributeDelta invAttrDelta = DeltaFactory.eINSTANCE.createAttributeDelta();
		invAttrDelta.setAttribute(attrDelta.getAttribute());
		invAttrDelta.setObject(attrDelta.getObject());
		invAttrDelta.setNewValue(attrDelta.getObject().eGet(attrDelta.getAttribute()));
		invertAttributeDeltas.add(invAttrDelta);
	}

	private void applyStructuralDelta(StructuralDelta strDelta) {
		applyCreations(strDelta.getCreatedObjects(), strDelta.getCreatedLinks());
		applyDeletions(strDelta.getDeletedObjects(), strDelta.getDeletedLinks());
	}

	private void applyDeletions(List<EObject> deletedObjects, List<Link> deletedLinks) {
		// TODO adrianm: implement
		
	}

	private void applyCreations(List<EObject> createdObjects, List<Link> createdLinks) {
		// TODO adrianm: implement
		
	}

}
