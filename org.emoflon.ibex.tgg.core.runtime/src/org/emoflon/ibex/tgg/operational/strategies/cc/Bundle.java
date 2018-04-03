package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

import gnu.trove.set.hash.THashSet;

public class Bundle {
	
	/**
	 * Collection of all matches belonging to the bundle (kernel and its complement matches)
	 */
	private HashSet<Integer> allMatches;
	private int kernelMatch;
	
	private HashSet<EObject> bundleContextNodes;
	private HashSet<RuntimeEdge> bundleContextEdges;

	
	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = new HashSet<Integer>();
		bundleContextNodes = new HashSet<EObject>();
		bundleContextEdges = new HashSet<RuntimeEdge>();
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
	
	public HashSet<EObject> getBundleContextNodes() {
		return bundleContextNodes;
	}

	public HashSet<RuntimeEdge> getBundleContextEdges() {
		return bundleContextEdges;
	}
	
	public HashSet<Integer> getAllMatches(){
		return allMatches;
	}
	
	public int getKernelMatch() {
		return kernelMatch;
	}

	public HashSet<Integer> getAllComplementMatches() {
		return allMatches.stream().filter(m -> m != kernelMatch).collect(Collectors.toCollection(HashSet::new));
	}

}
