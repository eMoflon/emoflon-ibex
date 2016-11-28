package org.emoflon.ibex.tgg.operational;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.hash.TIntHashSet;
import language.TGG;
import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;
import runtime.Edge;
import runtime.TGGRuleApplication;

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
		//created Edges must be redefined
		createdEdges = new ArrayList<>();
		
		protocolR.getContents().forEach(c ->{
			if(c instanceof TGGRuleApplication)
				calculateTables((TGGRuleApplication) c);
		});
		
		if(matchToInt.size() == 0)
			return;
		
		SolverFactory factory = new SolverFactoryGurobi();
		factory.setParameter(Solver.VERBOSE, 0);
		int all = 0;
		int chosen = 0;
		for (int v : getArrayFromResult(factory.get().solve(prepareProblem()))) {
			if (v < 0) {
				eliminate(intToMatch(-v));
				all++;
			}
			else{
				all++;
				chosen++;
				createdEdges.addAll(getOutputEdgesOf(intToMatch(v)));
				intToMatch(v).setFinal(true);
			}
		}
		System.out.println(chosen + " of " + all + " rule applications are chosen");
	}

	protected abstract Collection<Edge> getOutputEdgesOf(TGGRuleApplication ra);

	private Problem prepareProblem() {

		Problem ilpProblem = new Problem();

		defineExclusions(ilpProblem);

		defineImplications(ilpProblem);

		defineVariableTypesAsBoolean(ilpProblem);

		defineObjective(ilpProblem);

		return ilpProblem;
	}

	private void defineExclusions(Problem ilpProblem) {
		for (HashMap<EObject, TIntHashSet> createToMatch : getInputCreateTables()) {
			for (EObject el : createToMatch.keySet()) {
				TIntHashSet variables = createToMatch.get(el);
				Linear linear = new Linear();
				variables.forEach(v -> {
					linear.add(1, v);
					return true;
				});
				ilpProblem.add(new Constraint(String.valueOf(clauseName++), linear, "<=", 1));
			}
		}
	}

	private void defineImplications(Problem ilpProblem) {
		for (HashMap<EObject, TIntHashSet> contextToMatch : getContextTables()) {
			for (EObject el : contextToMatch.keySet()) {
				TIntHashSet variables = contextToMatch.get(el);
				variables.forEach(v -> {
					Linear linear = new Linear();
					linear.add(1, v);
					getCreatorsOf(el).forEach(v2 -> {
						linear.add(-1, v2);
						return true;
					});
					ilpProblem.add(new Constraint(String.valueOf(clauseName++), linear, "<=", 0));
					return true;
				});
			}
		}
	}
	
	private void defineVariableTypesAsBoolean(Problem ilpProblem) {
		intToMatch.keySet().forEach(v -> {
			ilpProblem.setVarType(v, Boolean.class);
			return true;
		});
	}

	protected abstract Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables();

	protected Collection<HashMap<EObject, TIntHashSet>> getContextTables() {
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(contextSrcToMatch);
		result.add(contextCorrToMatch);
		result.add(contextTrgToMatch);
		return result;
	}
	
	private void defineObjective(Problem ilpProblem) {
		Linear objective = new Linear();
		intToMatch.keySet().forEach(v -> {
			int weight = getWeight(intToMatch(v));
			objective.add(weight, v);
			return true;
		});
		ilpProblem.setObjective(objective, OptType.MAX);
	}

	protected abstract int getWeight(TGGRuleApplication ra);
	
	protected int[] getArrayFromResult(Result result) {

		String[] resultPartials = result.toString().split(", ");

		// cutting clutter at start and finish
		resultPartials[0] = resultPartials[0].split("\\{")[1];
		resultPartials[resultPartials.length - 1] = resultPartials[resultPartials.length - 1].split("\\}")[0];

		int[] returnArray = new int[intToMatch.keySet().size()];
		int returnArrayPos = 0;

		for (int i = 0; i < resultPartials.length; i++) {
			// solver also reports results of formulas, this rules them out
			if (!resultPartials[i].contains(".")) {

				String[] eval = resultPartials[i].split("=");

				int identifier = Integer.parseInt(eval[0]);

				// negate identifier if equals 0
				if (Integer.parseInt(eval[1]) == 0) {
					identifier = 0 - identifier;
				}
				returnArray[returnArrayPos] = identifier;
				returnArrayPos++;
			}
		}

		return returnArray;
	}
	
}
