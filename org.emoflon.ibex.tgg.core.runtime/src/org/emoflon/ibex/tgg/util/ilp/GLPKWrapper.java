/**
 * 
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPTerm;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;

import gnu.trove.map.hash.THashMap;

/**
 * This class is a wrapper around GLPK allowing the usage of this ILPSolver with the unified API of the {@link ILPSolver} class.
 * To use GLPK you have to download and extract it
 * The files have to be either in 
 * on Windows x64: C:\Program Files\GLPK\glpk-4.65 
 * on Windows x86: C:\Program Files (x86)\GLPK\glpk-4.65
 * or in a directory of your PATH, or you can give the path the vm arguments
 * @author Robin Oppermann
 *
 */
final class GLPKWrapper extends ILPSolver {

	/*
	 * Add standard library path when class is loaded
	 */
	static {
		try {
			addLibraryPath("C:\\Program Files\\GLPK\\glpk-4.65\\w64");
			addLibraryPath("C:\\Program Files (x86)\\GLPK\\glpk-4.65\\w32");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * This setting defines the variable range of variables registered at GLPK
	 */
	private final boolean onlyBinaryVariables;
	
	/**
	 * The GLPK problem
	 */
	private glp_prob problem;
	
	/**
	 * Mapping of variables to the integer identifiers used within GLPK
	 */
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
	 * Creates a new GLPK solver to solve the given problem
	 */
	GLPKWrapper(ILPProblem ilpProblem, boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/**
	 * Creates a new variable entry for the given variable
	 * @param variable the variable to create an entry for
	 * @param variableCounter The variable identifier (column counter). Each identifier must be unique and should be used incremental
	 */
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

	/**
	 * Registers the constraint in GLPK. Each constraint is a row in the matrix, containing the information about 
	 * the used variables and coefficients as well as the bounds.
	 * @param constraint The constraint to register
	 * @param constraintCounter Incremental counter (used for the index of the row)
	 */
	private void registerConstraint(ILPConstraint constraint, int constraintCounter){
		GLPK.glp_set_row_name(problem, constraintCounter, constraint.getName());
		
		int counter = 1;
		int numberOfVariables = constraint.getLinearExpression().getTerms().size();
		SWIGTYPE_p_int variableIndices = GLPK.new_intArray(numberOfVariables+1);
		SWIGTYPE_p_double coefficients = GLPK.new_doubleArray(numberOfVariables+1);
		for(ILPTerm term : constraint.getLinearExpression().getTerms()) {
			GLPK.intArray_setitem(variableIndices, counter, glpkVariableCounters.get(term.getVariable()));
			GLPK.doubleArray_setitem(coefficients, counter, term.getCoefficient());
			counter++;
		}
		GLPK.glp_set_mat_row(problem, constraintCounter, numberOfVariables, variableIndices, coefficients);
		switch(constraint.getComparator()) {
		case ge:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_LO, constraint.getValue(), constraint.getValue());
			break;
		case le:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_UP, constraint.getValue(), constraint.getValue());
			break;
		case eq:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_FX, constraint.getValue(), constraint.getValue());
		default:
			throw new IllegalArgumentException("Unsupported comparator: "+constraint.getComparator().toString());
		}
		// Free memory
		GLPK.delete_intArray(variableIndices);
		GLPK.delete_doubleArray(coefficients);
	}
	
	/**
	 * Registers the objective in GLPK
	 * @param objective The objective function
	 */
	private void registerObjective(ILPObjective objective) {
		GLPK.glp_set_obj_name(problem, "obj");
		switch(objective.getObjectiveOperation()) {
		case maximize:
			GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MAX);
			break;
		case minimize:
			GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MIN);
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation: "+objective.getObjectiveOperation().toString());
		}
	
		//define terms
		for(Entry<String, Integer> variable : glpkVariableCounters.entrySet()) {
			//find terms containing the variable
			double currentCoef = 0;
			for(ILPTerm term : objective.getLinearExpression().getTerms()) {
				if(term.getVariable().equals(variable.getKey())) {
					currentCoef += term.getCoefficient();
				}
			}
			GLPK.glp_set_obj_coef(problem, variable.getValue(), currentCoef);
		}
	}

	/* (non-Javadoc)
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#solveILP()
	 */
	@Override
	public ILPSolution solveILP() throws Exception {
		System.out.println("The ILP to solve has "+this.ilpProblem.getConstraints().size()+" constraints and "+this.ilpProblem.getVariables().size()+ " variables");
		System.out.println(this);
		if(this.ilpProblem.getVariables().size() <= 0) {
			return new ILPSolution(new HashMap<String, Integer>(), true, 0);
		}
		try {
			problem = GLPK.glp_create_prob();
			//register variables
			int numberOfVariables = this.ilpProblem.getVariables().size();
			GLPK.glp_add_cols(problem, numberOfVariables);
			int counter = 1;
			for(String variable : this.ilpProblem.getVariables()) {
				this.registerVariable(variable, counter++);
			}
			
			//register constraints
			counter = 1;
			GLPK.glp_add_rows(problem, this.ilpProblem.getConstraints().size());
			for(ILPConstraint constraint : this.ilpProblem.getConstraints()) {
				registerConstraint(constraint, counter++);
			}
			registerObjective(this.ilpProblem.getObjective());

			//Solve model
			glp_smcp parm= new glp_smcp();
			GLPK.glp_init_smcp(parm);
			GLPK.glp_write_lp(problem, null, "lp.lp");
			int exitCode = GLPK.glp_simplex(problem, parm);
			exitCode += GLPK.glp_intopt(problem, null);
			//For debugging: Write model to file
			GLPK.glp_print_sol(problem, "lp_sol_base.lp");
			GLPK.glp_print_mip(problem, "lp_sol_mip.lp");
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
				double optimum = GLPK.glp_mip_obj_val(problem);
				System.out.println("Solution found: "+optimum + " - Optimal: "+optimal);
				for(Entry<String, Integer> variable : glpkVariableCounters.entrySet()) {
					double value = GLPK.glp_mip_col_val(problem, variable.getValue());
					if(value != 0 && value != 1) {
						throw new InvalidAttributeValueException("Solution may only be 0 or 1");
					}
					variableSolutions.put(variable.getKey(), (int) value);
				}
				GLPK.glp_delete_prob(problem);
				ILPSolution solution = new ILPSolution(variableSolutions, optimal, optimum);
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
}