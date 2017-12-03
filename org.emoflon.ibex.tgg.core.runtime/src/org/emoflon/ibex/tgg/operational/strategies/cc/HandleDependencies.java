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
		ArrayList<Integer> dependeces = new ArrayList<Integer>();
		for (EObject node : bundle.getBundleContextNodes()) {
			TIntHashSet matches = nodeToMarkingMatches.get(node);
			for (Integer match : matches.toArray()) {
				if(!dependeces.contains(matchToBundle(match)))
					dependeces.add(matchToBundle(match));
			}
		}
		
		for (RuntimeEdge edge : bundle.getBundleContextEdges()) {
			TIntHashSet matches = edgeToMarkingMatches.get(edge);
			for (Integer match : matches.toArray()) {
				if(!dependeces.contains(matchToBundle(match)))
					dependeces.add(matchToBundle(match));
			}
		}
		return dependeces;
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
					ArrayList<Integer> cyclicBundles = new ArrayList<Integer>();
					int pop = recursiveStack.size() - 1;
					cyclicBundles.add(recursiveStack.get(pop));
					while(recursiveStack.get(pop) != contextBundle) {
						pop--;
						cyclicBundles.add(recursiveStack.get(pop));
					}
					cyclicDependencies.put(cyclicDependencyId, cyclicBundles);
					cyclicDependencyId++;
				}
			}
			recursiveStack.remove(recursiveStack.size() - 1);
		}
		
	}
	
	/**
	 * Detect concrete rule applications that caused dependencies between bundles
	 * @param detectedCycle - specific cycle between bundles
	 * @return Collection of all rule applications causing dependencies
	 */
	public HashMap<Integer, HashSet<Integer>> getDependedRuleApplications(int detectedCycle) {
		HashMap<Integer, HashSet<Integer>> bundleToDependRuleApplication = new HashMap<Integer, HashSet<Integer>>();
		
		//this list contains bundles sorted in way where next bundle depend on previous one; last then depend on first in the list
		ArrayList<Integer> detectedCycles = cyclicDependencies.get(detectedCycle);
			for (int i=0; i < detectedCycles.size(); i++) {
				if(i==detectedCycles.size()-1) {
					HashSet<Integer> dependedRuleApplications = getRuleApplicationDependences(detectedCycles.get(i), detectedCycles.get(0));
					bundleToDependRuleApplication.put(detectedCycles.get(0), dependedRuleApplications);
				}
				else {
					HashSet<Integer> dependedRuleApplications = getRuleApplicationDependences(detectedCycles.get(i), detectedCycles.get(i+1));
					bundleToDependRuleApplication.put(detectedCycles.get(i+1), dependedRuleApplications);
				}
			}
		return bundleToDependRuleApplication;
	}
	
	private HashSet<Integer> getRuleApplicationDependences(int bundleOrgin, int bundleDepend) {
		HashSet<Integer> dependedRuleApplications = new HashSet<Integer>();
		Bundle bundleContext = getBundle(bundleOrgin);
		Bundle bundleCreate = getBundle(bundleDepend);
		for (Integer match : bundleCreate.getAllMatches()) {
			for (EObject node : matchToContextNodes.get(match)) {
				for (Integer matchCreatedNode : nodeToMarkingMatches.get(node).toArray()) {
					if(bundleContext.getAllMatches().contains(matchCreatedNode))
						dependedRuleApplications.add(match);
				}
			}
		}
		return dependedRuleApplications;
	}


	private Bundle getBundle(int bundle) {
		return appliedBundles.stream()
				.filter(b -> b.getKernelMatch() == bundle)
				.findAny().get();
	}

}






