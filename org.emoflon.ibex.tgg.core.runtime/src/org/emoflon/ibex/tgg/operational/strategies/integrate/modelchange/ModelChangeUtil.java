package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.Link;
import delta.StructuralDelta;

public class ModelChangeUtil {

	private final ModelChangeProtocol protocol;

	public ModelChangeUtil(ModelChangeProtocol protocol) {
		this.protocol = protocol;
	}

	@SuppressWarnings("unchecked")
	public void deleteElement(EObject element, boolean deleteContainedChildren) {
		if (deleteContainedChildren) {
			deleteElementAndContainedChildren(element);
		} else {
			element.eClass().getEAllContainments().forEach(feature -> {
				Object content = element.eGet(feature);
				if (content instanceof Collection) {
					Collection<EObject> contentList = (Collection<EObject>) content;
					element.eResource().getContents().addAll(contentList);
					contentList.clear();
				} else if (content instanceof EObject) {
					element.eResource().getContents().add((EObject) content);
					element.eSet(feature, null);
				}
			});
			EcoreUtil.delete(element, false);
		}
	}

	private void deleteElementAndContainedChildren(EObject element) {
		element.eContents().forEach(child -> deleteElementAndContainedChildren(child));
		EcoreUtil.delete(element, false);
	}

	@SuppressWarnings("unchecked")
	public void createEdge(EMFEdge edge) {
		if (edge.getType().isMany()) {
			Collection<EObject> feature = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			feature.add(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), edge.getTarget());
	}

	@SuppressWarnings("unchecked")
	public void deleteEdge(EMFEdge edge) {
		if (edge.getType().isMany()) {
			Collection<EObject> value = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			value.remove(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), null);
	}

	public void revertAttributeChange(AttributeChange attributeChange) {
		attributeChange.getElement().eSet(attributeChange.getAttribute(), attributeChange.getOldValue());
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
		attrDelta.getObject().eSet(attrDelta.getAttribute(), attrDelta.getNewValue());
	}

	private void applyStructuralDelta(StructuralDelta strDelta) {
		strDelta.getDeletedObjects().forEach(obj -> this.deleteElement(obj, false));
		strDelta.getDeletedLinks().forEach(link -> {
			EMFEdge edge = createEMFEdgeFromLink(link);
			if (!protocol.getCurrentModelChanges().getRawDeletedEdges().contains(edge))
				this.deleteEdge(edge);
		});

		// TODO adrianm: first create containment edges
		strDelta.getCreatedLinks().forEach(link -> this.createEdge(createEMFEdgeFromLink(link)));
	}

	private EMFEdge createEMFEdgeFromLink(Link link) {
		return new EMFEdge(link.getSrc(), link.getTrg(), link.getType());
	}

}
