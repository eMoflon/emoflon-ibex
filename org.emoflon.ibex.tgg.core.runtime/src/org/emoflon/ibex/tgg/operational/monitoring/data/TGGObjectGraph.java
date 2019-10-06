package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

public class TGGObjectGraph {
	private Collection<Node> src;
	private Collection<Node> trg;
	private Collection<Edge> corr;
	private Collection<Edge> edges;
	private Collection<Node> nodes;
	private HashMap<String, HashSet<EObject>> resources;

	TGGObjectGraph(Collection<Node> pSrc, Collection<Node> pTrg,
			Collection<Edge> pCorr, Collection<Edge> pEdges, Collection<Node> pNodes, HashMap<String, HashSet<EObject>> pResources) {
		src = Collections.unmodifiableCollection(pSrc);
		trg = Collections.unmodifiableCollection(pTrg);
		corr = Collections.unmodifiableCollection(pCorr);
		edges = Collections.unmodifiableCollection(pEdges);
		nodes = Collections.unmodifiableCollection(pNodes);
		resources = pResources;
	}

	public Collection<Node> getSrcElements() {
		return src;
	}

	public Collection<Node> getTrgElements() {
		return trg;
	}

	public Collection<Edge> getCorrElements() {
		return corr;
	}

	public Collection<Edge> getEdges() {
		return edges;
	}
	
	public Collection<Node> getNodes() {
		return nodes;
	}	

	public HashMap<String, HashSet<EObject>> getResources() {
		return resources;
	}	

}