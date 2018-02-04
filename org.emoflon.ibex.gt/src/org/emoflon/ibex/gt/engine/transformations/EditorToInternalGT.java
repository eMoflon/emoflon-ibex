package org.emoflon.ibex.gt.engine.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Reference;
import org.emoflon.ibex.gt.editor.gT.Rule;

import GTLanguage.GTEdge;
import GTLanguage.GTGraph;
import GTLanguage.GTLanguageFactory;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;

/**
 * Transformation from the editor file (which conforms to the GT.ecore
 * meta-model generated from the Xtext specification) to the internal GT model
 * (which conforms to the GTLanguage.ecore meta-model).
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class EditorToInternalGT {

	/**
	 * Transforms an editor file into a GTRuleSet of the internal GT model.
	 * 
	 * @param file
	 *            the editor file model, must not be <code>null</code>
	 * @return the GTRuleSet
	 */
	public static GTRuleSet transformRuleSet(final GraphTransformationFile file) {
		Objects.requireNonNull(file, "file must not be null!");
		GTRuleSet gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		file.getRules().forEach(rule -> gtRuleSet.getRules().add(transformRule(rule)));
		return gtRuleSet;
	}

	/**
	 * Transforms an editor rule into a GTRule of the internal GT model.
	 * 
	 * @param editorRule
	 *            the editor rule, must not be <code>null</code>
	 * @return the GTRule
	 */
	public static GTRule transformRule(final Rule editorRule) {
		Objects.requireNonNull(editorRule, "rule must not be null!");
		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorRule.getName());

		GTGraph gtGraph = GTLanguageFactory.eINSTANCE.createGTGraph();
		gtRule.setGraph(gtGraph);

		// Transform nodes and edges.
		editorRule.getNodes().forEach(editorNode -> gtGraph.getNodes().add(transformNode(editorNode)));
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getEdges().addAll(transformReferencesToEdges(editorNode, gtGraph.getNodes()));
		});

		return gtRule;
	}

	/**
	 * Transforms an editor node into a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the GTNode
	 */
	private static GTNode transformNode(final Node editorNode) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		return gtNode;
	}

	/**
	 * Transforms the edges of an editor node into GTEdges.
	 * 
	 * @param editorNode
	 *            the editor node to transform, must not be <code>null</code>
	 * @param gtNodes
	 *            the list of GTNodes, which maybe source and target nodes of the
	 *            edges to be created
	 * @return the list of GTEdges
	 */
	private static List<GTEdge> transformReferencesToEdges(final Node editorNode, final List<GTNode> gtNodes) {
		Objects.requireNonNull(editorNode, "editor node must not be null!");
		Objects.requireNonNull(gtNodes, "node list must not be null!");
		List<GTEdge> gtEdges = new ArrayList<GTEdge>();
		List<Reference> references = extractReferences(editorNode);
		references.forEach(reference -> {
			String sourceNodeName = editorNode.getName();
			String targetNodeName = reference.getTarget().getName();

			Optional<GTNode> gtSourceNode = findGTNodeWithName(gtNodes, sourceNodeName);
			Optional<GTNode> gtTargetNode = findGTNodeWithName(gtNodes, targetNodeName);
			if (!gtSourceNode.isPresent()) {
				throw new IllegalStateException("Could not find node " + sourceNodeName + "!");
			}
			/* TODO: This can fail for rules with refinement if the target is from parent rule
			if (!gtTargetNode.isPresent()) {
				throw new IllegalStateException("Could not find node " + targetNodeName + "!");
			}*/

			GTEdge gtEdge = GTLanguageFactory.eINSTANCE.createGTEdge();
			gtEdge.setType(reference.getType());
			gtEdge.setName(sourceNodeName + "-" + gtEdge.getType().getName() + "-" + targetNodeName);
			gtSourceNode.ifPresent(node -> gtEdge.setSourceNode(node));
			gtTargetNode.ifPresent(node -> gtEdge.setTargetNode(node));

			gtEdges.add(gtEdge);
		});
		return gtEdges;
	}

	/**
	 * Searches for a GTNode with the given name in the given list.
	 * 
	 * @param nodes
	 *            the node list to search in. The list can be empty, but must not be
	 *            <code>null</code>.
	 * @param name
	 *            the node name to search for, must not be <code>null</code>.
	 * @return an Optional for the node to find
	 */
	public static Optional<GTNode> findGTNodeWithName(final List<GTNode> nodes, final String name) {
		Objects.requireNonNull(nodes, "node list must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return nodes.stream().filter(gtNode -> name.equals(gtNode.getName())).findAny();
	}

	/**
	 * Returns all references of an editor node.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the References which are the constraint list of the editor node
	 */
	public static List<Reference> extractReferences(final Node editorNode) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		return editorNode.getConstraints().stream().filter(x -> x instanceof Reference).map(x -> (Reference) x)
				.collect(Collectors.toList());
	}
}
