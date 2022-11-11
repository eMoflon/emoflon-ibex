package org.emoflon.ibex.tgg.util.ilp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import by.bsu.JVmipcl.LP;
import by.bsu.JVmipcl.MIP;
import by.bsu.JVmipshell.LPshell;

final class MIPCLWrapper2 extends ILPSolver {

	static {
		try {
			MIPCLWrapper2.addLibraryPath("C:\\Program Files\\PNN\\MIPCL\\mip\\bin");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.loadLibrary("JVmipcl");
	}

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

	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60 * 60; // 1 hour

	private MIP solver;

	private final IntToIntMap variableIdToMIPCLVar = CollectionFactory.cfactory.createIntToIntMap();
	private final IntToIntMap MIPCLVarTovariableId = CollectionFactory.cfactory.createIntToIntMap();

	/**
	 * This setting defines the variable range of variables registered at GLPK
	 */
	private final boolean onlyBinaryVariables;

	/**
	 * Creates a new CBCWrapper to solve the problem
	 *
	 * @param ilpProblem          The ILP to solve
	 * @param onlyBinaryVariables Whether the problem contains only binary variables
	 */
	MIPCLWrapper2(final ILPProblem ilpProblem, final boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	@Override
	public ILPSolution solveILP() throws Exception {
		ILPSolver.logger.info(this.ilpProblem.getProblemInformation());
		if (this.ilpProblem.getVariableIdsOfUnfixedVariables().size() < 1)
			return this.ilpProblem.createILPSolution(CollectionFactory.cfactory.createIntToIntMap(), true, 0);
		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = MIPCLWrapper2.MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = MIPCLWrapper2.MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, MIPCLWrapper2.MAX_TIMEOUT);

		this.prepareModel();
		this.solveModel(currentTimeout);
		ILPSolution solution = this.retrieveSolution();
		return solution;
	}

	/**
	 * Registers all problem contents at the solver
	 */
	private void prepareModel() {
		this.solver = new MIP("ILP problem");

		Set<Integer> variables = this.ilpProblem.getVariableIdsOfUnfixedVariables();
		Collection<ILPConstraint> constraints = this.ilpProblem.getConstraints();

		this.solver.openMatrix(constraints.size(), variables.size(), constraints.size() * variables.size());

		int varCounter = 0;
		for (int variableId : variables) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId, varCounter);
			varCounter++;
		}

		int constraintCounter = 0;
		for (ILPConstraint constraint : constraints) {
			this.registerConstraint(constraint, constraintCounter);
			constraintCounter++;
		}

		this.registerObjective(this.ilpProblem.getObjective());
		// solver._print();

		this.solver.closeMatrix();
	}

	/**
	 * Solves the model using the CBC wrapper
	 *
	 * @param timeout Maximum time to take for solving the problem
	 * @return the result
	 * @throws IOException
	 */
	private void solveModel(final long timeout) throws IOException {
		ILPSolver.logger.debug("Solving problem with time limit " + timeout + " seconds");
		File tempFile = File.createTempFile("ilpSol_", ".mipcl");
		tempFile.deleteOnExit();
		this.solver.optimize(timeout, this.getSolutionTolerance() * 100, tempFile.getAbsolutePath());
		// this.solver.printSolution("sol.mipcl");
	}

	/**
	 * Retrieves the solution from the solver
	 *
	 * @param result Result of the solving operation
	 * @return the variable mapping and objective value
	 * @throws InvalidAttributeValueException
	 */
	private ILPSolution retrieveSolution() throws InvalidAttributeValueException {
		boolean optimal = this.solver.isSolutionOptimal();
		boolean feasible = optimal || this.solver.isSolution();
		if (!feasible) {
			ILPSolver.logger.error("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}

		IntToIntMap variableSolutions = CollectionFactory.cfactory.createIntToIntMap();
		double optimum = this.solver.getObjVal();
		ILPSolver.logger.debug("MIPCL found solution: " + optimum + " - Optimal: " + optimal);

		this.solver.getSolution();
		int[] varHandles = this.solver.getVarHandles();
		double[] values = this.solver.getX();

		this.solver.dispose();

		for (int index = 0; index < varHandles.length; index++) {
			int variableID = this.MIPCLVarTovariableId.get(varHandles[index]);
			double value = values[index];
			if (value != 0 && value != 1)
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			variableSolutions.put(variableID, (int) value);
		}

		ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
		ILPSolver.logger.info(solution.getSolutionInformation());
		return solution;
	}

	/**
	 * Registers a variable at the solver
	 *
	 * @param variable   name of the variable
	 * @param variableID ID of the variable
	 */
	private void registerVariable(final String variable, final int variableID, final int counter) {
		if (this.onlyBinaryVariables) {
			this.solver.addVar(counter, MIP.VAR_BIN, 1, 0, 1);
		} else {
			this.solver.addVar(counter, MIP.VAR_INT, 0, -LP.VAR_INF, LP.VAR_INF);
		}
		this.variableIdToMIPCLVar.put(variableID, counter);
		this.MIPCLVarTovariableId.put(counter, variableID);
	}

	/**
	 * Registers a constraint at the solver
	 *
	 * @param constraint the constrint to register
	 */
	private void registerConstraint(final ILPConstraint constraint, final int counter) {
		Object[] linSum = this.createLinSum(constraint.getLinearExpression());
		int[] cols = (int[]) linSum[0];
		double[] vals = (double[]) linSum[1];

		switch (constraint.getComparator()) {
			case ge -> this.solver.addRow(counter, 0, constraint.getValue(), LPshell.INF, vals, cols, false);
			case le -> this.solver.addRow(counter, 0, -LPshell.INF, constraint.getValue(), vals, cols, false);
			case eq -> this.solver.addRow(counter, 0, constraint.getValue(), constraint.getValue(), vals, cols, false);
			default -> throw new IllegalArgumentException("Unsupported comparator: " + constraint.getComparator().toString());
		}
	}

	/**
	 * Registers the objective at the solver
	 *
	 * @param objective the objective to register
	 */
	private void registerObjective(final ILPObjective objective) {
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			this.solver.setObjCoeff(this.variableIdToMIPCLVar.get(variableId), coefficient);
		}

		switch (objective.getObjectiveOperation()) {
			case maximize -> this.solver.setObjSense(true);
			case minimize -> this.solver.setObjSense(false);
			default -> throw new IllegalArgumentException("Unsupported operation: " + objective.getObjectiveOperation().toString());
		}
	}

	private Object[] createLinSum(final ILPLinearExpression linearExpression) {
		int[] columns = new int[linearExpression.getVariables().size()];
		double[] values = new double[linearExpression.getVariables().size()];
		int counter = 0;
		// define terms
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			columns[counter] = this.variableIdToMIPCLVar.get(variableId);
			values[counter] = coefficient;
			counter++;
		}
		return new Object[] { columns, values };
	}
}
