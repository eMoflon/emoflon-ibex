package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.Link;
import delta.StructuralDelta;

public class ModelChangeUtil {

	@SuppressWarnings("unchecked")
	public static void deleteElement(EObject element, boolean deleteContainedChildren) {
		if(isDangling(element))
			return;
		
		Set<EObject> nodesToDelete = new HashSet<>();
		nodesToDelete.add(element);
		
		if (deleteContainedChildren) {
			EMFManipulationUtils.delete(nodesToDelete, Collections.EMPTY_SET, o -> {}, true);
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
			EMFManipulationUtils.delete(nodesToDelete, Collections.EMPTY_SET, o -> {}, false);
		}
	}

	@SuppressWarnings("unchecked")
	public static void createEdge(EMFEdge edge) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return;
		
		if (edge.getType().isMany()) {
			Collection<EObject> feature = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			feature.add(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), edge.getTarget());
	}

	@SuppressWarnings("unchecked")
	public static void deleteEdge(EMFEdge edge) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return;
		if(isDangling(edge.getSource()))
			return;
		if (edge.getType().isMany()) {
			Collection<EObject> value = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			value.remove(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), null);
	}

	public static void revertAttributeChange(AttributeChange attributeChange) {
		attributeChange.getElement().eSet(attributeChange.getAttribute(), attributeChange.getOldValue());
	}

	public static void applyUserDelta(DeltaContainer deltas) {
		deltas.getDeltas().forEach(delta -> apply(delta));
	}

	private static void apply(Delta delta) {
		delta.getAttributeDeltas().forEach(attrDelta -> applyAttributeDelta(attrDelta));
		StructuralDelta strDelta = delta.getStructuralDelta();
		if (strDelta != null)
			applyStructuralDelta(strDelta);
	}

	private static void applyAttributeDelta(AttributeDelta attrDelta) {
		attrDelta.getObject().eSet(attrDelta.getAttribute(), attrDelta.getNewValue());
	}

	private static void applyStructuralDelta(StructuralDelta strDelta) {
		strDelta.getDeletedObjects().forEach(obj -> deleteElement(obj, false));
		strDelta.getDeletedLinks().forEach(link -> deleteEdge(createEMFEdgeFromLink(link)));

		// adrianm: if there are any problems with Democles, try create containment edges first
		strDelta.getCreatedLinks().forEach(link -> createEdge(createEMFEdgeFromLink(link)));
	}

	private static EMFEdge createEMFEdgeFromLink(Link link) {
		return new EMFEdge(link.getSrc(), link.getTrg(), link.getType());
	}

	private static boolean isDangling(EObject object) {
		return object.eResource() == null;
	}

}
