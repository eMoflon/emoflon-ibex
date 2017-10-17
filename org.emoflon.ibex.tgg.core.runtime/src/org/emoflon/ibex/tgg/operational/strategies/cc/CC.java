package org.emoflon.ibex.tgg.operational.strategies.cc;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;
import org.emoflon.ibex.tgg.operational.edge.RuntimeEdgeHashingStrategy;
import org.emoflon.ibex.tgg.operational.util.IMatch;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;
import org.emoflon.ibex.tgg.operational.util.ManipulationUtil;

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
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import runtime.TGGRuleApplication;

public abstract class CC extends OperationalStrategy {

	private int nameCounter = 0;

	private int idCounter = 1;
	TIntObjectHashMap<IMatch> idToMatch = new TIntObjectHashMap<>();
	TIntObjectHashMap<String> matchIdToRuleName = new TIntObjectHashMap<>();

	TIntIntHashMap weights = new TIntIntHashMap();

	THashMap<IMatch, HashMap<String, EObject>> matchToCoMatch = new THashMap<>();

	TCustomHashMap<RuntimeEdge, TIntHashSet> edgeToMarkingMatches = new TCustomHashMap<>(
			new RuntimeEdgeHashingStrategy());
	THashMap<EObject, TIntHashSet> nodeToMarkingMatches = new THashMap<>();

	TIntObjectMap<THashSet<EObject>> matchToContextNodes = new TIntObjectHashMap<>();
	TIntObjectMap<TCustomHashSet<RuntimeEdge>> matchToContextEdges = new TIntObjectHashMap<>();

	ConsistencyReporter consistencyReporter = new ConsistencyReporter();

	public CC(IbexOptions options) throws IOException {
		super(options);
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
	protected void wrapUp() {
		for (int v : chooseTGGRuleApplications()) {
			IMatch match = idToMatch.get(v < 0 ? -v : v);
			HashMap<String, EObject> comatch = matchToCoMatch.get(match);
			if (v < 0) {
				comatch.values().forEach(EcoreUtil::delete);
			} else {
				super.prepareProtocol(matchIdToRuleName.get(v), match, comatch);
			}
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

		idCounter++;
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

	private TIntObjectHashMap<GRBVar> defineGurobiVariables(GRBModel model) {
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

	private void defineGurobiExclusions(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {

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
	}

	private void defineGurobiImplications(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {

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

	private void defineGurobiObjective(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {

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
