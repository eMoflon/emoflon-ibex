package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

public class TGGObjectGraphBuilder {
	private Collection<Node> src = new HashSet<>();
	private Collection<Node> trg = new HashSet<>();
	private Collection<Edge> corr = new HashSet<>();
	private Collection<Edge> edges = new HashSet<>();
	private Collection<Node> nodes = new HashSet<>();
	private HashMap<String, HashSet<EObject>> resources;
	
	public TGGObjectGraphBuilder() {
	}

	public TGGObjectGraphBuilder(TGGObjectGraph pGraph) {
		add(pGraph);
	}

	public TGGObjectGraphBuilder add(TGGObjectGraph pGraph) {
		src.addAll(pGraph.getSrcElements());
		trg.addAll(pGraph.getTrgElements());
		corr.addAll(pGraph.getCorrElements());
		edges.addAll(pGraph.getEdges());
		nodes.addAll(pGraph.getNodes());
		resources = pGraph.getResources();
		return this;
	}

	public TGGObjectGraphBuilder addSrcNode(Node pNode) {
		addNode(pNode);
		src.add(pNode);
		return this;
	}

	public TGGObjectGraphBuilder addSrcNodes(Collection<Node> pNodes) {
		addNodes(pNodes);
		src.addAll(pNodes);
		return this;
	}

	public TGGObjectGraphBuilder addTrgNode(Node pNode) {
		addNode(pNode);
		trg.add(pNode);
		return this;
	}

	public TGGObjectGraphBuilder addTrgNodes(Collection<Node> pNodes) {
		addNodes(pNodes);
		trg.addAll(pNodes);
		return this;
	}

	public TGGObjectGraphBuilder addCorrEdge(Edge pEdge) {
		corr.add(pEdge);
		return this;
	}

	public TGGObjectGraphBuilder addCorrEdges(Collection<Edge> pEdges) {
		corr.addAll(pEdges);
		return this;
	}

	public TGGObjectGraphBuilder addEdge(Edge pEdge) {
		edges.add(pEdge);
		return this;
	}

	public TGGObjectGraphBuilder addEdges(Collection<Edge> pEdges) {
		edges.addAll(pEdges);
		return this;
	}

	public TGGObjectGraph build() {
		return new TGGObjectGraph(src, trg, corr, edges, nodes, resources);
	}
	
	public void addNode(Node pNode) {
		if (!nodes.contains(pNode)) {
			nodes.add(pNode);
		}
	}
	
	public void addNodes(Collection<Node> pNodes) {
		for(Node n : pNodes) {
			addNode(n);
		}
	}
	
	public void setResources(HashMap<String, HashSet<EObject>> pResources) {
		resources = pResources;
	}		
}