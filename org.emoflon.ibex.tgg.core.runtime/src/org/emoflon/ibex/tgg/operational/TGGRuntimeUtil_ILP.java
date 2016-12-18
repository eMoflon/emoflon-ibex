package org.emoflon.ibex.tgg.operational;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.hash.TIntHashSet;
import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import language.TGG;
import runtime.Edge;
import runtime.TGGRuleApplication;

/**
 * THE ILP-based extension of TGGRuntimeUtil. This class additionally calculates
 * which rule applications create/require which elements and prepares an
 * optimization problem.
 * 
 * @author leblebici
 *
 */
public abstract class TGGRuntimeUtil_ILP extends TGGRuntimeUtil {

	protected TIntObjectHashMap<TGGRuleApplication> intToMatch = new TIntObjectHashMap<>();
	protected TObjectIntHashMap<TGGRuleApplication> matchToInt = new TObjectIntHashMap<>();

	protected HashMap<EObject, TIntHashSet> contextSrcToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdSrcToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> contextCorrToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdCorrToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> contextTrgToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdTrgToMatch = new HashMap<>();

	private int idCounter = 1;
	private int clauseName = 0;

	public TGGRuntimeUtil_ILP(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);

	}

	@Override
	public OperationStrategy getStrategy() {
		return OperationStrategy.ILP;
	}

	@Override
	public void finalize() {
		filter();
		super.finalize();
	}

	public void calculateTables(TGGRuleApplication m) {
		matchToInt.put(m, idCounter);
		intToMatch.put(idCounter, m);
		idCounter++;

		m.getContextSrc().forEach(e -> addMatchToTable(contextSrcToMatch, e, m));
		m.getCreatedSrc().forEach(e -> addMatchToTable(createdSrcToMatch, e, m));

		m.getContextTrg().forEach(e -> addMatchToTable(contextTrgToMatch, e, m));
		m.getCreatedTrg().forEach(e -> addMatchToTable(createdTrgToMatch, e, m));

		m.getContextCorr().forEach(e -> addMatchToTable(contextCorrToMatch, e, m));
		m.getCreatedCorr().forEach(e -> addMatchToTable(createdCorrToMatch, e, m));
	}

	protected TGGRuleApplication intToMatch(int id) {
		return intToMatch.get(id);
	}

	protected int matchToInt(TGGRuleApplication m) {
		return matchToInt.get(m);
	}

	private void addMatchToTable(HashMap<EObject, TIntHashSet> table, EObject e, TGGRuleApplication m) {
		TIntHashSet matchesOfE = null;
		if (table.containsKey(e))
			matchesOfE = table.get(e);
		else {
			matchesOfE = new TIntHashSet();
			table.put(e, matchesOfE);
		}
		matchesOfE.add(matchToInt(m));
	}

	protected TIntHashSet getCreatorsOf(EObject e) {
		if (createdSrcToMatch.containsKey(e))
			return createdSrcToMatch.get(e);
		if (createdTrgToMatch.containsKey(e))
			return createdTrgToMatch.get(e);
		if (createdCorrToMatch.containsKey(e))
			return createdCorrToMatch.get(e);
		return new TIntHashSet();
	}

	private void eliminate(TGGRuleApplication m) {
		deleteOutputElementsOf(m);
		m.eResource().getContents().remove(m);
	}

	protected abstract void deleteOutputElementsOf(TGGRuleApplication m);

	protected void deleteElements(Collection<EObject> elts) {
		elts.forEach(e -> e.eResource().getContents().remove(e));
		createdEdges.removeAll(elts);
	}

	// ILP problem related methods

	private void filter() {
		// created Edges must be redefined
		createdEdges = new ArrayList<>();

		protocolR.getContents().forEach(c -> {
			if (c instanceof TGGRuleApplication)
				calculateTables((TGGRuleApplication) c);
		});

		if (matchToInt.size() == 0)
			return;

		int all = 0;
		int chosen = 0;
				
		for (int v : chooseTGGRuleApplications()) {
			if (v < 0) {
				eliminate(intToMatch(-v));
				all++;
			} else {
				all++;
				chosen++;
				createdEdges.addAll(getOutputEdgesOf(intToMatch(v)));
				intToMatch(v).setFinal(true);
			}
		}
		System.out.println(chosen + " of " + all + " rule applications are chosen");
	}

	protected abstract Collection<Edge> getOutputEdgesOf(TGGRuleApplication ra);

	private int[] chooseTGGRuleApplications() {

		try {
			GRBEnv env = new GRBEnv("Gurobi_ILP.log");
			GRBModel model = new GRBModel(env);
			
			TIntObjectHashMap<GRBVar> gurobiVariables = defineGurobiVariables(model);
			
			defineGurobiExclusions(model, gurobiVariables);

			defineGurobiImplications(model, gurobiVariables);

			defineGurobiObjective(model, gurobiVariables);
			
			model.optimize();
			
			int[] result = new int[intToMatch.size()];
			
			intToMatch.keySet().forEach(v -> {
				try {
					if(gurobiVariables.get(v).get(GRB.DoubleAttr.X) > 0)
						result[v-1] = v;
					else
						result[v-1] = -v;
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
		intToMatch.keySet().forEach(v -> {
			try {
				gurobiVariables.put(v, model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x"+v));
			} catch (GRBException e) {
				e.printStackTrace();
			}
			return true;
		});
		return gurobiVariables;
	}

	private void defineGurobiExclusions(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {
		for (HashMap<EObject, TIntHashSet> createToMatch : getInputCreateTables()) {
			for (EObject el : createToMatch.keySet()) {
				TIntHashSet variables = createToMatch.get(el);
				GRBLinExpr expr = new GRBLinExpr();
				variables.forEach(v -> {
					expr.addTerm(1.0, gurobiVars.get(v));
					return true;
				});
				try {
					model.addConstr(expr, GRB.LESS_EQUAL, 1.0, "EXCL" + clauseName++);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void defineGurobiImplications(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {
		for (HashMap<EObject, TIntHashSet> contextToMatch : getContextTables()) {
			for (EObject el : contextToMatch.keySet()) {
				TIntHashSet variables = contextToMatch.get(el);
				variables.forEach(v -> {
				    GRBLinExpr expr = new GRBLinExpr();
					expr.addTerm(1.0, gurobiVars.get(v));
					getCreatorsOf(el).forEach(v2 -> {
						expr.addTerm(-1.0, gurobiVars.get(v2));
						return true;
					});
				    try {
						model.addConstr(expr, GRB.LESS_EQUAL, 0.0, "IMPL"+clauseName++);
					} catch (GRBException e) {
						e.printStackTrace();
					}
					return true;
				});
			}
		}
	}

	protected abstract Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables();

	protected Collection<HashMap<EObject, TIntHashSet>> getContextTables() {
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(contextSrcToMatch);
		result.add(contextCorrToMatch);
		result.add(contextTrgToMatch);
		return result;
	}

	private void defineGurobiObjective(GRBModel model, TIntObjectHashMap<GRBVar> gurobiVars) {
		GRBLinExpr expr = new GRBLinExpr();
		intToMatch.keySet().forEach(v -> {
			int weight = getWeight(intToMatch(v));
			expr.addTerm(weight, gurobiVars.get(v));
			return true;
		});
		try {
			model.setObjective(expr, GRB.MAXIMIZE);
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}

	protected abstract int getWeight(TGGRuleApplication ra);


}
