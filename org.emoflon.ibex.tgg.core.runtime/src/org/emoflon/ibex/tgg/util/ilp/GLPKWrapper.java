/**
 * 
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

/**
 * This class is a wrapper around GLPK allowing the usage of this ILPSolver with
 * the unified API of the {@link ILPSolver} class. To use GLPK you have to
 * download and extract it The files have to be either in on Windows x64:
 * C:\Program Files\GLPK\glpk-4.65 on Windows x86: C:\Program Files
 * (x86)\GLPK\glpk-4.65 or in a directory of your PATH, or you can give the path
 * the vm arguments
 * 
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

	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60 * 60; // 1 hour

	/**
	 * This setting defines the variable range of variables registered at GLPK
	 */
	private final boolean onlyBinaryVariables;

	/**
	 * The GLPK problem
	 */
	private glp_prob problem;

	/**
	 * Adds the specified path to the java library path
	 *
	 * @param pathToAdd
	 *            the path to add
	 * @throws Exception
	 */
	private static void addLibraryPath(String pathToAdd) throws Exception {
		// ClassLoader.getSystemClassLoader().
		final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		// get array of paths
		final String[] paths = (String[]) usrPathsField.get(null);

		// check if the path to add is already present
		for (String path : paths) {
			if (path.equals(pathToAdd)) {
				return;
			}
		}

		// add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length - 1] = pathToAdd;
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
	 * 
	 * @param variable
	 *            the variable to create an entry for
	 * @param variableCounter
	 *            The variable identifier (column counter). Each identifier must be
	 *            unique and should be used incremental
	 */
	private void registerVariable(String variable, int variableID) {
		GLPK.glp_set_col_name(problem, variableID, variable);
		if (this.onlyBinaryVariables) {
			GLPK.glp_set_col_kind(problem, variableID, GLPKConstants.GLP_BV);
			GLPK.glp_set_col_bnds(problem, variableID, GLPKConstants.GLP_DB, 0, 1);
		} else {
			GLPK.glp_set_col_kind(problem, variableID, GLPKConstants.GLP_IV);
			GLPK.glp_set_col_bnds(problem, variableID, GLPKConstants.GLP_FR, 0, 0);
		}
	}

	/**
	 * Registers the constraint in GLPK. Each constraint is a row in the matrix,
	 * containing the information about the used variables and coefficients as well
	 * as the bounds.
	 * 
	 * @param constraint
	 *            The constraint to register
	 * @param constraintCounter
	 *            Incremental counter (used for the index of the row)
	 */
	private void registerConstraint(ILPConstraint constraint, int constraintCounter) {
		GLPK.glp_set_row_name(problem, constraintCounter, constraint.getName());

		int counter = 1;
		int numberOfVariables = constraint.getLinearExpression().getVariables().size();
		SWIGTYPE_p_int variableIndices = GLPK.new_intArray(numberOfVariables + 1);
		SWIGTYPE_p_double coefficients = GLPK.new_doubleArray(numberOfVariables + 1);
		for (int variableId : constraint.getLinearExpression().getVariables()) {
			double coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			GLPK.intArray_setitem(variableIndices, counter, variableId);
			GLPK.doubleArray_setitem(coefficients, counter, coefficient);
			counter++;
		}
		GLPK.glp_set_mat_row(problem, constraintCounter, numberOfVariables, variableIndices, coefficients);
		switch (constraint.getComparator()) {
		case ge:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_LO, constraint.getValue(),
					constraint.getValue());
			break;
		case le:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_UP, constraint.getValue(),
					constraint.getValue());
			break;
		case eq:
			GLPK.glp_set_row_bnds(problem, constraintCounter, GLPKConstants.GLP_FX, constraint.getValue(),
					constraint.getValue());
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: " + constraint.getComparator().toString());
		}
		// Free memory
		GLPK.delete_intArray(variableIndices);
		GLPK.delete_doubleArray(coefficients);
	}

	/**
	 * Registers the objective in GLPK
	 * 
	 * @param objective
	 *            The objective function
	 */
	private void registerObjective(ILPObjective objective) {
		GLPK.glp_set_obj_name(problem, "obj");
		switch (objective.getObjectiveOperation()) {
		case maximize:
			GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MAX);
			break;
		case minimize:
			GLPK.glp_set_obj_dir(problem, GLPKConstants.GLP_MIN);
			break;
		default:
			throw new IllegalArgumentException(
					"Unsupported operation: " + objective.getObjectiveOperation().toString());
		}

		// define terms
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			GLPK.glp_set_obj_coef(problem, variableId, coefficient);
		}

	}

	/**
	 * Creates and fills the GLPK problem
	 */
	private void prepareModel() {
		problem = GLPK.glp_create_prob();
		// register variables
		int numberOfVariables = this.ilpProblem.getVariables().size();
		GLPK.glp_add_cols(problem, numberOfVariables);
		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId);
		}

		registerObjective(this.ilpProblem.getObjective());

		// register constraints
		int counter = 1;
		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			GLPK.glp_add_rows(problem, 1);
			registerConstraint(constraint, counter++);
		}
		GLPK.glp_write_lp(problem, null, "lp.lp");
	}

	/**
	 * Uses GLPK to solve the model
	 * 
	 * @return the GLPK exit code, 0 for success
	 */
	private int solveModel(int timeout) {
		logger.debug("Setting time-limit for each step to " + timeout + " seconds.");
		timeout *= 1000;
		GLPK.glp_scale_prob(problem, GLPK.GLP_SF_AUTO);
		glp_iocp mipParameters = new glp_iocp();
		GLPK.glp_init_iocp(mipParameters);
		// branching
		mipParameters.setBt_tech(GLPK.GLP_BT_BPH);
		// backtracking
		mipParameters.setBr_tech(GLPK.GLP_BR_PCH);
		// cuts
		mipParameters.setGmi_cuts(GLPK.GLP_ON);
		mipParameters.setMir_cuts(GLPK.GLP_ON);
		mipParameters.setCov_cuts(GLPK.GLP_ON);
		mipParameters.setClq_cuts(GLPK.GLP_ON);
		// time limit
		mipParameters.setTm_lim(timeout);
		// gap
		if(this.getSolutionTolerance() != 0) {
			mipParameters.setMip_gap(this.getSolutionTolerance());
		}

		// proximty heuristic
		// mipParameters.setPs_heur(GLPK.GLP_ON);
		// mipParameters.setPs_tm_lim(timeout/10);

		// mipParameters.setFp_heur(GLPK.GLP_ON);
		mipParameters.setSr_heur(GLPK.GLP_ON);

		// presolving
		mipParameters.setPresolve(GLPK.GLP_ON);
		mipParameters.setPp_tech(GLPK.GLP_PP_ALL);

		int exitCode = GLPK.glp_intopt(problem, mipParameters);
		// For debugging: Write model to file
		GLPK.glp_print_mip(problem, "lp_sol_mip.lp");

		return exitCode == GLPK.GLP_ETMLIM ? 0 : exitCode;
	}

	/**
	 * Retrieves the solution from the GLPK model
	 * 
	 * @return the solution containing the variable mapping provided by GLPK
	 * @throws InvalidAttributeValueException
	 */
	private ILPSolution retrieveSolution() throws InvalidAttributeValueException {
		int status = GLPK.glp_mip_status(problem);

		boolean optimal = status == GLPKConstants.GLP_OPT;
		boolean feasible = optimal || status == GLPKConstants.GLP_FEAS;
		if (!feasible) {
			logger.error("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}
		Int2IntOpenHashMap variableSolutions = new Int2IntOpenHashMap();
		double optimum = GLPK.glp_mip_obj_val(problem);
		logger.debug("GLPK found solution: " + optimum + " - Optimal: " + optimal);
		for (int variable : ilpProblem.getVariableIdsOfUnfixedVariables()) {
			double value = GLPK.glp_mip_col_val(problem, variable);
			if (value != 0 && value != 1) {
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			}
			variableSolutions.put(variable, (int) value);
		}
		GLPK.glp_delete_prob(problem);
		ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
		logger.info(solution.getSolutionInformation());
		return solution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#solveILP()
	 */
	@Override
	public ILPSolution solveILP() throws Exception {
		logger.info(this.ilpProblem.getProblemInformation());
		int currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = MIN_TIMEOUT + (int) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, MAX_TIMEOUT);
		if (this.ilpProblem.getVariableIdsOfUnfixedVariables().size() <= 0) {
			return this.ilpProblem.createILPSolution(new Int2IntOpenHashMap(), true, 0);
		}
		try {
			this.prepareModel();
			int exitCode = solveModel(currentTimeout);
			// Retrieve solution
			if (exitCode == 0) {
				return this.retrieveSolution();
			} else {
				logger.error("The problem could not be solved");
				GLPK.glp_delete_prob(problem);
				throw new RuntimeException("The problem could not be solved.");
			}
		} catch (Exception e) {
			logger.error("The problem could not be solved", e);
			throw new RuntimeException("The problem could not be solved.", e);
		}
	}
}