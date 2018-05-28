package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;

import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class Bundle {
	
	/**
	 * Collection of all matches belonging to the bundle (kernel and its complement matches)
	 */
	private IntLinkedOpenHashSet allMatches;
	private int kernelMatch;
	
	private ObjectOpenHashSet<EObject> bundleContextNodes;
	private ObjectOpenCustomHashSet<RuntimeEdge> bundleContextEdges;

	
	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = new IntLinkedOpenHashSet();
		bundleContextNodes = new ObjectOpenHashSet<EObject>();
		bundleContextEdges = new ObjectOpenCustomHashSet<RuntimeEdge>(new RuntimeEdgeHashingStrategy());
	}
	
	public void addMatch(int match) {
		allMatches.add(match);
	}
	
	public void addBundleContextNodes(Set<EObject> blackNodes) {
		bundleContextNodes.addAll(blackNodes);
	}
	
	public void addBundleContextEdges(Set<RuntimeEdge> blackEdges) {
		bundleContextEdges.addAll(blackEdges);
	}
	
	public ObjectOpenHashSet<EObject> getBundleContextNodes() {
		return bundleContextNodes;
	}

	public ObjectOpenCustomHashSet<RuntimeEdge> getBundleContextEdges() {
		return bundleContextEdges;
	}
	
	public IntLinkedOpenHashSet getAllMatches(){
		return allMatches;
	}
	
	public int getKernelMatch() {
		return kernelMatch;
	}

	public IntOpenHashSet getAllComplementMatches() {
		IntOpenHashSet complementMatches = new IntOpenHashSet();
		IntIterator it = this.allMatches.iterator();
		while(it.hasNext()) {
			int value = it.nextInt();
			if(value != kernelMatch) {
				complementMatches.add(value);
			}
		}
		return complementMatches;
	}
}
