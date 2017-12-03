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

public class HandleDependences {
	
	TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches = new TCustomHashMap<>(
			new RuntimeEdgeHashingStrategy());
	THashMap<EObject, TIntHashSet> nodeToMarkingMatches = new THashMap<>();
	HashSet<Bundle> appliedBundles;
	TIntObjectMap<THashSet<EObject>> matchToContextNodes = new TIntObjectHashMap<>();
	TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges = new TIntObjectHashMap<>();
	
	public HandleDependences(HashSet<Bundle> appliedBundles, TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches, THashMap<EObject, TIntHashSet> nodeToMarkingMatches,
			TIntObjectMap<THashSet<EObject>> matchToContextNodes,TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges) {
		this.appliedBundles = appliedBundles;
		this.nodeToMarkingMatches = nodeToMarkingMatches;
		this.edgeToMarkingMatches = edgeToMarkingMatches;
		this.matchToContextNodes = matchToContextNodes;
		this.matchToContextEdges = matchToContextEdges;
	}
	
	
	public HashMap<Integer, ArrayList<Integer>> getBundlesDirectDependences() {
		HashMap<Integer, ArrayList<Integer>> bundleToDependcesBundles = new HashMap<Integer, ArrayList<Integer>>();
		for (Bundle bundle : appliedBundles) {
			ArrayList<Integer> findDirectDependencesWithoutKernel = findDirectDependences(bundle).stream().filter(n -> n != bundle.getKernelMatch()).collect(Collectors.toCollection(ArrayList::new));
			bundleToDependcesBundles.put(bundle.getKernelMatch(), findDirectDependencesWithoutKernel);
		}
		return bundleToDependcesBundles;
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
		for (Bundle bundle : appliedBundles) {
			if (bundle.getAllMatches().contains(match))
					return bundle.getKernelMatch();
		}
		return 0;
	}
	
	HashMap<Integer, ArrayList<Integer>> cyclicDependeces = new HashMap<Integer, ArrayList<Integer>>();
	int cyclicDependceId = 1;
	HashMap<Integer, ArrayList<Integer>> directDependences;

	public HashMap<Integer, ArrayList<Integer>> detectAllBundleCycles() {
		directDependences = getBundlesDirectDependences();
		//directDependences = getFakeDepedences();
		HashMap<Integer, Boolean> visitedBundle = new HashMap<Integer, Boolean>();
		
		for (Integer i : directDependences.keySet()) {
			visitedBundle.put(i, false);
		}
	
		List<Integer> recursiveStack = new ArrayList<>();
		for (Integer bundle : directDependences.keySet()) {
			examineDependences(bundle, recursiveStack, visitedBundle);
		}
		
		return cyclicDependeces;
	}

	private void examineDependences(Integer bundle, List<Integer> recursiveStack, HashMap<Integer, Boolean> visited) {
		//examine if bundle is visited
		if(!visited.get(bundle)) {
			visited.put(bundle, true);
			recursiveStack.add(bundle);
			for (Integer depend : directDependences.get(bundle)) {
				if(!visited.get(depend)) {
					examineDependences(depend, recursiveStack, visited);
				}
				
				else if(recursiveStack.contains(depend)) {
					ArrayList<Integer> cyclicBundles = new ArrayList<Integer>();
					int pop = recursiveStack.size() - 1;
					cyclicBundles.add(recursiveStack.get(pop));
					while(recursiveStack.get(pop) != depend) {
						pop--;
						cyclicBundles.add(recursiveStack.get(pop));
					}
					cyclicDependeces.put(cyclicDependceId, cyclicBundles);
					cyclicDependceId++;
				}
			}
			recursiveStack.remove(recursiveStack.size() - 1);
		}
		
	}
	
	public HashMap<Integer, HashSet<Integer>> getDependedRuleApplications(int detectedCycle) {
		HashMap<Integer, HashSet<Integer>> bundleToDependRuleApplication = new HashMap<Integer, HashSet<Integer>>();
		
		ArrayList<Integer> detectedCycles = cyclicDependeces.get(detectedCycle);
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


	private Bundle getBundle(int b) {
		for (Bundle bundle : appliedBundles) {
			if(bundle.getKernelMatch() == b)
				return bundle;
		}
		return null;
	}

}






