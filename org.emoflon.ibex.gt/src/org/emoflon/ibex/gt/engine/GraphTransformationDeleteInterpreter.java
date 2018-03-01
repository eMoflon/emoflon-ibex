package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXNode;

/**
 * Interpreter applying deletion of elements for graph transformation.
 */
public class GraphTransformationDeleteInterpreter implements IDeletePatternInterpreter {
	private Resource trashResource;

	/**
	 * Creates a new GraphTransformationDeleteInterpreter.
	 * 
	 * @param trashResource
	 *            the resource containing trash objects
	 */
	public GraphTransformationDeleteInterpreter(final Resource trashResource) {
		this.trashResource = trashResource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXDeletePattern deletePattern, final IMatch match) {
		deletePattern.getDeletedNodes().forEach(node -> this.deleteNode(node, match));
		deletePattern.getDeletedEdges().forEach(edge -> this.deleteEdge(edge, match));
		return Optional.of(match);
	}

	/**
	 * Deletes the node.
	 * 
	 * @param node
	 *            the node to delete
	 * @param match
	 *            the match
	 */
	private void deleteNode(final IBeXNode node, final IMatch match) {
		EObject eObject = (EObject) match.get(node.getName());
		if (eObject.eResource() == null) { // Is dangling node?
			// Move to trash resource.
			trashResource.getContents().add(EcoreUtil.getRootContainer(eObject));
		}
		EcoreUtil.delete(eObject);
	}

	/**
	 * Deletes the edge.
	 * 
	 * @param edge
	 *            the edge to delete
	 * @param match
	 *            the match
	 */
	private void deleteEdge(final IBeXEdge edge, final IMatch match) {
		EObject src = (EObject) match.get(edge.getSourceNode().getName());
		EObject trg = (EObject) match.get(edge.getTargetNode().getName());
		deleteEdge(src, trg, edge.getType());
	}

	// TODO copied code
	@SuppressWarnings("rawtypes")
	private static void deleteEdge(EObject src, EObject trg, EReference ref) {
		if (src.eResource() == null) {
			return;
		}
		if (ref.isMany()) {
			EList list = ((EList) src.eGet(ref));
			if (list.contains(trg)) {
				list.remove(trg);
			}
		} else {
			if (src.eGet(ref) != null) {
				src.eUnset(ref);
			}
		}
	}
}
