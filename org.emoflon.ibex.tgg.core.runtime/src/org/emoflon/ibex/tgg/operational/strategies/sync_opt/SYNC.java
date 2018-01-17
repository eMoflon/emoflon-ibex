package org.emoflon.ibex.tgg.operational.strategies.sync_opt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.strategies.cc.Bundle;
import org.emoflon.ibex.tgg.operational.strategies.cc.ConsistencyReporter;
import org.emoflon.ibex.tgg.operational.strategies.cc.HandleDependencies;
import org.emoflon.ibex.tgg.operational.util.IMatch;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import com.google.common.collect.Sets;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.hash.TCustomHashSet;
import gnu.trove.set.hash.THashSet;
import gnu.trove.set.hash.TIntHashSet;
import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import language.TGGComplementRule;
import language.TGGRuleNode;

public abstract class SYNC extends org.emoflon.ibex.tgg.operational.strategies.sync.SYNC {

	protected TIntObjectHashMap<IMatch> idToMatch = new TIntObjectHashMap<>();
	protected TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches = new TCustomHashMap<>(
			new RuntimeEdgeHashingStrategy());
	protected THashMap<EObject, TIntHashSet> nodeToMarkingMatches = new THashMap<>();
	protected THashMap<IMatch, HashMap<String, EObject>> matchToCoMatch = new THashMap<>();
	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();
	protected int nameCounter = 0;
	TIntIntHashMap weights = new TIntIntHashMap();
	THashMap<Integer, TIntHashSet> sameCRmatches = new THashMap<>();
	TIntHashSet invalidKernels = new TIntHashSet();
	protected TIntObjectMap<THashSet<EObject>> matchToContextNodes = new TIntObjectHashMap<>();
	protected TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges = new TIntObjectHashMap<>();
	HashSet<Bundle> appliedBundles = new HashSet<Bundle>();
	Bundle lastAppliedBundle;
	protected TIntObjectHashMap<String> matchIdToRuleName = new TIntObjectHashMap<>();
	protected int idCounter = 1;
	
	public SYNC(IbexOptions options) throws IOException {
		super(options);
	}
	
