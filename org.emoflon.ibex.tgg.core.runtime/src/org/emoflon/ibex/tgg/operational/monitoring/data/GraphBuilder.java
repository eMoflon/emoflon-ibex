package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.HashSet;

public class GraphBuilder {
	private Collection<Node> src = new HashSet<>();
	private Collection<Node> trg = new HashSet<>();
	private Collection<Edge> corr = new HashSet<>();
	private Collection<Edge> edges = new HashSet<>();

	public GraphBuilder() {
	}

	public GraphBuilder(Graph pGraph) {
		addGraph(pGraph);
	}

	public GraphBuilder addGraph(Graph pGraph) {
		src.addAll(pGraph.getSrc());
		trg.addAll(pGraph.getTrg());
		corr.addAll(pGraph.getCorr());
		edges.addAll(pGraph.getEdges());
		return this;
	}

	public GraphBuilder addSrcNode(Node pNode) {
		src.add(pNode);
		return this;
	}

	public GraphBuilder addSrcNodes(Collection<Node> pNodes) {
		src.addAll(pNodes);
		return this;
	}

	public GraphBuilder addTrgNode(Node pNode) {
		trg.add(pNode);
		return this;
	}

	public GraphBuilder addTrgNodes(Collection<Node> pNodes) {
		trg.addAll(pNodes);
		return this;
	}

	public GraphBuilder addCorrEdge(Edge pEdge) {
		corr.add(pEdge);
		return this;
	}

	public GraphBuilder addCorrEdges(Collection<Edge> pEdges) {
		corr.addAll(pEdges);
		return this;
	}

	public GraphBuilder addEdge(Edge pEdge) {
		edges.add(pEdge);
		return this;
	}

	public GraphBuilder addEdges(Collection<Edge> pEdges) {
		edges.addAll(pEdges);
		return this;
	}

	public Graph build() {
		return new Graph(src, trg, corr, edges);
	}
}
