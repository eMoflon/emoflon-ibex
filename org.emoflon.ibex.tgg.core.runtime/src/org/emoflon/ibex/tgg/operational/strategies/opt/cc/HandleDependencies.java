package org.emoflon.ibex.tgg.operational.strategies.opt.cc;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.collections.IntSet;
import org.emoflon.ibex.common.collections.IntToObjectMap;
import org.emoflon.ibex.common.emf.EMFEdge;

public class HandleDependencies {

	/**
	 * Collection of bundles with their context bundles (on which they depend); key:
	 * bundle; value: bundles that provide context for this bundle
	 */
	private IntToObjectMap<ArrayList<Integer>> directDependencies = cfactory.createIntToObjectHashMap();

	/**
	 * Collection of all cycles detected in dependencies between the bundles; key:
	 * cycle id; value: list of bundles that cause cycle
	 */
	private IntToObjectMap<ArrayList<Integer>> cyclicDependencies = cfactory.createIntToObjectHashMap();
	private int cyclicDependencyId = 1;

	private Map<EMFEdge, IntSet> edgeToMarkingMatches = cfactory.createEMFEdgeHashMap();
	private Map<EObject, IntSet> nodeToMarkingMatches = cfactory.createObjectToObjectHashMap();
	private Set<Bundle> appliedBundles;
	private IntToObjectMap<Bundle> matchToBundle = cfactory.createIntToObjectHashMap();

	public HandleDependencies(Set<Bundle> appliedBundles, Map<EMFEdge, IntSet> edgeToMarkingMatches,
			Map<EObject, IntSet> nodeToMarkingMatches, IntToObjectMap<Set<EObject>> matchToContextNodes,
			IntToObjectMap<Set<EMFEdge>> matchToContextEdges) {
		this.appliedBundles = appliedBundles;
		appliedBundles.forEach(b -> b.getAllMatches().stream().forEach(m -> matchToBundle.put((int) m, b)));
		this.nodeToMarkingMatches = nodeToMarkingMatches;
		this.edgeToMarkingMatches = edgeToMarkingMatches;
	}

	private IntToObjectMap<ArrayList<Integer>> getBundlesDirectDependences() {
		IntToObjectMap<ArrayList<Integer>> bundleToDependences = cfactory.createIntToObjectHashMap();
		for (Bundle bundle : appliedBundles) {
			ArrayList<Integer> directDependences = findDirectDependences(bundle).stream()
					.filter(n -> n != bundle.getKernelMatch()).collect(Collectors.toCollection(ArrayList::new));
			bundleToDependences.put(bundle.getKernelMatch(), directDependences);
		}
		return bundleToDependences;
	}

	private ArrayList<Integer> findDirectDependences(Bundle bundle) {
		ArrayList<Integer> dependecies = new ArrayList<Integer>();
		IntSet matches = cfactory.createIntSet();
		for (EObject node : bundle.getBundleContextNodes()) {
			if (nodeToMarkingMatches.containsKey(node))
				matches = nodeToMarkingMatches.get(node);
			putDependentContext(dependecies, matches);
		}

		for (EMFEdge edge : bundle.getBundleContextEdges()) {
			if (edgeToMarkingMatches.containsKey(edge))
				matches = edgeToMarkingMatches.get(edge);
			putDependentContext(dependecies, matches);
		}
		return dependecies;
	}

	private void putDependentContext(ArrayList<Integer> dependencies, IntSet matches) {
		matches.stream().forEach(match -> {
			if (!dependencies.contains(matchToBundle(match)))
				dependencies.add(matchToBundle(match));
		});
	}

	private int matchToBundle(int match) {
		return matchToBundle.get(match).getKernelMatch();
	}

	/**
	 * 
	 * @return Collection of all cycles found in dependencies between bundles
	 */
	public IntToObjectMap<ArrayList<Integer>> getCyclicDependenciesBetweenBundles() {
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
	 * @param detectedCycle - specific cycle between bundles
	 * @return Collection of rule applications inside the bundle
	 */
	public List<IntSet> getRuleApplications(int detectedCycle) {
		List<IntSet> bundleToComplementRuleApplication = new ArrayList<>();
		for (int bundleID : cyclicDependencies.get(detectedCycle)) {
			IntSet set = getBundle(bundleID).getAllComplementMatches();
			// Note: if the set is empty then it means the cyclic dependency must involve
			// the kernel directly. In this case, we add the kernel match and use it to
			// generate exclusions constraints for the cycle. If the set is not empty,
			// however, it is enough to generate cyclic constraints only between complement
			// matches because maximality enforces that all complement matches must be
			// applied with their kernel match, i.e., as one "bundle". In fact it would be
			// wrong to include the kernel as there might not be any need to exclude them.
			if (set.isEmpty())
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
