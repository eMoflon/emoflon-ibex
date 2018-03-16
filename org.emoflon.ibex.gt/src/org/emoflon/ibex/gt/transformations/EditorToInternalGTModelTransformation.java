package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.editor.gT.Node;
import org.emoflon.ibex.gt.editor.gT.Operator;
import org.emoflon.ibex.gt.editor.gT.Rule;

import GTLanguage.GTBindingType;
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
 */
public class EditorToInternalGTModelTransformation
		extends AbstractModelTransformation<GraphTransformationFile, GTRuleSet> {
	/**
	 * The rules transformed into the internal model.
	 */
	private List<GTRule> gtRules;

	@Override
	public GTRuleSet transform(final GraphTransformationFile editorModel) {
		Objects.requireNonNull(editorModel, "The editor model must not be null!");
		this.gtRules = new ArrayList<GTRule>();
		editorModel.getRules().forEach(editorRule -> this.transformRule(editorRule));

		GTRuleSet gtRuleSet = GTLanguageFactory.eINSTANCE.createGTRuleSet();
		gtRuleSet.getRules().addAll(gtRules.stream() //
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())) //
				.collect(Collectors.toList()));
		return gtRuleSet;
	}

	/**
	 * Transforms an editor rule into a GTRule of the internal GT model.
	 * 
	 * @param editorRule
	 *            the editor rule, must not be <code>null</code>
	 */
	private void transformRule(final Rule editorRule) {
		Objects.requireNonNull(editorRule, "rule must not be null!");

		// Transform nodes and edges.
		GTGraph gtGraph = GTLanguageFactory.eINSTANCE.createGTGraph();
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getNodes().add(this.transformNode(editorNode));
		});
		editorRule.getNodes().forEach(editorNode -> {
			gtGraph.getEdges().addAll(this.transformReferencesToEdges(editorNode, gtGraph.getNodes()));
		});

		GTRule gtRule = GTLanguageFactory.eINSTANCE.createGTRule();
		gtRule.setName(editorRule.getName());
		gtRule.setAbstract(editorRule.isAbstract());
		gtRule.setExecutable(hasOperatorOrReference(editorRule));
		gtRule.setGraph(gtGraph);
		this.gtRules.add(gtRule);
	}

	/**
	 * Checks whether the editor rule contains at least one operator node or
	 * reference.
	 * 
	 * @param editorRule
	 *            the editor rule
	 * @return true if the rule contains an operator node.
	 */
	private static boolean hasOperatorOrReference(final Rule editorRule) {
		boolean hasOperatorNode = editorRule.getNodes().stream() //
				.anyMatch(node -> node.getOperator() != Operator.CONTEXT);
		return hasOperatorNode || editorRule.getNodes().stream() //
				.map(node -> node.getReferences()).flatMap(references -> references.stream())
				.anyMatch(reference -> reference.getOperator() != Operator.CONTEXT);
	}

	/**
	 * Transforms an editor node into a GTNode.
	 * 
	 * @param editorNode
	 *            the editor node, must not be <code>null</code>
	 * @return the GTNode
	 */
	private GTNode transformNode(final Node editorNode) {
		Objects.requireNonNull(editorNode, "node must not be null!");
		GTNode gtNode = GTLanguageFactory.eINSTANCE.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		gtNode.setLocal(editorNode.getName().startsWith("_"));
		gtNode.setBindingType(convertOperatorToBindingType(editorNode.getOperator()));
		return gtNode;
	}

	/**
	 * Converts the operator to the binding type.
	 * 
	 * @param operator
	 *            the operator
	 * @return the binding type
	 */
	private static GTBindingType convertOperatorToBindingType(Operator operator) {
		switch (operator) {
		case CREATE:
			return GTBindingType.CREATE;
		case DELETE:
			return GTBindingType.DELETE;
		case CONTEXT:
		default:
			return GTBindingType.CONTEXT;
		}
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
	private List<GTEdge> transformReferencesToEdges(final Node editorNode, final List<GTNode> gtNodes) {
		Objects.requireNonNull(editorNode, "editor node must not be null!");
		Objects.requireNonNull(gtNodes, "node list must not be null!");
		List<GTEdge> gtEdges = new ArrayList<GTEdge>();
		editorNode.getReferences().forEach(reference -> {
			String sourceNodeName = editorNode.getName();
			String targetNodeName = reference.getTarget().getName();

			Optional<GTNode> gtSourceNode = findGTNodeWithName(gtNodes, sourceNodeName);
			Optional<GTNode> gtTargetNode = findGTNodeWithName(gtNodes, targetNodeName);
			if (!gtSourceNode.isPresent()) {
				this.logError("Could not find node " + sourceNodeName + "!");
			}
			if (!gtTargetNode.isPresent()) {
				this.logError("Could not find node " + targetNodeName + "!");
			}

			if (gtSourceNode.isPresent() && gtSourceNode.isPresent()) {
				GTEdge gtEdge = GTLanguageFactory.eINSTANCE.createGTEdge();
				gtEdge.setType(reference.getType());
				gtEdge.setName(sourceNodeName + "-" + gtEdge.getType().getName() + "-" + targetNodeName);
				gtEdge.setBindingType(convertOperatorToBindingType(reference.getOperator()));
				gtSourceNode.ifPresent(node -> gtEdge.setSourceNode(node));
				gtTargetNode.ifPresent(node -> gtEdge.setTargetNode(node));
				gtEdges.add(gtEdge);
			}
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
	private static Optional<GTNode> findGTNodeWithName(final List<GTNode> nodes, final String name) {
		Objects.requireNonNull(nodes, "node list must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return nodes.stream().filter(gtNode -> name.equals(gtNode.getName())).findAny();
	}
}
