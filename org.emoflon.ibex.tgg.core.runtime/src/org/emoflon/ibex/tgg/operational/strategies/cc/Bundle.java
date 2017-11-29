package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.util.IMatch;

import gnu.trove.set.hash.THashSet;

public class Bundle {
	
	int kernelMatch;

	HashSet<Integer> allMatches;
	// Bookeeping of context and created elements(nodes&edges) with bundle
	HashSet<EObject> contextNodes;
	HashSet<RuntimeEdge> contextEdges;

	
	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = new HashSet<Integer>();
		contextNodes = new HashSet<EObject>();
		contextEdges = new HashSet<RuntimeEdge>();
	}
	
	public void addMatch(Integer complementMatch) {
		allMatches.add(complementMatch);
	}
	
	public void addContextNodes(THashSet<EObject> blackNodes) {
		contextNodes.addAll(blackNodes);
	}
	
	public void addContextEdges(THashSet<RuntimeEdge> blackEdges) {
		contextEdges.addAll(blackEdges);
	}
	
	public HashSet<EObject> getContextNodes() {
		return contextNodes;
	}

	public HashSet<RuntimeEdge> getContextEdges() {
		return contextEdges;
	}
	
	public HashSet<Integer> getAllMatches(){
		return allMatches;
	}
	
	public int getKernelMatch() {
		return kernelMatch;
	}

}
