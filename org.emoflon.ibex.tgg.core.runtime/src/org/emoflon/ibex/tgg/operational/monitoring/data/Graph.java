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
