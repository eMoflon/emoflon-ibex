package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXInjectivityConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.moflon.core.utilities.EcoreUtils;


/**
 * Utility methods to transform editor patterns to IBeX Patterns.
 */
final public class EditorToIBeXPatternHelper {

//	/**
//	 * Transforms the given editor reference into an {@link IBeXEdge}. If a source
//	 * or target node does not exist in the lists of changed or context nodes, the
//	 * node will be added to the context nodes.
//	 * 
//	 * @param editorReference
//	 *            the reference
//	 * @param changedNodes
//	 *            the list of nodes
//	 * @param contextNodes
//	 *            the list of nodes where
//	 * @return the transformed edge
//	 */
//	public Optional<IBeXEdge> transformEdge(final EditorReference editorReference, final List<IBeXNode> changedNodes,
//			final List<IBeXNode> contextNodes) {
//		EditorNode editorSourceNode = EditorModelUtils.getSourceNode(editorReference);
//		EditorNode editorTargetNode = editorReference.getTarget();
//
//		Objects.requireNonNull(editorReference, "Edge must not be null!");
//		Objects.requireNonNull(changedNodes, "Changed node must not be null!");
//		Objects.requireNonNull(contextNodes, "Context node must not be null!");
//
//		if (editorSourceNode == null || editorSourceNode.getName() == null) {
//			transformation.logError("Cannot resolve reference to source node.");
//			return Optional.empty();
//		}
//
//		if (editorTargetNode == null || editorTargetNode.getName() == null) {
//			transformation.logError("Cannot resolve reference to target node.");
//			return Optional.empty();
//		}
//
//		IBeXNode ibexSourceNode = addIBeXNodeToContextNodes(editorSourceNode, changedNodes, contextNodes);
//		IBeXNode ibexTargetNode = addIBeXNodeToContextNodes(editorTargetNode, changedNodes, contextNodes);
//		IBeXEdge ibexEdge = IBeXPatternFactory.createEdge(ibexSourceNode, ibexTargetNode, editorReference.getType());
//		return Optional.of(ibexEdge);
//	}

//	/**
//	 * Searches the IBeXNode with the same name as the given editor node within the
//	 * given node lists. If such an IBeXNode exists, it is returned, otherwise it
//	 * created and added to the context nodes.
//	 * 
//	 * @param editorNode
//	 *            the editor node
//	 * @param changedNodes
//	 *            the list of changed nodes of the pattern
//	 * @param contextNodes
//	 *            the list of context nodes of the pattern
//	 * @return the IBeXNode
//	 */
//	public static IBeXNode addIBeXNodeToContextNodes(final EditorNode editorNode, final List<IBeXNode> changedNodes,
//			final List<IBeXNode> contextNodes) {
//		Optional<IBeXNode> existingNode = IBeXPatternUtils.findIBeXNodeWithName(changedNodes, contextNodes,
//				editorNode.getName());
//		if (existingNode.isPresent()) {
//			return existingNode.get();
//		} else {
//			IBeXNode node = transformNode(editorNode);
//			contextNodes.add(node);
//			return node;
//		}
//	}

//	/**
//	 * Transforms an editor node into an IBeXNode.
//	 * 
//	 * @param editorNode
//	 *            the editor node
//	 * @return the IBeXNode
//	 */
//	public static IBeXNode transformNode(final EditorNode editorNode) {
//		Objects.requireNonNull(editorNode, "Node must not be null!");
//
//		return IBeXPatternFactory.createNode(editorNode.getName(), editorNode.getType());
//	}

//	/**
//	 * Transforms the nodes and edges of the given operator and adds them to the
//	 * correct lists.
//	 * 
//	 * @param editorPattern
//	 *            the editor pattern
//	 * @param editorOperator
//	 *            the editor operator
//	 * @param changedNodes
//	 *            the changed nodes
//	 * @param contextNodes
//	 *            the context nodes
//	 * @param changedEdges
//	 *            the changed edges
//	 */
//	public void transformNodesAndEdgesOfOperator(final EditorPattern editorPattern, final EditorOperator editorOperator,
//			final List<IBeXNode> changedNodes, final List<IBeXNode> contextNodes, final List<IBeXEdge> changedEdges) {
//		EditorModelUtils.getNodesByOperator(editorPattern, editorOperator).forEach(editorNode -> {
//			changedNodes.add(transformNode(editorNode));
//		});
//
//		List<IBeXNode> context = new ArrayList<IBeXNode>();
//		EditorModelUtils.getReferencesByOperator(editorPattern, editorOperator).forEach(editorReference -> {
//			transformEdge(editorReference, changedNodes, context) //
//					.ifPresent(ibexEdge -> changedEdges.add(ibexEdge));
//		});
//		context.sort(IBeXPatternUtils.sortByName);
//		contextNodes.addAll(context);
//	}

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
	public static void addInjectivityConstraintIfNecessary(final IBeXContextPattern ibexPattern, final IBeXNode node1,
			final IBeXNode node2) {
		if (EcoreUtils.areTypesCompatible(node1.getType(), node2.getType())) {
			IBeXInjectivityConstraint nodePair = IBeXPatternModelFactory.eINSTANCE.createIBeXInjectivityConstraint();
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
