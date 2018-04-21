package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;

import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNamedElement;
import IBeXLanguage.IBeXNode;

/**
 * Utility methods from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternUtils {
	/**
	 * A comparator for IBeXNamedElements.
	 */
	public static final Comparator<IBeXNamedElement> sortByName = (a, b) -> a.getName().compareTo(b.getName());

	/**
	 * Transforms the given editor reference into an {@link IBeXEdge}. If a source
	 * or target node does not exist in the lists of changed or context nodes, the
	 * node will be added to the context nodes.
	 * 
	 * @param editorReference
	 *            the reference
	 * @param changedNodes
	 *            the list of nodes
	 * @param contextNodes
	 *            the list of nodes where
	 * @return the transformed edge
	 */
	public static IBeXEdge transformEdge(final EditorReference editorReference, final List<IBeXNode> changedNodes,
			final List<IBeXNode> contextNodes) {
		EditorNode editorSourceNode = EditorModelUtils.getSourceNode(editorReference);
		EditorNode editorTargetNode = editorReference.getTarget();

		Objects.requireNonNull(editorReference, "Edge must not be null!");
		Objects.requireNonNull(editorSourceNode, "Edge must have a source node!");
		Objects.requireNonNull(editorTargetNode, "Edge must have a target node!");
		Objects.requireNonNull(changedNodes, "Changed node must not be null!");
		Objects.requireNonNull(contextNodes, "Context node must not be null!");

		IBeXNode ibexSourceNode = addIBeXNodeToContextNodes(editorSourceNode, changedNodes, contextNodes);
		IBeXNode ibexTargetNode = addIBeXNodeToContextNodes(editorTargetNode, changedNodes, contextNodes);

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(editorReference.getType());
		ibexEdge.setSourceNode(ibexSourceNode);
		ibexEdge.setTargetNode(ibexTargetNode);
		return ibexEdge;
	}

	/**
	 * Searches the IBeXNode with the same name as the given editor node within the
	 * given node lists. If such an IBeXNode exists, it is returned, otherwise it
	 * created and added to the context nodes.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @param changedNodes
	 *            the list of changed nodes of the pattern
	 * @param contextNodes
	 *            the list of context nodes of the pattern
	 * @return the IBeXNode
	 */
	public static IBeXNode addIBeXNodeToContextNodes(final EditorNode editorNode, final List<IBeXNode> changedNodes,
			final List<IBeXNode> contextNodes) {
		Optional<IBeXNode> existingNode = IBeXPatternUtils.findIBeXNodeWithName(changedNodes, contextNodes,
				editorNode.getName());
		if (existingNode.isPresent()) {
			return existingNode.get();
		} else {
			IBeXNode node = transformNode(editorNode);
			contextNodes.add(node);
			return node;
		}
	}

	/**
	 * Transforms an editor node into an IBeXNode.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @return the IBeXNode
	 */
	public static IBeXNode transformNode(final EditorNode editorNode) {
		Objects.requireNonNull(editorNode, "Node must not be null!");

		IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		ibexNode.setName(editorNode.getName());
		ibexNode.setType(editorNode.getType());
		return ibexNode;
	}

	/**
	 * Transforms the nodes and edges of the given operator and adds them to the
	 * correct lists.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @param editorOperator
	 *            the editor operator
	 * @param changedNodes
	 *            the changed nodes
	 * @param contextNodes
	 *            the context nodes
	 * @param changedEdges
	 *            the changed edges
	 */
	public static void transformNodesAndEdgesOfOperator(final EditorPattern editorPattern,
			final EditorOperator editorOperator, final List<IBeXNode> changedNodes, final List<IBeXNode> contextNodes,
			final List<IBeXEdge> changedEdges) {
		EditorModelUtils.getNodesByOperator(editorPattern, editorOperator).forEach(editorNode -> {
			changedNodes.add(EditorToIBeXPatternUtils.transformNode(editorNode));
		});

		List<IBeXNode> context = new ArrayList<IBeXNode>();
		EditorModelUtils.getReferencesByOperator(editorPattern, editorOperator).forEach(editorReference -> {
			changedEdges.add(EditorToIBeXPatternUtils.transformEdge(editorReference, changedNodes, context));
		});
		context.sort(sortByName);
		contextNodes.addAll(context);
	}
}
