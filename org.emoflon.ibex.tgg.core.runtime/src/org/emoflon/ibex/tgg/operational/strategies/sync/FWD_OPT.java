package org.emoflon.ibex.tgg.operational.strategies.sync;

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
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.cc.Bundle;
import org.emoflon.ibex.tgg.operational.strategies.cc.ConsistencyReporter;
import org.emoflon.ibex.tgg.operational.strategies.cc.HandleDependencies;

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
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class FWD_OPT extends OperationalStrategy {

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
	
	//Hash maps to save the old metamodel state
	THashMap<EReference, Integer> referenceToUpperBound = new THashMap<EReference, Integer>();
	THashMap<EReference, Integer> referenceToLowerBound = new THashMap<EReference, Integer>();
	THashMap<EReference, EReference> referenceToEOpposite = new THashMap<EReference, EReference>();
	THashMap<EReference, Boolean> referenceToContainment = new THashMap<EReference, Boolean>();
	
	public FWD_OPT(IbexOptions options) throws IOException {
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
						if (referenceToUpperBound.containsKey(reference) &&
							referenceToLowerBound.containsKey(reference) &&
							referenceToContainment.containsKey(reference) &&
							referenceToEOpposite.containsKey(reference)) {
							// Reference already exists, values must not be overwritten
								continue;
						}
						
						//Save metamodel values
						referenceToUpperBound.put(reference, reference.getUpperBound());
						referenceToLowerBound.put(reference, reference.getLowerBound());
						referenceToContainment.put(reference, reference.isContainment());
						referenceToEOpposite.put(reference, reference.getEOpposite());
						
						
						//Change metamodel values
						reference.setUpperBound(-1);
						reference.setLowerBound(0);
						reference.setContainment(false);
						reference.setEOpposite(null);						
					}
				}
			}
		}		
	}
	
	public void unrelaxReferences(EList<EPackage> model) {

		EPackage[] packages = (EPackage[])model.toArray();
		
		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();
			
			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl)next;
					
					for (EReference reference : nextEClassImpl.getEAllReferences()) {				
						// Get old metamodel values
						int upperBound = referenceToUpperBound.get(reference);
						int lowerBound = referenceToLowerBound.get(reference);
						boolean containment = referenceToContainment.get(reference);
						EReference eOpposite = referenceToEOpposite.get(reference);
						
						// Change metamodel values
						reference.setUpperBound(upperBound);
						reference.setLowerBound(lowerBound);
						reference.setContainment(containment);
						reference.setEOpposite(eOpposite);
						
						// Reset setting for reference
						((EStructuralFeatureImpl) reference).setSettingDelegate(null);
					}
				}
			}
		}
	}
	
	@Override
	protected void wrapUp() {
		  for (int v : chooseTGGRuleApplications()) {
			  int id = v < 0 ? -v : v;
			  IMatch comatch = idToMatch.get(id);
			   if (v < 0) {
					for (TGGRuleCorr createdCorr : getGreenFactory(matchIdToRuleName.get(id)).getGreenCorrNodesInRule())
						EcoreUtil.delete((EObject) comatch.get(createdCorr.getName()));
					
					//for (TGGRuleEdge createdTrgEdge : getGreenFactory(matchIdToRuleName.get(id)).getGreenTrgEdgesInRule())
					//	EcoreUtil.delete((EObject) comatch.get(createdTrgEdge.getName()));
					
					for (TGGRuleNode createdTrgNode : getGreenFactory(matchIdToRuleName.get(id)).getGreenTrgNodesInRule())
						EcoreUtil.delete((EObject) comatch.get(createdTrgNode.getName()));
					
					EcoreUtil.delete(getRuleApplicationNode(comatch));
				}
		  }
		  consistencyReporter.init(this);
	}
	
	@Override
	public boolean isPatternRelevantForCompiler(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD_OPT);
	}
	
	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
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
	
	private void handleBundles(IMatch comatch, String ruleName) {
		if(!(getRule(ruleName) instanceof TGGComplementRule)) {
			Bundle appliedBundle = new Bundle(idCounter);
			appliedBundles.add(appliedBundle);
			lastAppliedBundle = appliedBundle;
		}
		
		lastAppliedBundle.addMatch(idCounter);
		
		// add context nodes and edges of this concrete match to its bundle
		lastAppliedBundle.addBundleContextNodes(getBlackNodes(comatch, ruleName));
		lastAppliedBundle.addBundleContextEdges(getBlackEdges(comatch, ruleName));
	}

	private THashSet<EObject> getGreenNodes(IMatch comatch, String ruleName) {
		THashSet<EObject> result = new THashSet<>();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getGreenCorrNodesInRule()));
		return result;
	}

	private THashSet<EObject> getBlackNodes(IMatch comatch, String ruleName) {
		THashSet<EObject> result = new THashSet<>();
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackSrcNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackTrgNodesInRule()));
		result.addAll(getNodes(comatch, getGreenFactory(ruleName).getBlackCorrNodesInRule()));
		return result;
	}

	private THashSet<EObject> getNodes(IMatch comatch,
			Collection<? extends TGGRuleNode> specNodes) {
		THashSet<EObject> result = new THashSet<>();
		specNodes.forEach(n -> {
			result.add((EObject) comatch.get(n.getName()));
		});
		return result;
	}

	private THashSet<RuntimeEdge> getGreenEdges(IMatch comatch, String ruleName) {
		THashSet<RuntimeEdge> result = new THashSet<>();
		result.addAll(((IbexGreenInterpreter)greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getGreenSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter)greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getGreenTrgEdgesInRule(), false));
		return result;
	}

	private THashSet<RuntimeEdge> getBlackEdges(IMatch comatch, String ruleName) {
		THashSet<RuntimeEdge> result = new THashSet<>();
		result.addAll(((IbexGreenInterpreter)greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getBlackSrcEdgesInRule(), false));
		result.addAll(((IbexGreenInterpreter)greenInterpreter).createEdges(comatch, getGreenFactory(ruleName).getBlackTrgEdgesInRule(), false));
		return result;
	}
	
	@Override
	protected void createMarkers(IGreenPattern greenPattern, IMatch comatch, String ruleName) {
		
		idToMatch.put(idCounter, comatch);
		matchIdToRuleName.put(idCounter, ruleName);

		int weight = 
				getGreenFactory(ruleName).getGreenSrcEdgesInRule().size() + 
				getGreenFactory(ruleName).getGreenSrcNodesInRule().size() + 
				getGreenFactory(ruleName).getGreenTrgEdgesInRule().size() + 
				getGreenFactory(ruleName).getGreenTrgNodesInRule().size();

		weights.put(idCounter, weight);

		getGreenNodes(comatch, ruleName).forEach(e -> {
			if (!nodeToMarkingMatches.containsKey(e))
				nodeToMarkingMatches.put(e, new TIntHashSet());
			nodeToMarkingMatches.get(e).add(idCounter);
		});

		getGreenEdges(comatch, ruleName).forEach(e -> {
			if (!edgeToMarkingMatches.containsKey(e)) {
				edgeToMarkingMatches.put(e, new TIntHashSet());
			}
			edgeToMarkingMatches.get(e).add(idCounter);
		});

		matchToContextNodes.put(idCounter, new THashSet<>());
		matchToContextNodes.get(idCounter).addAll(getBlackNodes(comatch, ruleName));

		matchToContextEdges.put(idCounter, new TCustomHashSet<RuntimeEdge>(new RuntimeEdgeHashingStrategy()));
		matchToContextEdges.get(idCounter).addAll(getBlackEdges(comatch, ruleName));
		
		handleBundles(comatch, ruleName);

		idCounter++;
		
		super.createMarkers(greenPattern, comatch, ruleName);
	}
	
	public void forward() throws IOException {
		run();
	}
	
	@Override
	public void run() throws IOException {	
 
		do {
			blackInterpreter.updateMatches();
		} while (processOneOperationalRuleMatch());

		wrapUp();
	}
	
	@Override
	public void loadTGG() throws IOException {
		super.loadTGG();
		relaxReferences(options.tgg().getTrg());
	}
	
	@Override
	public void saveModels() throws IOException {
		//c.save(null);
	 	p.save(null);
		
	 	// Fix target before saving
	 	
	 	// Unrelax the metamodel
		unrelaxReferences(options.tgg().getTrg());
		
		// Remove adapters to avoid problems with notifications
		t.eAdapters().clear();
		t.getAllContents().forEachRemaining(o -> o.eAdapters().clear());
		
		// Copy and fix the model in the process
		FixingCopier.fixAll(t, c);
		
		// Now save fixed models
		t.save(null);
		c.save(null);
	}
}

