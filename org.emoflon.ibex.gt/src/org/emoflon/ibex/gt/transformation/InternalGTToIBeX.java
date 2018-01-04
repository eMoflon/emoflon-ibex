package org.emoflon.ibex.gt.transformation;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EReference;

import GTLanguage.GTRule;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXGraph;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;

/**
 * Transformation from the internal GT model to IBeX Patterns.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class InternalGTToIBeX {

	/**
	 * Transforms a GTRule into an IBeXPattern.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @return the IBeXPattern
	 */
	public static IBeXPattern transformRuleToIBeX(final GTRule gtRule) {
		Objects.requireNonNull(gtRule, "rule must not be null!");
		IBeXPattern ibexPattern = createIBeXPattern(gtRule);
		addGraphWithAllNodes(ibexPattern);
		addGraphsPerEdgeType(ibexPattern);
		return ibexPattern;
	}

	/**
	 * Creates a new IBeXPattern with nodes and edges equivalent to the given GTRule
	 * 
	 * @param gtRule
	 *            the GTRule to derive nodes and edges from
	 * @return the IBeXPattern
	 */
	private static IBeXPattern createIBeXPattern(final GTRule gtRule) {
		Objects.requireNonNull(gtRule, "rule must not be null!");
		IBeXPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		ibexPattern.setName(gtRule.getName());
		gtRule.getGraph().getNodes().forEach(gtNode -> {
			// Transform GTNode to IBeXNode.
			IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
			ibexNode.setName(gtNode.getName());
			ibexNode.setType(gtNode.getType());
			ibexPattern.getNodes().add(ibexNode);
		});
		gtRule.getGraph().getEdges().forEach(gtEdge -> {
			// Transform GTEdge to IBeXEdge.
			IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
			ibexEdge.setType(gtEdge.getType());
			findIBeXNodeWithName(ibexPattern, gtEdge.getSourceNode().getName())
					.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
			findIBeXNodeWithName(ibexPattern, gtEdge.getTargetNode().getName())
					.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
			ibexPattern.getEdges().add(ibexEdge);
		});
		return ibexPattern;
	}

	/**
	 * Adds a graph with all nodes (and without edges) to the IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern
	 */
	private static void addGraphWithAllNodes(final IBeXPattern ibexPattern) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
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
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		ibexPattern.getEdges().stream().map(e -> e.getType()).distinct()
				.forEach(edgeType -> addGraphWithAllEdgesOfType(ibexPattern, edgeType));
	}

	/**
	 * Adds a graph with the edges of the given type nodes to the IBeXPattern. The
	 * graph is the subgraph containing all edges of the given type together with
	 * their source and target nodes.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param edgeType
	 *            the type of edge, must not be <code>null</code>
	 */
	private static void addGraphWithAllEdgesOfType(final IBeXPattern ibexPattern, final EReference edgeType) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(edgeType, "edge type must not be null!");
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
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param name
	 *            the name to search for, must not be <code>null</code>
	 * @return an Optional for the IBeXNode
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return ibexPattern.getNodes().stream().filter(n -> name.equals(n.getName())).findAny();
	}
}
