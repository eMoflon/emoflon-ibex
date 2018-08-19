package org.emoflon.ibex.tgg.operational.strategies.opt.cc;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.PrimitiveIterator.OfInt;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.emf.EMFEdge;

public class Bundle {
	/**
	 * Collection of all matches belonging to the bundle (kernel and its complement
	 * matches)
	 */
	private IntSet allMatches;
	private int kernelMatch;

	private Set<EObject> bundleContextNodes;
	private Set<EMFEdge> bundleContextEdges;

	public Bundle(int kernelMatch) {
		this.kernelMatch = kernelMatch;
		allMatches = cfactory.createIntSet();
		bundleContextNodes = cfactory.createObjectSet();
		bundleContextEdges = cfactory.createEMFEdgeHashSet();
	}

	public void addMatch(int match) {
		allMatches.add(match);
	}
	
	public void addBundleContextNodes(Set<EObject> blackNodes) {
		bundleContextNodes.addAll(blackNodes);
	}
	
	public void addBundleContextEdges(Set<EMFEdge> blackEdges) {
		bundleContextEdges.addAll(blackEdges);
	}

	public Set<EObject> getBundleContextNodes() {
		return bundleContextNodes;
	}

	public Set<EMFEdge> getBundleContextEdges() {
		return bundleContextEdges;
	}

	public IntSet getAllMatches() {
		return allMatches;
	}

	public int getKernelMatch() {
		return kernelMatch;
	}
	
	public IntSet getAllComplementMatches() {
		IntSet complementMatches = cfactory.createIntSet();
		OfInt it = this.allMatches.iterator();
		while (it.hasNext()) {
			int value = it.nextInt();
			if (value != kernelMatch) {
				complementMatches.add(value);
			}
		}
		return complementMatches;
	}
}
