package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.emoflon.ibex.common.utils.EcoreUtils;
import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPatternInvocation;

/**
 * Utility methods to transform editor patterns to IBeX Patterns.
 */
public class EditorToIBeXPatternHelper {

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
			changedNodes.add(transformNode(editorNode));
		});

		List<IBeXNode> context = new ArrayList<IBeXNode>();
		EditorModelUtils.getReferencesByOperator(editorPattern, editorOperator).forEach(editorReference -> {
			changedEdges.add(transformEdge(editorReference, changedNodes, context));
		});
		context.sort(IBeXPatternUtils.sortByName);
		contextNodes.addAll(context);
	}

	/**
	 * Adds injectivity constraints to the pattern such that all nodes in the
	 * pattern must be disjoint.
	 * 
	 * @param ibexPattern
	 *            the context pattern
	 */
	public static void addInjectivityConstraints(final IBeXContextPattern ibexPattern) {
		List<IBeXNode> allNodes = IBeXPatternUtils.getAllNodes(ibexPattern);
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				addInjectivityConstraintIfNecessary(ibexPattern, allNodes.get(i), allNodes.get(j));
			}
		}
	}

	/**
	 * Adds an injectivity constraint for the nodes if their types are compatible.
	 * 
	 * @param ibexPattern
	 *            the context pattern
	 * @param node1
	 *            the first node
	 * @param node2
	 *            the second node
	 */
	private static void addInjectivityConstraintIfNecessary(final IBeXContextPattern ibexPattern, final IBeXNode node1,
			final IBeXNode node2) {
		if (EcoreUtils.areTypesCompatible(node1.getType(), node2.getType())) {
			IBeXNodePair nodePair = IBeXLanguageFactory.eINSTANCE.createIBeXNodePair();
			nodePair.getValues().add(node1);
			nodePair.getValues().add(node2);
			ibexPattern.getInjectivityConstraints().add(nodePair);
		}
	}

	/**
	 * Adds the nodes of the given map to the mappings of the invocation.
	 * 
	 * @param invocation
	 *            the invocation
	 * @param nodeMap
	 *            the mapping of nodes
	 */
	public static void addNodeMapping(final IBeXPatternInvocation invocation, final Map<IBeXNode, IBeXNode> nodeMap) {
		// sorting necessary for deterministic output
		nodeMap.keySet().stream().sorted(IBeXPatternUtils.sortByName).forEach(k -> {
			invocation.getMapping().put(k, nodeMap.get(k));
		});
	}
}
