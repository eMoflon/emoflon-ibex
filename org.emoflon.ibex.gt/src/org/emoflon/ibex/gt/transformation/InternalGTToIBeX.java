package org.emoflon.ibex.gt.transformation;

import java.util.Optional;

import org.eclipse.emf.ecore.EReference;

import GTLanguage.GTRule;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXGraph;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;

/**
 * Transformation from the editor model the internal GT model to IBeX Patterns.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class InternalGTToIBeX {

	/**
	 * Transforms a GTRule into an IBeXPattern.
	 * 
	 * @param gtRule
	 *            the rule
	 * @return the IBeXPattern
	 */
	public static IBeXPattern transformRuleToIBeX(final GTRule gtRule) {
		IBeXPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		ibexPattern.setName(gtRule.getName());

		gtRule.getGraph().getNodes().forEach(gtNode -> {
			IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
			ibexNode.setName(gtNode.getName());
			ibexNode.setType(gtNode.getType());
			ibexPattern.getNodes().add(ibexNode);
		});
		gtRule.getGraph().getEdges().forEach(gtEdge -> {
			IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
			ibexEdge.setType(gtEdge.getType());
			findIBeXNodeWithName(ibexPattern, gtEdge.getSourceNode().getName())
					.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
			findIBeXNodeWithName(ibexPattern, gtEdge.getTargetNode().getName())
					.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
			ibexPattern.getEdges().add(ibexEdge);
		});

		addGraphWithAllNodes(ibexPattern);
		addGraphsPerEdgeType(ibexPattern);

		return ibexPattern;
	}

	/**
	 * Adds a graph with all nodes and no edges to the IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern
	 */
	private static void addGraphWithAllNodes(final IBeXPattern ibexPattern) {
		IBeXGraph ibexGraph = IBeXLanguageFactory.eINSTANCE.createIBeXGraph();
		ibexGraph.setName("all-nodes");
		ibexPattern.getNodes().forEach(node -> ibexGraph.getNodes().add(node));
		ibexPattern.getGraphs().add(ibexGraph);
	}

	/**
	 * Per type of edges, add a graph with those edges and their nodes to the
	 * IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern
	 */
	private static void addGraphsPerEdgeType(final IBeXPattern ibexPattern) {
		ibexPattern.getEdges().stream().map(e -> e.getType()).distinct()
				.forEach(ref -> addGraphWithAllEdgesOfType(ibexPattern, ref));
	}

	/**
	 * Adds a graph with the edges of the given type and their source and target
	 * nodes to the IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @param edgeType
	 *            the type of edge
	 */
	private static void addGraphWithAllEdgesOfType(final IBeXPattern ibexPattern, final EReference edgeType) {
		IBeXGraph ibexGraph = IBeXLanguageFactory.eINSTANCE.createIBeXGraph();
		ibexGraph.setName("edges-" + edgeType.getName());
		ibexPattern.getEdges().stream().filter(e -> e.getType().equals(edgeType)).forEach(e -> {
			ibexGraph.getEdges().add(e);
			if (!ibexGraph.getNodes().contains(e.getSourceNode())) {
				ibexGraph.getNodes().add(e.getSourceNode());
			}
			if (!ibexGraph.getNodes().contains(e.getTargetNode())) {
				ibexGraph.getNodes().add(e.getTargetNode());
			}
		});
		ibexPattern.getGraphs().add(ibexGraph);
	}

	/**
	 * Finds an IBeXNode with the given name in the given IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @param name
	 *            the name to search for
	 * @return an Optional for the IBeXNode
	 */
	private static Optional<IBeXNode> findIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		return ibexPattern.getNodes().stream().filter(n -> name.equals(n.getName())).findAny();
	}
}
