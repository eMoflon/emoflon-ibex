package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

public class TGGObjectGraphBuilder {
    private Collection<EObject> srcElements;
    private Collection<EObject> trgElements;
    private Collection<EObject> corrElements;
    private Collection<TGGObjectGraph.Edge> edges;

    public TGGObjectGraphBuilder() {
	srcElements = new HashSet<EObject>();
	trgElements = new HashSet<EObject>();
	corrElements = new HashSet<EObject>();
	edges = new HashSet<>();
    }

    public TGGObjectGraphBuilder add(TGGObjectGraph pObjectGraph) {
	srcElements.addAll(pObjectGraph.getSrcElements());
	trgElements.addAll(pObjectGraph.getTrgElements());
	corrElements.addAll(pObjectGraph.getCorrElements());
	edges.addAll(pObjectGraph.getEdges());
	return this;
    }

    public TGGObjectGraph build() {
	return new TGGObjectGraph(srcElements, trgElements, corrElements, edges);
    }
}
