package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.operational.ICreatePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXNode;

/**
 * Interpreter applying creation of elements for graph transformation.
 */
public class GraphTransformationCreateInterpreter implements ICreatePatternInterpreter {
	private Resource defaultResource;

	/**
	 * Creates a new GraphTransformationCreateInterpreter.
	 * 
	 * @param defaultResource
	 *            the default resource
	 */
	public GraphTransformationCreateInterpreter(final Resource defaultResource) {
		this.defaultResource = defaultResource;
	}

	@Override
	public Optional<IMatch> apply(final IBeXCreatePattern createPattern, final IMatch match) {
		createPattern.getCreatedNodes().forEach(node -> this.createNode(node, match));
		createPattern.getCreatedEdges().forEach(edge -> this.createEdge(edge, match));
		return Optional.of(match);
	}

	/**
	 * Creates an EObject for the given node and adds the element to the match.
	 * 
	 * @param node
	 *            the node to create
	 * @param match
	 *            the match
	 */
	private void createNode(final IBeXNode node, final IMatch match) {
		EObject newNode = EcoreUtil.create(node.getType());
		this.defaultResource.getContents().add(newNode);
		match.put(node.getName(), newNode);
	}

	/**
	 * Creates an EReference for the given edge.
	 * 
	 * @param edge
	 *            the edge to create
	 * @param match
	 *            the match
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createEdge(final IBeXEdge edge, final IMatch match) {
		EObject src = (EObject) match.get(edge.getSourceNode().getName());
		EObject trg = (EObject) match.get(edge.getTargetNode().getName());

		EReference reference = edge.getType();
		if (reference.isMany()) {
			((EList) src.eGet(reference)).add(trg);
		} else {
			src.eSet(reference, trg);
		}
	}
}