	public void relaxReferences(EList<EPackage> model) {

		EPackage[] packages = (EPackage[])model.toArray();
		
		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();
			
			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl)next;
					
					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						reference.setUpperBound(-1);
						reference.setLowerBound(0);
						//reference.setEOpposite(null);
						reference.setContainment(false);
					}
				}
			}
		}		
	}
	
	public void relaxModels() {
		relaxReferences(options.tgg().getSrc());
		relaxReferences(options.tgg().getTrg());
	}
	
	@Override
	protected void wrapUp() {
		  for (int v : chooseTGGRuleApplications()) {
			   IMatch match = idToMatch.get(v < 0 ? -v : v);
			   HashMap<String, EObject> comatch = matchToCoMatch.get(match);
			   if (v < 0)
			    comatch.values().forEach(EcoreUtil::delete);
		  }
		  consistencyReporter.init(s, t, p, ruleInfos);
	}
	
	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD_OPT);
	}

	protected TIntObjectHashMap<GRBVar> defineGurobiVariables(GRBModel model) {
		TIntObjectHashMap<GRBVar> gurobiVariables = new TIntObjectHashMap<>();
		idToMatch.keySet().forEach(v -> {
			try {
				gurobiVariables.put(v, model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x" + v));
			} catch (GRBException e) {
				e.printStackTrace();
			}
			return true;
		});
		return gurobiVariables;
	}
	
	protected void defineGurobiExclusions(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {
		for (EObject node : nodeToMarkingMatches.keySet()) {
			TIntHashSet variables = nodeToMarkingMatches.get(node);
			GRBLinExpr expr = new GRBLinExpr();
			variables.forEach(v -> {
				expr.addTerm(1.0, gurobiVars.get(v));
				return true;
			});
			try {
				model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "EXCL" + nameCounter++);
			} catch (GRBException e) {
				e.printStackTrace();
			}
		}

		for (RuntimeEdge edge : edgeToMarkingMatches.keySet()) {
			TIntHashSet variables = edgeToMarkingMatches.get(edge);
			GRBLinExpr expr = new GRBLinExpr();
			variables.forEach(v -> {
				expr.addTerm(1.0, gurobiVars.get(v));
				return true;
			});
			try {
				model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "EXCL" + nameCounter++);
			} catch (GRBException e) {
				e.printStackTrace();
			}
		}
		
		for (Integer match : sameCRmatches.keySet()) {
			TIntHashSet vars = sameCRmatches.get(match);
		
			GRBLinExpr expr = new GRBLinExpr();
			vars.forEach(v -> {
				expr.addTerm(1.0, gurobiVars.get(v));
				return true;
			});
			try {
				model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "EXCL" + nameCounter++);
			} catch (GRBException e) {
				e.printStackTrace();
			}
		}
		
		if (!invalidKernels.isEmpty()) {
			TIntHashSet vars = invalidKernels;
		
			GRBLinExpr expr = new GRBLinExpr();
			vars.forEach(v -> {
				expr.addTerm(1.0, gurobiVars.get(v));
				return true;
			});
			try {
				model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "EXCL" + nameCounter++);
			} catch (GRBException e) {
				e.printStackTrace();
			}
		}
		
		HandleDependencies handleCycles = new HandleDependencies(appliedBundles, edgeToMarkingMatches, nodeToMarkingMatches, matchToContextNodes, matchToContextEdges);
		HashMap<Integer, ArrayList<Integer>> cyclicBundles = handleCycles.getCyclicDependenciesBetweenBundles();

		for (int cycle : cyclicBundles.keySet()) {
			Set<List<Integer>> cyclicConstraints = getCyclicConstraints(handleCycles.getDependedRuleApplications(cycle));
			for (List<Integer> vars : cyclicConstraints) {
				GRBLinExpr expr = new GRBLinExpr();
				vars.forEach(v -> {
					expr.addTerm(1.0, gurobiVars.get(v));
					});
				try {
					model.addConstr(expr, GRB.LESS_EQUAL, vars.size()-1, "EXCL" + nameCounter++);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private Set<List<Integer>> getCyclicConstraints(HashMap<Integer, HashSet<Integer>> dependedRuleApplications) {
		List<HashSet<Integer>> excludedRuleApplications = new ArrayList<>();
		for (HashSet<Integer> ruleApplication : dependedRuleApplications.values()) {
			excludedRuleApplications.add(ruleApplication);
		}
		return Sets.cartesianProduct(excludedRuleApplications);
	}

	protected void defineGurobiImplications(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {
		for (int v : idToMatch.keySet().toArray()) {
			
			THashSet<EObject> contextNodes = matchToContextNodes.get(v);
			for (EObject node : contextNodes) {
				GRBLinExpr expr = new GRBLinExpr();
				expr.addTerm(1.0, gurobiVars.get(v));
				if (!nodeToMarkingMatches.contains(node)) {
					try {
						model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "IMPL" + nameCounter++);
					} catch (GRBException e) {
						e.printStackTrace();
					}
					continue;
				}

				for (int v2 : nodeToMarkingMatches.get(node).toArray()) {
					expr.addTerm(-1.0, gurobiVars.get(v2));
				}

				try {
					model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "IMPL" + nameCounter++);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			}

			TCustomHashSet<RuntimeEdge> contextEdges = matchToContextEdges.get(v);
			for (RuntimeEdge edge : contextEdges) {
				GRBLinExpr expr = new GRBLinExpr();
				expr.addTerm(1.0, gurobiVars.get(v));
				if (!edgeToMarkingMatches.contains(edge)) {
					try {
						model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "IMPL" + nameCounter++);
					} catch (GRBException e) {
						e.printStackTrace();
					}
					continue;
				}

				for (int v2 : edgeToMarkingMatches.get(edge).toArray()) {
					expr.addTerm(-1.0, gurobiVars.get(v2));
				}

				try {
					model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "IMPL" + nameCounter++);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void defineGurobiObjective(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {

		GRBLinExpr expr = new GRBLinExpr();
		idToMatch.keySet().forEach(v -> {
			int weight = weights.get(v);
			expr.addTerm(weight, gurobiVars.get(v));
			return true;
		});
		try {
			model.setObjective(expr, GRB.MAXIMIZE);
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}
	
	private int[] chooseTGGRuleApplications() {

		try {
			GRBEnv env = new GRBEnv("Gurobi_ILP.log");
			GRBModel model = new GRBModel(env);

			TIntObjectHashMap<GRBVar> gurobiVariables = defineGurobiVariables(model);

			defineGurobiExclusions(model, gurobiVariables);

			defineGurobiImplications(model, gurobiVariables);

			defineGurobiObjective(model, gurobiVariables);

			model.optimize();

			int[] result = new int[idToMatch.size()];

			idToMatch.keySet().forEach(v -> {
				try {
					if (gurobiVariables.get(v).get(GRB.DoubleAttr.X) > 0)
						result[v - 1] = v;
					else
						result[v - 1] = -v;
				} catch (GRBException e) {
					e.printStackTrace();
				}
				return true;
			});

			env.dispose();
			model.dispose();

			return result;

		} catch (GRBException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void handleBundles(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		if(!(getRule(ruleName) instanceof TGGComplementRule)) {
			Bundle appliedBundle = new Bundle(idCounter);
			appliedBundles.add(appliedBundle);
			lastAppliedBundle = appliedBundle;
		}
		
		lastAppliedBundle.addMatch(idCounter);
		
		// add context nodes and edges of this concrete match to its bundle
		lastAppliedBundle.addBundleContextNodes(getBlackNodes(match, comatch, ruleName));
		lastAppliedBundle.addBundleContextEdges(getBlackEdges(match, comatch, ruleName));
	}

	private THashSet<EObject> getGreenNodes(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		THashSet<EObject> result = new THashSet<>();
		result.addAll(getNodes(match, comatch, ruleInfos.getGreenSrcNodes(ruleName)));
		result.addAll(getNodes(match, comatch, ruleInfos.getGreenTrgNodes(ruleName)));
		result.addAll(getNodes(match, comatch, ruleInfos.getGreenCorrNodes(ruleName)));
		return result;
	}

	private THashSet<EObject> getBlackNodes(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		THashSet<EObject> result = new THashSet<>();
		result.addAll(getNodes(match, comatch, ruleInfos.getBlackSrcNodes(ruleName)));
		result.addAll(getNodes(match, comatch, ruleInfos.getBlackTrgNodes(ruleName)));
		result.addAll(getNodes(match, comatch, ruleInfos.getBlackCorrNodes(ruleName)));
		return result;
	}

	private THashSet<EObject> getNodes(IMatch match, HashMap<String, EObject> comatch,
			Collection<? extends TGGRuleNode> specNodes) {
		THashSet<EObject> result = new THashSet<>();
		specNodes.forEach(n -> {
			result.add(ManipulationUtil.getVariableByName(n.getName(), comatch, match));
		});
		return result;
	}

	private THashSet<RuntimeEdge> getGreenEdges(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		THashSet<RuntimeEdge> result = new THashSet<>();
		result.addAll(ManipulationUtil.createEdges(match, comatch, ruleInfos.getGreenSrcEdges(ruleName), false));
		result.addAll(ManipulationUtil.createEdges(match, comatch, ruleInfos.getGreenTrgEdges(ruleName), false));
		return result;
	}

	private THashSet<RuntimeEdge> getBlackEdges(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		THashSet<RuntimeEdge> result = new THashSet<>();
		result.addAll(ManipulationUtil.createEdges(match, comatch, ruleInfos.getBlackSrcEdges(ruleName), false));
		result.addAll(ManipulationUtil.createEdges(match, comatch, ruleInfos.getBlackTrgEdges(ruleName), false));
		return result;
	}
	
	@Override
	protected void prepareProtocol(String ruleName, IMatch match, HashMap<String, EObject> comatch) {

		idToMatch.put(idCounter, match);
		matchIdToRuleName.put(idCounter, ruleName);

		int weight = ruleInfos.getGreenSrcEdges(ruleName).size() + ruleInfos.getGreenSrcNodes(ruleName).size()
				+ ruleInfos.getGreenTrgEdges(ruleName).size() + ruleInfos.getGreenTrgNodes(ruleName).size();

		weights.put(idCounter, weight);

		matchToCoMatch.put(match, comatch);

		getGreenNodes(match, comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, new TIntHashSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(match, comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, new TIntHashSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, new THashSet<>());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(match, comatch, ruleName));

		matchToContextEdges.put(idCounter, new TCustomHashSet<RuntimeEdge>(new RuntimeEdgeHashingStrategy()));
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(match, comatch, ruleName));
		
		handleBundles(match, comatch, ruleName);

		idCounter++;
		
		super.prepareProtocol(ruleName, match, comatch);
	}
	
	@Override
	public void run() throws IOException {
		relaxModels();
		super.run();
	}
}
