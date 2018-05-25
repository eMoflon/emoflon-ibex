package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.utils.EMFEdge;
import org.emoflon.ibex.common.utils.EMFEdgeHashingStrategy;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class HandleDependencies {

	/**
	 * Collection of bundles with their context bundles (on which they depend); key:
	 * bundle; value: bundles that provide context for this bundle
	 */
	Int2ObjectOpenHashMap<ArrayList<Integer>> directDependencies = new Int2ObjectOpenHashMap<ArrayList<Integer>>();

	/**
	 * Collection of all cycles detected in dependencies between the bundles; key:
	 * cycle id; value: list of bundles that cause cycle
	 */
	Int2ObjectOpenHashMap<ArrayList<Integer>> cyclicDependencies = new Int2ObjectOpenHashMap<ArrayList<Integer>>();
	private int cyclicDependencyId = 1;

	Object2ObjectOpenCustomHashMap<EMFEdge, IntOpenHashSet> edgeToMarkingMatches = new Object2ObjectOpenCustomHashMap<>(
			new EMFEdgeHashingStrategy());
	Object2ObjectOpenHashMap<EObject, IntOpenHashSet> nodeToMarkingMatches = new Object2ObjectOpenHashMap<>();
	ObjectLinkedOpenHashSet<Bundle> appliedBundles;
	Int2ObjectOpenHashMap<ObjectOpenHashSet<EObject>> matchToContextNodes = new Int2ObjectOpenHashMap<ObjectOpenHashSet<EObject>>();
	Int2ObjectOpenHashMap<ObjectOpenCustomHashSet<EMFEdge>> matchToContextEdges = new Int2ObjectOpenHashMap<ObjectOpenCustomHashSet<EMFEdge>>();
	Int2ObjectOpenHashMap<Bundle> matchToBundle = new Int2ObjectOpenHashMap<Bundle>();

	public HandleDependencies(ObjectLinkedOpenHashSet<Bundle> appliedBundles,
			Object2ObjectOpenCustomHashMap<EMFEdge, IntOpenHashSet> edgeToMarkingMatches,
			Object2ObjectOpenHashMap<EObject, IntOpenHashSet> nodeToMarkingMatches,
			Int2ObjectOpenHashMap<ObjectOpenHashSet<EObject>> matchToContextNodes,
			Int2ObjectOpenHashMap<ObjectOpenCustomHashSet<EMFEdge>> matchToContextEdges) {
		this.appliedBundles = appliedBundles;
		appliedBundles.forEach(b -> b.getAllMatches().stream().forEach(m -> matchToBundle.put((int) m, b)));
		this.nodeToMarkingMatches = nodeToMarkingMatches;
		this.edgeToMarkingMatches = edgeToMarkingMatches;
		this.matchToContextNodes = matchToContextNodes;
		this.matchToContextEdges = matchToContextEdges;
	}

	private Int2ObjectOpenHashMap<ArrayList<Integer>> getBundlesDirectDependences() {
		Int2ObjectOpenHashMap<ArrayList<Integer>> bundleToDependences = new Int2ObjectOpenHashMap<ArrayList<Integer>>();
		for (Bundle bundle : appliedBundles) {
			ArrayList<Integer> directDependences = findDirectDependences(bundle).stream()
					.filter(n -> n != bundle.getKernelMatch()).collect(Collectors.toCollection(ArrayList::new));
			bundleToDependences.put(bundle.getKernelMatch(), directDependences);
		}
		return bundleToDependences;
	}

	private ArrayList<Integer> findDirectDependences(Bundle bundle) {
		ArrayList<Integer> dependecies = new ArrayList<Integer>();
		IntOpenHashSet matches = new IntOpenHashSet();
		for (EObject node : bundle.getBundleContextNodes()) {
			if (nodeToMarkingMatches.containsKey(node))
				matches = nodeToMarkingMatches.get(node);
			putDependedContext(dependecies, matches);
		}

		for (EMFEdge edge : bundle.getBundleContextEdges()) {
			if (edgeToMarkingMatches.containsKey(edge))
				matches = edgeToMarkingMatches.get(edge);
			putDependedContext(dependecies, matches);
		}
		return dependecies;
	}

	private void putDependedContext(ArrayList<Integer> dependecies, IntOpenHashSet matches) {
		matches.stream().forEach(match -> {
			if (!dependecies.contains(matchToBundle(match)))
				dependecies.add(matchToBundle(match));
		});
	}

	private int matchToBundle(int match) {
		return matchToBundle.get(match).getKernelMatch();
	}

	/**
	 * 
	 * @return Collection of all cycles found in dependencies between bundles
	 */
	public Int2ObjectOpenHashMap<ArrayList<Integer>> getCyclicDependenciesBetweenBundles() {
		detectAllBundleCycles();
		return cyclicDependencies;
	}

	private void detectAllBundleCycles() {
		directDependencies = getBundlesDirectDependences();
		HashMap<Integer, Boolean> visitedBundle = new HashMap<Integer, Boolean>();

		directDependencies.keySet().stream().forEach(k -> visitedBundle.put(k, false));

		List<Integer> recursiveStack = new ArrayList<>();

		directDependencies.keySet().stream().forEach(k -> examineDependences(k, recursiveStack, visitedBundle));
	}

	private void examineDependences(int bundle, List<Integer> recursiveStack, HashMap<Integer, Boolean> visited) {
		if (!visited.get(bundle)) {
			visited.put(bundle, true);
			recursiveStack.add(bundle);
			for (int contextBundle : directDependencies.get(bundle)) {
				if (!visited.get(contextBundle)) {
					examineDependences(contextBundle, recursiveStack, visited);
				} else if (recursiveStack.contains(contextBundle)) {
					ArrayList<Integer> cyclicContextBundles = new ArrayList<Integer>();
					int pop = recursiveStack.size() - 1;
					// add node that caused cycle
					cyclicContextBundles.add(recursiveStack.get(pop));
					// add all other nodes from stack until the same node that caused the cycle is
					// reached
					while (recursiveStack.get(pop).intValue() != contextBundle) {
						pop--;
						cyclicContextBundles.add(recursiveStack.get(pop));
					}
					cyclicDependencies.put(cyclicDependencyId, cyclicContextBundles);
					cyclicDependencyId++;
				}
			}
			recursiveStack.remove(recursiveStack.size() - 1);
		}

	}

	/**
	 * Find all rule applications for each bundle
	 * 
	 * @param detectedCycle
	 *            - specific cycle between bundles
	 * @return Collection of rule applications inside the bundle
	 */
	public List<IntLinkedOpenHashSet> getComplementRuleApplications(int detectedCycle) {
		List<IntLinkedOpenHashSet> bundleToComplementRuleApplication = new ArrayList<>();
		for (int bundleID : cyclicDependencies.get(detectedCycle)) {
			IntLinkedOpenHashSet set = getBundle(bundleID).getAllComplementMatches();
			if(set.isEmpty())
				set.add(getBundle(bundleID).getKernelMatch());
			bundleToComplementRuleApplication.add(set);
		}
		return bundleToComplementRuleApplication;
	}

	private Bundle getBundle(int bundleID) {
		return appliedBundles.stream() //
				.filter(b -> b.getKernelMatch() == bundleID) //
				.findAny().get();
	}

}
