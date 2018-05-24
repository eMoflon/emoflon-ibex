package org.emoflon.ibex.tgg.operational.strategies.cc;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;

public class Bundle {
	
	/**
	 * Collection of all matches belonging to the bundle (kernel and its complement matches)
	 */
	private TIntHashSet allMatches;
	private int kernelMatch;
	
	private THashSet<EObject> bundleContextNodes;
	private THashSet<RuntimeEdge> bundleContextEdges;

	
	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = new TIntHashSet();
		bundleContextNodes = new THashSet<EObject>();
		bundleContextEdges = new THashSet<RuntimeEdge>();
	}
	
	public void addMatch(Integer match) {
		allMatches.add(match);
	}
	
	public void addBundleContextNodes(THashSet<EObject> blackNodes) {
		bundleContextNodes.addAll(blackNodes);
	}
	
	public void addBundleContextEdges(THashSet<RuntimeEdge> blackEdges) {
		bundleContextEdges.addAll(blackEdges);
	}
	
	public THashSet<EObject> getBundleContextNodes() {
		return bundleContextNodes;
	}

	public THashSet<RuntimeEdge> getBundleContextEdges() {
		return bundleContextEdges;
	}
	
	public TIntHashSet getAllMatches(){
		return allMatches;
	}
	
	public int getKernelMatch() {
		return kernelMatch;
	}

	public TIntHashSet getAllComplementMatches() {
		TIntHashSet complementMatches = new TIntHashSet();
		TIntIterator it = this.allMatches.iterator();
		while(it.hasNext()) {
			int value = it.next();
			if(value != kernelMatch) {
				complementMatches.add(value);
			}
		}
		return complementMatches;
	}
}
