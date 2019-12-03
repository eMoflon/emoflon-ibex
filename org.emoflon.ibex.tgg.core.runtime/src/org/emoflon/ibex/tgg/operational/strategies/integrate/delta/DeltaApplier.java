package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.DeltaFactory;
import delta.Link;
import delta.StructuralDelta;

public class DeltaApplier {

	private Set<AttributeDelta> inverseAttributeDeltas;

	public DeltaApplier() {
		inverseAttributeDeltas = new HashSet<>();
	}

	public void apply(DeltaContainer deltas) {
		deltas.getDeltas().forEach(delta -> apply(delta));
	}

	private void apply(Delta delta) {
		delta.getAttributeDeltas().forEach(attrDelta -> applyAttributeDelta(attrDelta));
		StructuralDelta strDelta = delta.getStructuralDelta();
		if (strDelta != null)
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
		inverseAttributeDeltas.add(invAttrDelta);
	}

	private void applyStructuralDelta(StructuralDelta strDelta) {
		applyCreations(strDelta.getCreatedObjects(), strDelta.getCreatedLinks());
		applyDeletions(strDelta.getDeletedObjects(), strDelta.getDeletedLinks());
	}

	@SuppressWarnings("unchecked")
	private void applyCreations(List<EObject> createdObjects, List<Link> createdLinks) {
		createdLinks.forEach(link -> {
			if (link.getType().isMany()) {
				Collection<EObject> feature = (Collection<EObject>) link.getSrc().eGet(link.getType());
				feature.add(link.getTrg());
			} else
				link.getSrc().eSet(link.getType(), link.getTrg());
		});
	}

	@SuppressWarnings("unchecked")
	private void applyDeletions(List<EObject> deletedObjects, List<Link> deletedLinks) {
		deletedLinks.forEach(link -> {
			if (link.getType().isMany()) {
				Collection<EObject> feature = (Collection<EObject>) link.getSrc().eGet(link.getType());
				feature.remove(link.getTrg());
			} else
				link.getSrc().eSet(link.getType(), null);
		});
		
		deletedObjects.forEach(obj -> {
			EcoreUtil.delete(obj, true);
		});
	}

}
