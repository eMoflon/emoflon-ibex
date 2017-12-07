package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.IMatch;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

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
import language.TGGRule;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import runtime.TGGRuleApplication;

public abstract class CC extends OperationalStrategy {

	protected int nameCounter = 0;

	protected int idCounter = 1;
	protected TIntObjectHashMap<IMatch> idToMatch = new TIntObjectHashMap<>();
	TIntObjectHashMap<String> matchIdToRuleName = new TIntObjectHashMap<>();

	TIntIntHashMap weights = new TIntIntHashMap();

	protected THashMap<IMatch, HashMap<String, EObject>> matchToCoMatch = new THashMap<>();

	protected TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches = new TCustomHashMap<>(
			new RuntimeEdgeHashingStrategy());
	protected THashMap<EObject, TIntHashSet> nodeToMarkingMatches = new THashMap<>();

	protected TIntObjectMap<THashSet<EObject>> matchToContextNodes = new TIntObjectHashMap<>();
	protected TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges = new TIntObjectHashMap<>();

	/**
	 * Collection of constraints to guarantee uniqueness property;
	 * key: ComplementRule (CR) match; 
	 * value: other CR matches of the same CR using the same context as CR match
	 */
	THashMap<Integer, TIntHashSet> sameCRmatches = new THashMap<>();

	/**
	 * Collection of constraints to guarantee maximality property;
	 * value: kernels whose complement rules did not fulfill maximality property
	 */
	TIntHashSet invalidKernels = new TIntHashSet();
	
	/**
	 * Collection of constraints to guarantee cyclic dependences are avoided;
	 * value: correctly applied bundles (kernel match + its CRs matches)
	 */
	HashSet<Bundle> appliedBundles = new HashSet<Bundle>();
	Bundle lastAppliedBundle;
	
	protected ConsistencyReporter consistencyReporter = new ConsistencyReporter();
	
	public CC(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}

	@Override
	protected boolean manipulateSrc() {
		return false;
	}

	@Override
	protected boolean manipulateTrg() {
		return false;
	}

	@Override
	public void loadModels() throws IOException {
		s = loadResource(projectPath + "/instances/src.xmi");
		t = loadResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");

		EcoreUtil.resolveAll(rs);
	}

