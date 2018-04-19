package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

import gnu.trove.set.hash.THashSet;

public class Bundle {
	
	/**
	 * Collection of all matches belonging to the bundle (kernel and its complement matches)
	 */
	private THashSet<Integer> allMatches;
	private int kernelMatch;
	
	private THashSet<EObject> bundleContextNodes;
	private THashSet<RuntimeEdge> bundleContextEdges;

	
	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = new THashSet<Integer>();
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
	
	public THashSet<Integer> getAllMatches(){
		return allMatches;
	}
	
	public int getKernelMatch() {
		return kernelMatch;
	}

	public THashSet<Integer> getAllComplementMatches() {
		return allMatches.stream().filter(m -> m != kernelMatch).collect(Collectors.toCollection(THashSet::new));
	}

}
