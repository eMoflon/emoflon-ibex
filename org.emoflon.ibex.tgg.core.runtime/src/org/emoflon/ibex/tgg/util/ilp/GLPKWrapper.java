/**
 * 
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;

import gnu.trove.map.hash.THashMap;

/**
 * @author RobinO
 *
 */
public final class GLPKWrapper extends ILPSolver {

	static {
		try {
			addLibraryPath("C:\\Program Files\\GLPK\\glpk-4.65\\w64");
			addLibraryPath("C:\\Program Files (x86)\\GLPK\\glpk-4.65\\w32");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This setting defines the variable range of variables registered at Gurobi
	 */
	private final boolean onlyBinaryVariables;
	private glp_prob problem;
	private final THashMap<String, Integer> glpkVariableCounters = new THashMap<String, Integer>();


	/**
	 * Adds the specified path to the java library path
	 *
	 * @param pathToAdd the path to add
	 * @throws Exception
	 */
	private static void addLibraryPath(String pathToAdd) throws Exception{
		//		ClassLoader.getSystemClassLoader().
		final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		//get array of paths
		final String[] paths = (String[])usrPathsField.get(null);

		//check if the path to add is already present
		for(String path : paths) {
			if(path.equals(pathToAdd)) {
				return;
			}
		}

		//add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length-1] = pathToAdd;
		usrPathsField.set(null, newPaths);
	}

	/**
	 * 
	 */
	GLPKWrapper(boolean onlyBinaryVariables) {
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#createLinearExpression(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPTerm[])
	 */
	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		ILPLinearExpression expr = new GLPKLinearExression();
		for (ILPTerm term : terms) {
			expr.addTerm(term);
		}
		return expr;
	}

	private void registerVariable(String variable, int variableCounter) {
		this.glpkVariableCounters.put(variable, variableCounter);
		GLPK.glp_set_col_name(problem, variableCounter, variable);
		if(this.onlyBinaryVariables) {
			GLPK.glp_set_col_kind(problem, variableCounter, GLPKConstants.GLP_BV);
			GLPK.glp_set_col_bnds(problem, variableCounter, GLPKConstants.GLP_DB, 0, 1);
		} else {
			GLPK.glp_set_col_kind(problem, variableCounter, GLPKConstants.GLP_IV);
			GLPK.glp_set_col_bnds(problem, variableCounter, GLPKConstants.GLP_FR, 0, 0);
		}
		
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#addConstraint(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPLinearExpression, org.emoflon.ibex.tgg.util.ilp.ILPSolver.Operation, double, java.lang.String)
	 */
	@Override
	public ILPConstraint addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value,
			String name) {
		ILPConstraint constr = new GLPKConstraint(linearExpression, comparator, value, name);
		this.addConstraint(constr);
		return constr;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#setObjective(org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPLinearExpression, org.emoflon.ibex.tgg.util.ilp.ILPSolver.Operation)
	 */
	@Override
	public ILPObjective setObjective(ILPLinearExpression linearExpression, Operation operation) {
		ILPObjective objective = new GLPKObjective(linearExpression, operation);
		this.setObjective(objective);
		return objective;
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#solveILP()
	 */
	@Override
	public ILPSolution solveILP() throws Exception {
		System.out.println("The ILP to solve has "+this.getConstraints().size()+" constraints and "+this.getVariables().size()+ " variables");
		System.out.println(this);
		if(this.getVariables().size() <= 0) {
			return new ILPSolution(new HashMap<String, Integer>(), true, 0);
		}
		try {
			problem = GLPK.glp_create_prob();
			//register variables
			int numberOfVariables = this.getVariables().size();
			GLPK.glp_add_cols(problem, numberOfVariables);
			int counter = 1;
			for(String variable : this.getVariables()) {
				this.registerVariable(variable, counter++);
			}
			
			//register constraints
			counter = 1;
			GLPK.glp_add_rows(problem, this.getConstraints().size());
			for(ILPConstraint constraint : this.getConstraints()) {
				((GLPKConstraint) constraint).registerConstraint(counter++);
				//For some reason this needs some time. If we just continue the program will terminate
//				Thread.sleep(1000);
			}
			((GLPKObjective) this.getObjective()).registerObjective();

			//Solve model
			glp_smcp parm= new glp_smcp();
			GLPK.glp_init_smcp(parm);
			int exitCode = GLPK.glp_simplex(problem, parm);
			//For debugging: Write model to file
			GLPK.glp_write_lp(problem, null, "lp.lp");
			// Retrieve solution
			if (exitCode == 0) {
				int status = GLPK.glp_get_status(problem);
				
				boolean optimal = status == GLPKConstants.GLP_OPT;
				boolean feasible = optimal || status == GLPKConstants.GLP_FEAS;
				if (!feasible) { 
					System.err.println("No optimal or feasible solution found.");
					return null;
				}
				Map<String, Integer> variableSolutions = new HashMap<String, Integer>();
				double optimum = GLPK.glp_get_obj_val(problem);
				System.out.println("Solution found: "+optimum + " - Optimal: "+optimal);
				for(Entry<String, Integer> variable : glpkVariableCounters.entrySet()) {
					double value = GLPK.glp_get_col_prim(problem, variable.getValue());
					variableSolutions.put(variable.getKey(), (int) value);
				}
				GLPK.glp_delete_prob(problem);
				ILPSolution solution = new ILPSolution(variableSolutions, optimal, optimum);
				System.out.println(solution);
				return solution;
			} else {
				System.err.println("The problem could not be solved");
				GLPK.glp_delete_prob(problem);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private class GLPKConstraint extends ILPConstraint{
		private GLPKConstraint(ILPLinearExpression linearExpression, Operation comparator, double value,
				String name) {
			super(linearExpression, comparator, value, name);
		}

		private void registerConstraint(int constraintCounter) throws InterruptedException {
			GLPK.glp_set_row_name(problem, constraintCounter, this.name);
			
			int counter = 1;
			int numberOfVariables = this.linearExpression.getTerms().size();
			SWIGTYPE_p_int variableIndices = GLPK.new_intArray(numberOfVariables+1);
			SWIGTYPE_p_double coefficients = GLPK.new_doubleArray(numberOfVariables+1);
			for(ILPTerm term : this.linearExpression.getTerms()) {
				GLPK.intArray_setitem(variableIndices, counter, glpkVariableCounters.get(term.getVariable()));
				GLPK.doubleArray_setitem(coefficients, counter, term.getCoefficient());
				counter++;
			}
			GLPK.glp_set_mat_row(problem, constraintCounter, numberOfVariables, variableIndices, coefficients);
			switch(comparator) {
			case ge:
				GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_LO, this.value, this.value);
				break;
			case le:
				GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_UP, this.value, this.value);
				break;
			case eq:
				GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_FX, this.value, this.value);
			default:
				throw new IllegalArgumentException("Unsupported comparator: "+comparator.toString());
			}
			// Free memory
			GLPK.delete_intArray(variableIndices);
			GLPK.delete_doubleArray(coefficients);
		}
	}

	private class GLPKObjective extends ILPObjective {
		private GLPKObjective(ILPLinearExpression linearExpression, Operation objectiveOperation) {
			super(linearExpression, objectiveOperation);
		}

		private void registerObjective() {
			GLPK.glp_set_obj_name(problem, "obj");
			switch(this.objectiveOperation) {
			case maximize:
				GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MAX);
				break;
			case minimize:
				GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MIN);
				break;
			default:
				throw new IllegalArgumentException("Unsupported operation: "+objectiveOperation.toString());
			}

			//define terms
			for(Entry<String, Integer> variable : glpkVariableCounters.entrySet()) {
				//find terms containing the variable
				double currentCoef = 0;
				for(ILPTerm term : this.linearExpression.getTerms()) {
					if(term.getVariable().equals(variable.getKey())) {
						currentCoef += term.getCoefficient();
					}
				}
				GLPK.glp_set_obj_coef(problem, variable.getValue(), currentCoef);
			}
		}
	}

	private class GLPKLinearExression extends ILPLinearExpression {
	}
}
