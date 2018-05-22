package org.emoflon.ibex.common.utils;

import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Utility methods for manipulating EMF models.
 */
public class EMFManipulationUtils {

	/**
	 * Checks whether the given object is a dangling node.
	 * 
	 * @param eObject
	 *            the node
	 * @return true if the node is a dangling node
	 */
	public static boolean isDanglingNode(final EObject eObject) {
		return eObject.eResource() == null;
	}

	/**
	 * Checks whether the given object is a dangling node.
	 * 
	 * @param eObject
	 *            an {@link Optional} for a node
	 * @return true if the node is a dangling node; false if the node is not a
	 *         dangling node or the Optional is not present
	 */
	public static boolean isDanglingNode(Optional<EObject> eObject) {
		return eObject.map(EMFManipulationUtils::isDanglingNode).orElse(false);
	}

	/**
	 * Creates a reference between the two objects
	 * 
	 * @param source
	 *            the source node
	 * @param target
	 *            the target node
	 * @param reference
	 *            the edge type
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createEdge(final EObject source, final EObject target, final EReference reference) {
		if (reference.isMany()) {
			((EList) source.eGet(reference)).add(target);
		} else {
			source.eSet(reference, target);
		}
	}

	/**
	 * Delete the given EReference between the two objects.
	 * 
	 * @param source
	 *            the source node
	 * @param target
	 *            the target node
	 * @param reference
	 *            the edge type
	 */
	@SuppressWarnings("rawtypes")
	public static void deleteEdge(final EObject source, final EObject target, final EReference reference) {
		if (source.eResource() == null) {
			return;
		}
		if (reference.isMany()) {
			EList list = ((EList) source.eGet(reference));
			if (list.contains(target)) {
				list.remove(target);
			}
		} else {
			if (source.eGet(reference) != null) {
				source.eUnset(reference);
			}
		}
	}

	/**
	 * Deletes the given nodes and edges in the following order:
	 * <ol>
	 * <li>the regular edges</li>
	 * <li>the nodes</li>
	 * <li>the containment edges</li>
	 * </ol>
	 * 
	 * @param nodesToDelete
	 *            the nodes marked for deletion
	 * @param edgesToDelete
	 *            the edges marked for deletion
	 * @param trashResource
	 *            the resource resource whether dangling nodes are moved to
	 */
	public static void delete(final Set<EObject> nodesToDelete, final Set<EMFEdge> edgesToDelete,
			final Resource trashResource) {
		deleteEdges(edgesToDelete, false);
		deleteNodes(nodesToDelete, trashResource);
		deleteEdges(edgesToDelete, true);
	}

	/**
	 * Deletes the given nodes.
	 * 
	 * @param nodesToDelete
	 *            the nodes marked for deletion
	 * @param trashResource
	 *            the resource resource whether dangling nodes are moved to
	 */
	private static void deleteNodes(final Set<EObject> nodesToDelete, final Resource trashResource) {
		for (EObject node : nodesToDelete) {
			if (isDanglingNode(node)) {
				// Move node to trash resource.
				trashResource.getContents().add(EcoreUtil.getRootContainer(node));
			}
			EcoreUtil.delete(node);
		}
	}

	/**
	 * Deletes the edges whose type has the containment set to the given value.
	 * 
	 * @param edgesToDelete
	 *            the edges marked for deletion
	 * @param containment
	 *            the containment setting
	 */
	private static void deleteEdges(final Set<EMFEdge> edgesToDelete, boolean containment) {
		for (EMFEdge edge : edgesToDelete) {
			if (edge.getType().isContainment() == containment) {
				deleteEdge(edge.getSource(), edge.getTarget(), edge.getType());
			}
		}
	}
}
