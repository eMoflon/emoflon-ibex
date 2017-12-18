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

public class EditorToInternalGT {
	private static GTLanguageFactory factory = GTLanguageFactory.eINSTANCE;

	public GTRuleSet transformRuleSet(Model editorModel) {
		GTRuleSet gtRuleSet = factory.createGTRuleSet();
		editorModel.getRules()
			.forEach(
					rule -> gtRuleSet.getRules().add(this.transformRule(rule))
			);
		return gtRuleSet;
	}

	public GTRule transformRule(Rule rule) {
		GTRule gtRule = factory.createGTRule();
		gtRule.setName(rule.getName());

		GTGraph gtGraph = factory.createGTGraph();
		List<Node> nodes = rule.getConstraints().stream()
			.filter(c -> c instanceof Node)
			.map(c -> (Node) c)
			.collect(Collectors.toList());
		nodes.forEach(
				node -> gtGraph.getNodes().add(this.transformNode(node))
		);
		nodes.forEach(
				node -> gtGraph.getEdges().addAll(this.transformReferencesOfNode(node, gtGraph.getNodes()))
		);
		gtRule.setGraph(gtGraph);

		return gtRule;
	}

	public GTNode transformNode(Node node) {
		GTNode gtNode = factory.createGTNode();
		gtNode.setName(node.getVariable().getName());
		// gtNode.setType(node.getVariable().getType()); TODO Set node type to EClass
		return gtNode;
	}

	public List<GTEdge> transformReferencesOfNode(Node node, List<GTNode> nodes) {
		List<GTEdge> gtEdges = new ArrayList<GTEdge>();
		List<Reference> references = node.getConstraints().stream()
			.filter(x -> x instanceof Reference)
			.map(x -> (Reference) x)
			.collect(Collectors.toList());
		references.forEach(
				reference -> {
					GTEdge gtEdge = factory.createGTEdge();
					gtEdge.setName(reference.getName());
					// gtEdge.setType(); TODO Set edge type to EReference
					this.findNodeWithName(nodes, node.getVariable().getName())
						.ifPresent(
								gtSourceNode -> {
									gtEdge.setSourceNode(gtSourceNode);
									gtSourceNode.getOutgoingEdges().add(gtEdge);
								}
						);
					this.findNodeWithName(nodes, reference.getValue())
						.ifPresent(
								gtTargetNode -> {
									gtEdge.setTargetNode(gtTargetNode);
									gtTargetNode.getIncomingEdges().add(gtEdge);
								}
						);
					gtEdges.add(gtEdge);
				}
		);
		return gtEdges;
	}

	public Optional<GTNode> findNodeWithName(List<GTNode> nodes, String name) {
		return nodes.stream().filter(gtNode -> name.equals(gtNode.getName())).findAny();
	}
}
