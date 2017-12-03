package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;

public class HandleDependencies {
	
	/**
	 * Collection of bundles with their context bundles (on which they depend);
	 * key: bundle;
	 * value: bundles that provide context for this bundle
	 */
	HashMap<Integer, ArrayList<Integer>> directDependencies = new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * Collection of all cycles detected in dependencies between the bundles;
	 * key: cycle id; 
	 * value: list of bundles that cause cycle
	 */
	HashMap<Integer, ArrayList<Integer>> cyclicDependencies = new HashMap<Integer, ArrayList<Integer>>();
	private int cyclicDependencyId = 1;
	
	TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches = new TCustomHashMap<>(
			new RuntimeEdgeHashingStrategy());
	THashMap<EObject, TIntHashSet> nodeToMarkingMatches = new THashMap<>();
	HashSet<Bundle> appliedBundles;
	TIntObjectMap<THashSet<EObject>> matchToContextNodes = new TIntObjectHashMap<>();
	TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges = new TIntObjectHashMap<>();
	
	public HandleDependencies(HashSet<Bundle> appliedBundles, TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches, THashMap<EObject, TIntHashSet> nodeToMarkingMatches,
			TIntObjectMap<THashSet<EObject>> matchToContextNodes,TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges) {
		this.appliedBundles = appliedBundles;
		this.nodeToMarkingMatches = nodeToMarkingMatches;
		this.edgeToMarkingMatches = edgeToMarkingMatches;
		this.matchToContextNodes = matchToContextNodes;
		this.matchToContextEdges = matchToContextEdges;
	}
	
	
	private HashMap<Integer, ArrayList<Integer>> getBundlesDirectDependences() {
		HashMap<Integer, ArrayList<Integer>> bundleToDependences = new HashMap<Integer, ArrayList<Integer>>();
		for (Bundle bundle : appliedBundles) {
			ArrayList<Integer> directDependences = findDirectDependences(bundle).stream().filter(n -> n != bundle.getKernelMatch()).collect(Collectors.toCollection(ArrayList::new));
			bundleToDependences.put(bundle.getKernelMatch(), directDependences);
		}
		return bundleToDependences;
	}

	private ArrayList<Integer> findDirectDependences(Bundle bundle) {
		ArrayList<Integer> dependecies = new ArrayList<Integer>();
		TIntHashSet matches = new TIntHashSet();
		for (EObject node : bundle.getBundleContextNodes()) {
			if (nodeToMarkingMatches.contains(node))
				matches = nodeToMarkingMatches.get(node);
			putDependedContext(dependecies, matches);
		}
		
		for (RuntimeEdge edge : bundle.getBundleContextEdges()) {
			if (edgeToMarkingMatches.contains(edge))
				matches = edgeToMarkingMatches.get(edge);
			putDependedContext(dependecies, matches);
		}
		return dependecies;
	}

	private void putDependedContext(ArrayList<Integer> dependecies, TIntHashSet matches) {	
		matches.forEach(match -> {
			if(!dependecies.contains(matchToBundle(match)))
				dependecies.add(matchToBundle(match));
			return true;
		});
	}


	private int matchToBundle(Integer match) {
		return appliedBundles.stream()
				.filter(b -> b.getAllMatches().contains(match))
				.findAny().get()
				.getKernelMatch();
	}

	/**
	 * 
	 * @return Collection of all cycles found in dependencies between bundles
	 */
	public HashMap<Integer, ArrayList<Integer>> getCyclicDependenciesBetweenBundles(){
		detectAllBundleCycles();
		return cyclicDependencies;
	}
	
	private void detectAllBundleCycles() {
		directDependencies = getBundlesDirectDependences();
		HashMap<Integer, Boolean> visitedBundle = new HashMap<Integer, Boolean>();
		
		directDependencies.keySet().stream().forEach(k -> visitedBundle.put(k, false));
	
		List<Integer> recursiveStack = new ArrayList<>();
		
		directDependencies.keySet().stream().forEach(
				k -> examineDependences(k, recursiveStack, visitedBundle)
				);
	}

	private void examineDependences(Integer bundle, List<Integer> recursiveStack, HashMap<Integer, Boolean> visited) {
		if(!visited.get(bundle)) {
			visited.put(bundle, true);
			recursiveStack.add(bundle);
			for (Integer contextBundle : directDependencies.get(bundle)) {
				if(!visited.get(contextBundle)) {
					examineDependences(contextBundle, recursiveStack, visited);
				}
				else if(recursiveStack.contains(contextBundle)) {
					ArrayList<Integer> cyclicContextBundles = new ArrayList<Integer>();
					int pop = recursiveStack.size() - 1;
					// add node that caused cycle
					cyclicContextBundles.add(recursiveStack.get(pop));
					// add all other nodes from stack until the same node that caused the cycle is reached
					while(recursiveStack.get(pop) != contextBundle) {
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
	 * Detect concrete rule applications that caused dependencies between bundles
	 * @param detectedCycle - specific cycle between bundles
	 * @return Collection of all rule applications causing dependencies for detectedCycle
	 */
	public HashMap<Integer, HashSet<Integer>> getDependedRuleApplications(int detectedCycle) {
		HashMap<Integer, HashSet<Integer>> bundleToDependedRuleApplication = new HashMap<Integer, HashSet<Integer>>();
		
		//this list contains bundles sorted in way where next bundle depend on previous one; last then depend on first in the list
		ArrayList<Integer> detectedCycles = cyclicDependencies.get(detectedCycle);
			for (int i=0; i < detectedCycles.size(); i++) {
				if(i==detectedCycles.size()-1) {
					HashSet<Integer> dependedRuleApplications = getRuleApplicationDependencies(detectedCycles.get(i), detectedCycles.get(0));
					bundleToDependedRuleApplication.put(detectedCycles.get(0), dependedRuleApplications);
				}
				else {
					HashSet<Integer> dependedRuleApplications = getRuleApplicationDependencies(detectedCycles.get(i), detectedCycles.get(i+1));
					bundleToDependedRuleApplication.put(detectedCycles.get(i+1), dependedRuleApplications);
				}
			}
		return bundleToDependedRuleApplication;
	}
	
	private HashSet<Integer> getRuleApplicationDependencies(int bundleTarget, int bundleSource) {
		HashSet<Integer> dependedRuleApplications = new HashSet<Integer>();
		Bundle bundleContext = getBundle(bundleTarget);
		Bundle bundleCreate = getBundle(bundleSource);
		for (Integer match : bundleCreate.getAllMatches()) {
			determineRuleApplicationDependencies(match, dependedRuleApplications, bundleContext);
		}
		return dependedRuleApplications;
	}

	private void determineRuleApplicationDependencies(Integer match, HashSet<Integer> dependedRuleApplications, Bundle bundleContext) {
		for (EObject node : matchToContextNodes.get(match)) {
			for (Integer matchCreatedNode : nodeToMarkingMatches.get(node).toArray()) {
				if(bundleContext.getAllMatches().contains(matchCreatedNode))
					dependedRuleApplications.add(match);
			}
		}
		
		for (RuntimeEdge edge : matchToContextEdges.get(match)) {
			for (Integer matchCreatedNode : edgeToMarkingMatches.get(edge).toArray()) {
				if(bundleContext.getAllMatches().contains(matchCreatedNode))
					dependedRuleApplications.add(match);
			}
		}
		
	}


	private Bundle getBundle(int bundle) {
		return appliedBundles.stream()
				.filter(b -> b.getKernelMatch() == bundle)
				.findAny().get();
	}

}






