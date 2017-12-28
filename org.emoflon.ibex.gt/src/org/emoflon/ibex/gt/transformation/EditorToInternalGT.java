package org.emoflon.ibex.gt.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.gt.editor.gT.Model;
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
 * Transformation from the editor model (which conforms to the GT.ecore
 * meta-model generated from the Xtext specification) to the internal GT model
 * (which conforms to the GTLanguage.ecore meta-model).
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class EditorToInternalGT {
	private static GTLanguageFactory factory = GTLanguageFactory.eINSTANCE;

	public static GTRuleSet transformRuleSet(final Model editorModel) {
		GTRuleSet gtRuleSet = factory.createGTRuleSet();
		editorModel.getRules().forEach(rule -> gtRuleSet.getRules().add(transformRule(rule)));
		return gtRuleSet;
	}

	public static GTRule transformRule(final Rule editorRule) {
		GTRule gtRule = factory.createGTRule();
		gtRule.setName(editorRule.getName());

		GTGraph gtGraph = factory.createGTGraph();
		List<Node> editorNodes = editorRule.getNodes();
		editorNodes.forEach(editorNode -> gtGraph.getNodes().add(transformNode(editorNode)));
		editorNodes.forEach(editorNode -> {
			gtGraph.getEdges().addAll(transformReferencesToEdges(editorNode, gtGraph.getNodes()));
		});
		gtRule.setGraph(gtGraph);

		return gtRule;
	}

	public static GTNode transformNode(final Node editorNode) {
		GTNode gtNode = factory.createGTNode();
		gtNode.setName(editorNode.getName());
		gtNode.setType(editorNode.getType());
		return gtNode;
	}

	public static List<GTEdge> transformReferencesToEdges(final Node editorNode, final List<GTNode> gtNodes) {
		List<GTEdge> gtEdges = new ArrayList<GTEdge>();
		List<Reference> references = extractReferences(editorNode);
		references.forEach(reference -> {
			GTEdge gtEdge = factory.createGTEdge();
			gtEdge.setType(reference.getType());
			findGTNodeWithName(gtNodes, editorNode.getName()).ifPresent(gtSourceNode -> {
				gtEdge.setSourceNode(gtSourceNode);
				gtSourceNode.getOutgoingEdges().add(gtEdge);
			});
			findGTNodeWithName(gtNodes, reference.getValue()).ifPresent(gtTargetNode -> {
				gtEdge.setTargetNode(gtTargetNode);
				gtTargetNode.getIncomingEdges().add(gtEdge);
			});
			gtEdges.add(gtEdge);
		});
		return gtEdges;
	}

	public static Optional<GTNode> findGTNodeWithName(final List<GTNode> nodes, final String name) {
		return nodes.stream().filter(gtNode -> name.equals(gtNode.getName())).findAny();
	}

	public static List<Reference> extractReferences(final Node editorNode) {
		return editorNode.getConstraints().stream().filter(x -> x instanceof Reference).map(x -> (Reference) x)
				.collect(Collectors.toList());
	}
}
