package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.Collections;

public class Graph {
    private Collection<Node> src;
    private Collection<Node> trg;
    private Collection<Edge> corr;
    private Collection<Edge> edges;

    Graph(Collection<Node> pSrc, Collection<Node> pTrg, Collection<Edge> pCorr, Collection<Edge> pEdges) {
	src = Collections.unmodifiableCollection(pSrc);
	trg = Collections.unmodifiableCollection(pTrg);
	corr = Collections.unmodifiableCollection(pCorr);
	edges = Collections.unmodifiableCollection(pEdges);

	for (Edge corrEdge : corr) {
	    if (!src.contains(corrEdge.getSrcNode()))
		throw new IllegalArgumentException("All corr edges must have a node in SRC as their src-node.");
	    if (!trg.contains(corrEdge.getTrgNode()))
		throw new IllegalArgumentException("All corr edges must have a node in TRG as their trg-node.");
	    corrEdge.getSrcNode().addCorrEdge(corrEdge);
	    corrEdge.getTrgNode().addCorrEdge(corrEdge);
	}

	for (Edge edge : edges) {
	    if (!((src.contains(edge.getSrcNode()) && src.contains(edge.getTrgNode())) || //
		    (trg.contains(edge.getSrcNode()) && trg.contains(edge.getTrgNode()))))
		throw new IllegalArgumentException("All edges must have a src-node and a trg-node in the same domain.");
	    edge.getSrcNode().addOutEdge(edge);
	    edge.getTrgNode().addInEdge(edge);
	}
    }

    public Collection<Node> getSrc() {
	return src;
    }

    public Collection<Node> getTrg() {
	return trg;
    }

    public Collection<Edge> getCorr() {
	return corr;
    }

    public Collection<Edge> getEdges() {
	return edges;
    }
}
