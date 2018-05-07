package org.emoflon.ibex.tgg.util.ilp;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import by.bsu.JVmipshell.LinSum;
import by.bsu.JVmipshell.MIPshell;
import by.bsu.JVmipshell.Var;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class MIPCLWrapper extends ILPSolver {

	static {
		try {
			addLibraryPath("C:\\Program Files\\PNN\\MIPCL\\mip\\bin");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.loadLibrary("JVmipcl");
	}

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

	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60 * 60; // 1 hour

	private MIPshell solver;
	
	private TIntObjectHashMap<Var> variableIdToMIPCLVar = new TIntObjectHashMap<Var>();

	/**
	 * This setting defines the variable range of variables registered at GLPK
	 */
	private final boolean onlyBinaryVariables;

	/**
	 * Creates a new CBCWrapper to solve the problem
	 * 
	 * @param ilpProblem
	 *            The ILP to solve
	 * @param onlyBinaryVariables
	 *            Whether the problem contains only binary variables
	 */
	MIPCLWrapper(ILPProblem ilpProblem, boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	@Override
	public ILPSolution solveILP() throws Exception {
		System.out.println("The ILP to solve has " + this.ilpProblem.getConstraints().size() + " constraints and "
				+ this.ilpProblem.getVariableIdsOfUnfixedVariables().length + " variables");
		if(this.ilpProblem.getVariableIdsOfUnfixedVariables().length < 1) {
			return this.ilpProblem.createILPSolution(new TIntIntHashMap() , true, 0);
		}
		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().length;
		currentTimeout = MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, MAX_TIMEOUT);

		this.prepareModel();
		this.solveModel(currentTimeout);
		ILPSolution solution = this.retrieveSolution();
		return solution;
	}

	/**
	 * Registers all problem contents at the solver
	 */
	private void prepareModel() {
		solver = new MIPshell("ILP problem");
		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId);
		}
		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			registerConstraint(constraint);
		}
		registerObjective(this.ilpProblem.getObjective());
//		solver._print();
	}

	/**
	 * Solves the model using the CBC wrapper
	 * 
	 * @param timeout
	 *            Maximum time to take for solving the problem
	 * @return the result
	 */
	private void solveModel(long timeout) {
		System.out.println("Solving problem with time limit " + timeout + " seconds");
		solver.optimize(false, timeout);
//		solver.mipclModel();
//		solver.optimize();
	}

	/**
	 * Retrieves the solution from the solver
	 * 
	 * @param result
	 *            Result of the solving operation
	 * @return the variable mapping and objective value
	 * @throws InvalidAttributeValueException
	 */
	private ILPSolution retrieveSolution() throws InvalidAttributeValueException {
		boolean optimal = solver.isSolutionOptimal();
		boolean feasible = optimal || solver.isSolution();
		if (!feasible) { 
			System.err.println("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}
		
		TIntIntHashMap variableSolutions = new TIntIntHashMap();
		double optimum = solver.getobjVal();
		System.out.println("Solution found: "+optimum + " - Optimal: "+optimal);
		for(int variable : ilpProblem.getVariableIdsOfUnfixedVariables()) {
			double value = this.variableIdToMIPCLVar.get(variable).getval();
			if(value != 0 && value != 1) {
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			}
			variableSolutions.put(variable, (int) value);
		}
		return this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
	}

	/**
	 * Registers a variable at the solver
	 * 
	 * @param variable
	 *            name of the variable
	 * @param variableID
	 *            ID of the variable
	 */
	private void registerVariable(String variable, int variableID) {
		Var var;
		if(onlyBinaryVariables) {
			var = new Var(solver, variable, MIPshell.VAR_BIN, 0, 1);
		} else {
			var = new Var(solver, variable, MIPshell.VAR_INT, -MIPshell.VAR_INF, MIPshell.VAR_INF);
		}
		this.variableIdToMIPCLVar.put(variableID, var);
	}

	/**
	 * Registers a constraint at the solver
	 * 
	 * @param constraint
	 *            the constrint to register
	 */
	private void registerConstraint(ILPConstraint constraint) {
		LinSum linSum = createLinSum(constraint.getLinearExpression());

		switch (constraint.getComparator()) {
		case ge:
			solver.addCtr(linSum, constraint.getValue(), MIPshell.INF);
			break;
		case le:
			solver.addCtr(linSum, -MIPshell.INF, constraint.getValue());
			break;
		case eq:
			solver.addCtr(linSum, constraint.getValue(), constraint.getValue());
			break;
		default:
			throw new IllegalArgumentException("Unsupported comparator: " + constraint.getComparator().toString());
		}
	}

	/**
	 * Registers the objective at the solver
	 * 
	 * @param objective
	 *            the objective to register
	 */
	private void registerObjective(ILPObjective objective) {
		LinSum linSum = createLinSum(objective.getLinearExpression());
		
		switch (objective.getObjectiveOperation()) {
		case maximize:
			solver.maximize(linSum);
			break;
		case minimize:
			solver.minimize(linSum);
			break;
		default:
			throw new IllegalArgumentException(
					"Unsupported operation: " + objective.getObjectiveOperation().toString());
		}
	}
	
	private LinSum createLinSum(ILPLinearExpression linearExpression) {
		LinSum linSum = new LinSum();
		// define terms
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			linSum.add(coefficient, variableIdToMIPCLVar.get(variableId));
		}
		return linSum;
	}
}
