package org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;

public class ModelChangeUtil {

	@SuppressWarnings("unchecked")
	public static void deleteElement(EObject element, boolean deleteContainedChildren) {
		if (isDangling(element))
			return;

		Set<EObject> nodesToDelete = new HashSet<>();
		nodesToDelete.add(element);

		if (deleteContainedChildren) {
			// TODO adrianM: this will only work with democles if you delete correctly using a trash resource!
			EMFManipulationUtils.delete(nodesToDelete, Collections.EMPTY_SET, true, true);
		} else {
			element.eClass().getEAllContainments().forEach(feature -> {
				Object content = element.eGet(feature);
				if (content instanceof Collection) {
					Collection<EObject> contentList = (Collection<EObject>) content;
					element.eResource().getContents().addAll(contentList);
					contentList.clear();
				} else if (content instanceof EObject obj) {
					element.eResource().getContents().add(obj);
					element.eSet(feature, null);
				}
			});
			EMFManipulationUtils.delete(nodesToDelete, Collections.EMPTY_SET, false, true);
		}
	}

	@SuppressWarnings("unchecked")
	public static void createEdge(EMFEdge edge) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return;

		if (edge.getType().isMany()) {
			Collection<EObject> value = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			value.add(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), edge.getTarget());
	}

	@SuppressWarnings("unchecked")
	public static void deleteEdge(EMFEdge edge) {
		if (edge.getSource() == null || edge.getTarget() == null)
			return;
		if (isDangling(edge.getSource()))
			return;
		if (edge.getType().isContainment()) {
			edge.getTarget().eResource().getContents().add(edge.getTarget());
			return;
		}
		if (edge.getType().isMany()) {
			Collection<EObject> value = (Collection<EObject>) edge.getSource().eGet(edge.getType());
			value.remove(edge.getTarget());
		} else
			edge.getSource().eSet(edge.getType(), null);
	}

	public static void revertAttributeChange(AttributeChange attributeChange) {
		attributeChange.getElement().eSet(attributeChange.getAttribute(), attributeChange.getOldValue());
	}

	private static boolean isDangling(EObject object) {
		return object.eResource() == null;
	}

}
