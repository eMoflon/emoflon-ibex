/**
 *
 */
package org.emoflon.ibex.tgg.util.ilp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

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
			GLPKWrapper.addLibraryPath("C:\\Program Files\\GLPK\\glpk-4.65\\w64");
			GLPKWrapper.addLibraryPath("C:\\Program Files (x86)\\GLPK\\glpk-4.65\\w32");
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
	 * @param pathToAdd the path to add
	 * @throws Exception
	 */
	private static void addLibraryPath(final String pathToAdd) throws Exception {
		// ClassLoader.getSystemClassLoader().
		final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		// get array of paths
		final String[] paths = (String[]) usrPathsField.get(null);

		// check if the path to add is already present
		for (String path : paths) {
			if (path.equals(pathToAdd))
				return;
		}

		// add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length - 1] = pathToAdd;
		usrPathsField.set(null, newPaths);
	}

	/**
	 * Creates a new GLPK solver to solve the given problem
	 */
	GLPKWrapper(final ILPProblem ilpProblem, final boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	/**
	 * Creates a new variable entry for the given variable
	 *
	 * @param variable        the variable to create an entry for
	 * @param variableCounter The variable identifier (column counter). Each
	 *                        identifier must be unique and should be used
	 *                        incremental
	 */
	private void registerVariable(final String variable, final int variableID) {
		GLPK.glp_set_col_name(this.problem, variableID, variable);
		if (this.onlyBinaryVariables) {
			GLPK.glp_set_col_kind(this.problem, variableID, GLPKConstants.GLP_BV);
			GLPK.glp_set_col_bnds(this.problem, variableID, GLPKConstants.GLP_DB, 0, 1);
		} else {
			GLPK.glp_set_col_kind(this.problem, variableID, GLPKConstants.GLP_IV);
			GLPK.glp_set_col_bnds(this.problem, variableID, GLPKConstants.GLP_FR, 0, 0);
		}
	}

	/**
	 * Registers the constraint in GLPK. Each constraint is a row in the matrix,
	 * containing the information about the used variables and coefficients as well
	 * as the bounds.
	 *
	 * @param constraint        The constraint to register
	 * @param constraintCounter Incremental counter (used for the index of the row)
	 */
	private void registerConstraint(final ILPConstraint constraint, final int constraintCounter) {
		GLPK.glp_set_row_name(this.problem, constraintCounter, constraint.getName());

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
		GLPK.glp_set_mat_row(this.problem, constraintCounter, numberOfVariables, variableIndices, coefficients);
		switch (constraint.getComparator()) {
		case ge:
			GLPK.glp_set_row_bnds(this.problem, constraintCounter, GLPKConstants.GLP_LO, constraint.getValue(),
					constraint.getValue());
			break;
		case le:
			GLPK.glp_set_row_bnds(this.problem, constraintCounter, GLPKConstants.GLP_UP, constraint.getValue(),
					constraint.getValue());
			break;
		case eq:
			GLPK.glp_set_row_bnds(this.problem, constraintCounter, GLPKConstants.GLP_FX, constraint.getValue(),
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
	 * @param objective The objective function
	 */
	private void registerObjective(final ILPObjective objective) {
		GLPK.glp_set_obj_name(this.problem, "obj");
		switch (objective.getObjectiveOperation()) {
		case maximize:
			GLPK.glp_set_obj_dir(this.problem, GLPKConstants.GLP_MAX);
			break;
		case minimize:
			GLPK.glp_set_obj_dir(this.problem, GLPKConstants.GLP_MIN);
			break;
		default:
			throw new IllegalArgumentException(
					"Unsupported operation: " + objective.getObjectiveOperation().toString());
		}

		// define terms
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			GLPK.glp_set_obj_coef(this.problem, variableId, coefficient);
		}

	}

	/**
	 * Creates and fills the GLPK problem
	 */
	private void prepareModel() {
		this.problem = GLPK.glp_create_prob();
		// register variables
		int numberOfVariables = this.ilpProblem.getVariables().size();
		GLPK.glp_add_cols(this.problem, numberOfVariables);
		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId);
		}

		this.registerObjective(this.ilpProblem.getObjective());

		// register constraints
		int counter = 1;
		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			GLPK.glp_add_rows(this.problem, 1);
			this.registerConstraint(constraint, counter++);
		}
		try {
			File temp = File.createTempFile("lp_", ".lp");
			GLPK.glp_write_lp(this.problem, null, temp.getAbsolutePath());
			temp.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses GLPK to solve the model
	 *
	 * @return the GLPK exit code, 0 for success
	 */
	private int solveModel(int timeout) {
		ILPSolver.logger.debug("Setting time-limit for each step to " + timeout + " seconds.");
		timeout *= 1000;
		GLPK.glp_scale_prob(this.problem, GLPKConstants.GLP_SF_AUTO);
		glp_iocp mipParameters = new glp_iocp();
		GLPK.glp_init_iocp(mipParameters);
		// branching
		mipParameters.setBt_tech(GLPKConstants.GLP_BT_BPH);
		// backtracking
		mipParameters.setBr_tech(GLPKConstants.GLP_BR_PCH);
		// cuts
		mipParameters.setGmi_cuts(GLPKConstants.GLP_ON);
		mipParameters.setMir_cuts(GLPKConstants.GLP_ON);
		mipParameters.setCov_cuts(GLPKConstants.GLP_ON);
		mipParameters.setClq_cuts(GLPKConstants.GLP_ON);
		// time limit
		mipParameters.setTm_lim(timeout);
		// gap
		if (this.getSolutionTolerance() != 0) {
			mipParameters.setMip_gap(this.getSolutionTolerance());
		}

		// proximty heuristic
		// mipParameters.setPs_heur(GLPK.GLP_ON);
		// mipParameters.setPs_tm_lim(timeout/10);

		// mipParameters.setFp_heur(GLPK.GLP_ON);
		mipParameters.setSr_heur(GLPKConstants.GLP_ON);

		// presolving
		mipParameters.setPresolve(GLPKConstants.GLP_ON);
		mipParameters.setPp_tech(GLPKConstants.GLP_PP_ALL);

		int exitCode = GLPK.glp_intopt(this.problem, mipParameters);

		try {
			// For debugging: Write model to file
			File temp = File.createTempFile("sol_mip_", ".lp");
			GLPK.glp_print_mip(this.problem, temp.getAbsolutePath());
			temp.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return exitCode == GLPKConstants.GLP_ETMLIM || exitCode == GLPKConstants.GLP_EMIPGAP ? 0 : exitCode;
	}

	/**
	 * Retrieves the solution from the GLPK model
	 *
	 * @return the solution containing the variable mapping provided by GLPK
	 * @throws InvalidAttributeValueException
	 */
	private ILPSolution retrieveSolution() throws InvalidAttributeValueException {
		int status = GLPK.glp_mip_status(this.problem);

		boolean optimal = status == GLPKConstants.GLP_OPT;
		boolean feasible = optimal || status == GLPKConstants.GLP_FEAS;
		if (!feasible) {
			ILPSolver.logger.error("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}
		IntToIntMap variableSolutions = CollectionFactory.cfactory.createIntToIntMap();
		double optimum = GLPK.glp_mip_obj_val(this.problem);
		ILPSolver.logger.debug("GLPK found solution: " + optimum + " - Optimal: " + optimal);
		for (int variable : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			double value = GLPK.glp_mip_col_val(this.problem, variable);
			if (value != 0 && value != 1)
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			variableSolutions.put(variable, (int) value);
		}
		GLPK.glp_delete_prob(this.problem);
		ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
		ILPSolver.logger.info(solution.getSolutionInformation());
		return solution;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.emoflon.ibex.tgg.util.ilp.ILPSolver#solveILP()
	 */
	@Override
	public ILPSolution solveILP() throws Exception {
		ILPSolver.logger.info(this.ilpProblem.getProblemInformation());
		int currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = GLPKWrapper.MIN_TIMEOUT + (int) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = GLPKWrapper.MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, GLPKWrapper.MAX_TIMEOUT);
		if (this.ilpProblem.getVariableIdsOfUnfixedVariables().size() <= 0)
			return this.ilpProblem.createILPSolution(CollectionFactory.cfactory.createIntToIntMap(), true, 0);
		try {
			this.prepareModel();
			int exitCode = this.solveModel(currentTimeout);
			// Retrieve solution
			if (exitCode == 0)
				return this.retrieveSolution();
			else {
				ILPSolver.logger.error("The problem could not be solved");
				GLPK.glp_delete_prob(this.problem);
				throw new RuntimeException("The problem could not be solved.");
			}
		} catch (Exception e) {
			ILPSolver.logger.error("The problem could not be solved", e);
			throw new RuntimeException("The problem could not be solved.", e);
		}
	}
}