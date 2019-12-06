package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.Link;
import delta.StructuralDelta;

public class ModelChangeInterface {

	private INTEGRATE integrate;

	private ModelChanges userDeltas;

	public ModelChangeInterface(INTEGRATE integrate) {
		this.integrate = integrate;

		userDeltas = new ModelChanges();
	}

	public void applyUserDelta(DeltaContainer deltas) {
		deltas.getDeltas().forEach(delta -> apply(delta));
	}

	private void apply(Delta delta) {
		delta.getAttributeDeltas().forEach(attrDelta -> applyAttributeDelta(attrDelta));
		StructuralDelta strDelta = delta.getStructuralDelta();
		if (strDelta != null)
			applyStructuralDelta(strDelta);
	}

	private void applyAttributeDelta(AttributeDelta attrDelta) {
		userDeltas.addAttributeChange(new AttributeChange( //
				attrDelta.getObject(), //
				attrDelta.getAttribute(), //
				attrDelta.getObject().eGet(attrDelta.getAttribute()), //
				attrDelta.getNewValue()));

		attrDelta.getObject().eSet(attrDelta.getAttribute(), attrDelta.getNewValue());
	}

	private void applyStructuralDelta(StructuralDelta strDelta) {
		applyDeletions(strDelta.getDeletedObjects(), strDelta.getDeletedLinks());
		applyCreations(strDelta.getCreatedObjects(), strDelta.getCreatedLinks());
	}

	@SuppressWarnings("unchecked")
	private void applyDeletions(List<EObject> deletedObjects, List<Link> deletedLinks) {
		deletedObjects.forEach(obj -> {
			userDeltas.addDeletedElement(obj);

			obj.eClass().getEAllContainments().forEach(feature -> {
				Object content = obj.eGet(feature);
				if (content instanceof Collection) {
					Collection<EObject> contentList = (Collection<EObject>) content;
					obj.eResource().getContents().addAll(contentList);
					contentList.clear();
				} else if (content instanceof EObject) {
					obj.eResource().getContents().add((EObject) content);
					obj.eSet(feature, null);
				}
			});

			integrate.getModelChangeProtocol().util.deleteNode(obj).forEach(
					notification -> userDeltas.addAllDeletedEdges(getDeletedEdgesFromNotification(notification)));
		});

		deletedLinks.forEach(link -> {
			EMFEdge edge = createEMFEdgeFromLink(link);
			if (!userDeltas.isDeleted(edge)) {
				userDeltas.addDeletedEdge(edge);
				if (link.getType().isMany()) {
					Collection<EObject> feature = (Collection<EObject>) link.getSrc().eGet(link.getType());
					feature.remove(link.getTrg());
				} else
					link.getSrc().eSet(link.getType(), null);
			}

		});
	}

	@SuppressWarnings("unchecked")
	private void applyCreations(List<EObject> createdObjects, List<Link> createdLinks) {
		userDeltas.addAllCreatedElements(createdObjects);

		createdLinks.forEach(link -> {
			userDeltas.addCreatedEdge(createEMFEdgeFromLink(link));
			if (link.getType().isMany()) {
				Collection<EObject> feature = (Collection<EObject>) link.getSrc().eGet(link.getType());
				feature.add(link.getTrg());
			} else
				link.getSrc().eSet(link.getType(), link.getTrg());
		});
	}

	private EMFEdge createEMFEdgeFromLink(Link link) {
		return new EMFEdge(link.getSrc(), link.getTrg(), link.getType());
	}

	private Set<EMFEdge> getDeletedEdgesFromNotification(Notification notification) {
		Set<EMFEdge> edges = new HashSet<>();

		if (notification.getNotifier() instanceof EObject) {
			EObject src = (EObject) notification.getNotifier();
			if (notification.getFeature() instanceof EReference) {
				EReference type = (EReference) notification.getFeature();
				Object oldValue = notification.getOldValue();
				if (oldValue instanceof EObject)
					edges.add(new EMFEdge(src, (EObject) oldValue, type));
				else if (oldValue instanceof Collection)
					((Collection<?>) oldValue).forEach(val -> edges.add(new EMFEdge(src, (EObject) val, type)));
			}
		}

		return edges;
	}

}
