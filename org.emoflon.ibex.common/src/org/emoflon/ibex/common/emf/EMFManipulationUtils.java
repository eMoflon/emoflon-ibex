package org.emoflon.ibex.common.emf;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Utility methods for manipulating EMF models.
 */
public class EMFManipulationUtils {
	
	private static Logger logger = Logger.getLogger(EMFManipulationUtils.class);

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
		if(!reference.isChangeable()) {
			logger.debug(reference.getName() + " is not changeable, skipping...");
			return;
		}
		
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
	 * @param danglingNodeAction
	 *            the action to execute for dangling nodes
	 */
	public static void delete(final Set<EObject> nodesToDelete, final Set<EMFEdge> edgesToDelete,
			final Consumer<EObject> danglingNodeAction) {
		delete(nodesToDelete, edgesToDelete, danglingNodeAction, false);
	}
	
	public static void delete(final Set<EObject> nodesToDelete, final Set<EMFEdge> edgesToDelete,
			final Consumer<EObject> danglingNodeAction, boolean recursive) {
		deleteEdges(edgesToDelete, danglingNodeAction, false);
		deleteEdges(edgesToDelete, danglingNodeAction, true);
		deleteNodes(nodesToDelete, danglingNodeAction, recursive);
	}

	/**
	 * Deletes the given nodes.
	 * 
	 * @param nodesToDelete
	 *            the nodes marked for deletion
	 * @param danglingNodeAction
	 *            the action to execute for dangling nodes
	 */
	private static void deleteNodes(final Set<EObject> nodesToDelete, final Consumer<EObject> danglingNodeAction, boolean recursive) {
		for (EObject node : nodesToDelete) {
			if (isDanglingNode(node)) {
				danglingNodeAction.accept(node);
			}
			if(!node.eContents().isEmpty() && recursive)
				deleteNodes(node.eContents().stream().collect(Collectors.toSet()), danglingNodeAction, recursive);
		}
//		EcoreUtil.deleteAll(nodesToDelete, false);
		for (EObject eObject : nodesToDelete) {
			for (EReference ref : eObject.eClass().getEReferences()) {
				if(ref.isMany())
					((List<?>) eObject.eGet(ref)).clear();
				else
					eObject.eSet(ref, null);
			}
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
	private static void deleteEdges(final Set<EMFEdge> edgesToDelete, final Consumer<EObject> danglingNodeAction, boolean containment) {
		for (EMFEdge edge : edgesToDelete) {
			if (isContainment(edge.getType()) == containment) {
				if (isDanglingNode(edge.getSource())) {
					danglingNodeAction.accept(edge.getSource());
				}
				if (isDanglingNode(edge.getTarget())) {
					danglingNodeAction.accept(edge.getTarget());
				}
				deleteEdge(edge.getSource(), edge.getTarget(), edge.getType());
			}
		}
	}

	/**
	 * Checks whether the reference is a reference is not containment edge or its
	 * opposite.
	 * 
	 * @param reference
	 *            the reference
	 * @return true if and only if the reference is a containment or container
	 *         reference
	 */
	private static boolean isContainment(final EReference reference) {
		return reference.isContainment() || reference.isContainer();
	}
}
