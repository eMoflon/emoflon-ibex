package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public class TGGObjectGraph {
    private Collection<EObject> srcElements;
    private Collection<EObject> trgElements;
    private Collection<EObject> corrElements;
    private Collection<Edge> edges;

    public TGGObjectGraph(Collection<EObject> pSrcElements, Collection<EObject> pTrgElements,
	    Collection<EObject> pCorrElements, Collection<Edge> pEdges) {
	srcElements = pSrcElements;
	trgElements = pTrgElements;
	corrElements = pCorrElements;
	edges = pEdges;
    }

    public Collection<EObject> getSrcElements() {
	return srcElements;
    }

    public Collection<EObject> getTrgElements() {
	return trgElements;
    }

    public Collection<EObject> getCorrElements() {
	return corrElements;
    }

    public Collection<Edge> getEdges() {
	return edges;
    }

    public static class Edge {
	private String label;
	private EObject src;
	private EObject trg;

	public Edge(String pLabel, EObject pSrc, EObject pTrg) {
	    label = pLabel;
	    src = pSrc;
	    trg = pTrg;
	}

	public String getLabel() {
	    return label;
	}

	public EObject getSrc() {
	    return src;
	}

	public EObject getTrg() {
	    return trg;
	}
    }
}