	@Override
	public void saveModels() throws IOException {
		c.save(null);
		p.save(null);
	}

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.CC);
	}
	
	@Override
	protected boolean processOneOperationalRuleMatch() {
		if (operationalMatchContainer.isEmpty())
			return false;

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);
		
		HashMap<String, EObject> comatch = processOperationalRuleMatch(ruleName, match);
		if (comatch != null && isKernelMatch(ruleName))
			processComplementRuleMatches(comatch);
		removeOperationalRuleMatch(match);
		return true;
	}
	
	private void processComplementRuleMatches(HashMap<String, EObject> comatch) {
		engine.updateMatches();
		int kernelMatchID = idToMatch.size();
		Set<IMatch> contextRuleMatches = findAllComplementRuleContextMatches();
		Set<IMatch> complementRuleMatches = findAllComplementRuleMatches();
		
		THashMap<Integer, THashSet<EObject>> crMatchToContextNodes = new THashMap<>();
		
		while (complementRuleMatches.iterator().hasNext()) {
			IMatch match = complementRuleMatches.iterator().next();
			applyMatchAndHandleUniqueness(match, crMatchToContextNodes);
			complementRuleMatches.remove(match);
			removeOperationalRuleMatch(match);
		}
		
		//check if all found CR matches are really applied
		while (contextRuleMatches.iterator().hasNext()) {
			IMatch match = contextRuleMatches.iterator().next();
			handleMaximality(match, contextRuleMatches, kernelMatchID);
			contextRuleMatches.remove(match);
		}
		
		//FIXME:[Milica] Check if this is really needed
		TGGRuleApplication application = (TGGRuleApplication) comatch.get(ConsistencyPattern.getProtocolNodeName());
		application.setAmalgamated(true);
	}
	
	//FIXME:[Milica] Check if maximality need to be done for edges as well
	private void handleMaximality(IMatch match, Set<IMatch> contextRuleMatches, int kernelMatchID) {
		String ruleName = removeAllSuffixes(match.patternName());
		TGGComplementRule rule = (TGGComplementRule) getRule(ruleName);
		if(rule.isBounded()) {
		//check if the complement rule was applied. If not, mark its kernel as invalid.
			THashSet<EObject> contextNodes = getGenContextNodes(match);
			if (!matchToContextNodes.containsValue(contextNodes))
				invalidKernels.add(kernelMatchID);
		}
	}

	//FIXME:[Milica] Check if uniqueness need to be done for edges as well
	private void applyMatchAndHandleUniqueness(IMatch match, THashMap<Integer, THashSet<EObject>> contextNodesMatches) {
		String ruleName = operationalMatchContainer.getRuleName(match);
		if (processOperationalRuleMatch(ruleName, match) != null) {
			TGGComplementRule rule = (TGGComplementRule) getRule(ruleName);
			if(rule.isBounded())
				findDuplicatedMatches(idToMatch.size(), contextNodesMatches);
		}
	}

	private void findDuplicatedMatches(int matchID, THashMap<Integer, THashSet<EObject>> contextNodesMatches) { 
		THashSet<EObject> contextNodesForMatchID = matchToContextNodes.get(matchID);
		for (Integer id : contextNodesMatches.keySet()) {
		//check if matches belong to the same complement rule
			if (matchIdToRuleName.get(matchID).equals(matchIdToRuleName.get(id))) {
				if(matchToContextNodes.get(id).equals(contextNodesForMatchID)) {
					if (!sameCRmatches.containsKey(matchID)) {
						sameCRmatches.put(matchID, new TIntHashSet());
						sameCRmatches.get(matchID).add(matchID);
						sameCRmatches.get(matchID).add(id);
					}
					else {
					sameCRmatches.get(matchID).add(id);
					}
				}
			}
		}
		contextNodesMatches.put(matchID, contextNodesForMatchID);
	}
	
	private String removeAllSuffixes(String name) {
		if(name.indexOf(PatternSuffixes.GENForCC) == -1)
			return name;
		return name.substring(0, name.indexOf(PatternSuffixes.GENForCC));
	}

	private THashSet<EObject> getGenContextNodes(IMatch match){
		THashSet<EObject> contextNodes = match.parameterNames().stream()
				.map(n -> match.get(n)).collect(Collectors.toCollection(THashSet<EObject>::new));
		return contextNodes;
	}
	
	/**
	 * @return Collection of all matches that has to be applied.
	 */
	private Set<IMatch> findAllComplementRuleContextMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> m.patternName().contains(PatternSuffixes.GENForCC))
				.collect(Collectors.toSet());
		return allComplementRuleMatches;
	}
	
	private Set<IMatch> findAllComplementRuleMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.patternName())))
				.collect(Collectors.toSet());
		return allComplementRuleMatches;
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
	protected boolean allContextElementsalreadyProcessed(IMatch match, String ruleName) {
		return true;
	}

	@Override
	protected boolean someElementsAlreadyProcessed(String ruleName, IMatch match) {
		return false;
	}

	@Override
	protected void setIsRuleApplicationFinal(TGGRuleApplication ra) {
		ra.setFinal(false);
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

	private void handleBundles(IMatch match, HashMap<String, EObject> comatch, String ruleName) {
		Bundle appliedBundle;
		TGGRule rule = getRule(ruleName);
		if(! (rule instanceof TGGComplementRule)) {
			appliedBundle = new Bundle(idCounter);
			appliedBundles.add(appliedBundle);
			lastAppliedBundle = appliedBundle;
		}
		else {
			appliedBundle = lastAppliedBundle;
		}
		appliedBundle.addMatch(idCounter);
		// add context nodes and edges of this concrete match to its bundle
		appliedBundle.addBundleContextNodes(getBlackNodes(match, comatch, ruleName));
		appliedBundle.addBundleContextEdges(getBlackEdges(match, comatch, ruleName));
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
						model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "IMPL" + nameCounter++);
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
						model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "IMPL" + nameCounter++);
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

	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_CC();
	}

	public boolean modelsAreConsistent() {
		return getInconsistentSrcNodes().size() + getInconsistentTrgNodes().size() + getInconsistentSrcEdges().size()
				+ getInconsistentTrgEdges().size() == 0;
	}

	public Collection<EObject> getInconsistentSrcNodes() {
		return consistencyReporter.getInconsistentSrcNodes();
	}

	public Collection<EObject> getInconsistentTrgNodes() {
		return consistencyReporter.getInconsistentTrgNodes();
	}

	public Collection<RuntimeEdge> getInconsistentSrcEdges() {
		return consistencyReporter.getInconsistentSrcEdges();
	}

	public Collection<RuntimeEdge> getInconsistentTrgEdges() {
		return consistencyReporter.getInconsistentTrgEdges();
	}

	public String generateConsistencyReport() {
		String result = "";
		if (modelsAreConsistent())
			result += "Your models are consistent";
		else {
			result += "Your models are inconsistent. Following elements are not part of a consistent triple:";
			result += "\n" + "Source nodes:" + "\n";
			result += String.join("\n",
					getInconsistentSrcNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Source edges:" + "\n";
			result += String.join("\n",
					getInconsistentSrcEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target nodes:" + "\n";
			result += String.join("\n",
					getInconsistentTrgNodes().stream().map(n -> n.toString()).collect(Collectors.toSet()));
			result += "\n" + "Target edges:" + "\n";
			result += String.join("\n",
					getInconsistentTrgEdges().stream().map(n -> n.toString()).collect(Collectors.toSet()));
		}
		return result;
	}

}
