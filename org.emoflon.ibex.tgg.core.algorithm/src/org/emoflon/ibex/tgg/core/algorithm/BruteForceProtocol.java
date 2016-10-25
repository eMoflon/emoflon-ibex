package org.emoflon.ibex.tgg.core.algorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import org.eclipse.emf.ecore.EObject;
import gnu.trove.set.hash.TIntHashSet;
import net.sf.javailp.Constraint;
import net.sf.javailp.Linear;
import net.sf.javailp.Problem;
import net.sf.javailp.Result;
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryGurobi;

public abstract class BruteForceProtocol extends Protocol {

	private int name = 0;

	public void filter() {
		SolverFactory factory = new SolverFactoryGurobi();
		factory.setParameter(Solver.VERBOSE, 0);
		for (int v : getArrayFromResult(factory.get().solve(prepareProblem()))) {
			if (v < 0) {
				eliminate(intToMatch(-v));
			}
		}
	}

	private Problem prepareProblem() {

		Problem ilpProblem = new Problem();

		defineExclusions(ilpProblem);

		defineImplications(ilpProblem);

		defineVariableTypesAsBoolean(ilpProblem);

		defineObjective(ilpProblem);

		return ilpProblem;
	}

	protected abstract Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables();

	protected Collection<HashMap<EObject, TIntHashSet>> getContextTables(){
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(contextSrcToMatch);
		result.add(contextCorrToMatch);
		result.add(contextTrgToMatch);
		return result;
	}

	protected void eliminate(TGGRuleApplication m){
		deleteOutputElementsOf(m);
	}

	protected abstract void deleteOutputElementsOf(TGGRuleApplication m);
	
	protected void deleteElements(Collection<EObject> elts) {
		elts.forEach(e -> e.eResource().getContents().remove(e));
	}

	// ILP-related preparations

	private void defineExclusions(Problem ilpProblem) {
		for (HashMap<EObject, TIntHashSet> createToMatch : getInputCreateTables()) {
			for (EObject el : createToMatch.keySet()) {
				TIntHashSet variables = createToMatch.get(el);
				Linear linear = new Linear();
				variables.forEach(v -> {
					linear.add(1, v);
					return true;
				});
				ilpProblem.add(new Constraint(String.valueOf(name++), linear, "<=", 1));
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
					ilpProblem.add(new Constraint(String.valueOf(name++), linear, "<=", 0));
					return true;
				});
			}
		}
	}

	private void defineObjective(Problem ilpProblem) {
		Linear objective = new Linear();
		intToMatch.keySet().forEach(v -> {
			int weight = getWeight(intToMatch(v));
			objective.add(weight, v);
			return true;
		});
		ilpProblem.setObjective(objective);
	}

	protected abstract int getWeight(TGGRuleApplication m);

	private void defineVariableTypesAsBoolean(Problem ilpProblem) {
		intToMatch.keySet().forEach(v -> {
			ilpProblem.setVarType(v, Boolean.class);
			return true;
		});
	}

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
