package org.emoflon.ibex.tgg.util.ilp;

import javax.naming.directory.InvalidAttributeValueException;

import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPSolver.ResultStatus;
import com.google.ortools.linearsolver.MPVariable;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * This class is a wrapper that allows usage of the CBC solver (<href a=
 * "https://projects.coin-or.org/Cbc">https://projects.coin-or.org/Cbc</href>)<br>
 *
 * The access is done using the Google Operations Research library which can be
 * found at <href a=
 * "https://developers.google.com/optimization/">https://developers.google.com/optimization/</href>
 *
 * @author RobinO
 *
 */
final class CBCWrapper extends ILPSolver {

	/**
	 * The solver model
	 */
	private MPSolver solver;

	/**
	 * This setting defines the variable range of variables registered at GLPK
	 */
	private final boolean onlyBinaryVariables;

	/**
	 * Mapping of the internally registered variables to the variable Ids of the
	 * problem
	 */
	private final Int2ObjectOpenHashMap<MPVariable> variableIdToCBCVar = new Int2ObjectOpenHashMap<MPVariable>();

	private static final int MIN_TIMEOUT = 30;
	private static final int MAX_TIMEOUT = 60 * 60; // 1 hour

	/**
	 * static loading of required library when loading class
	 */
	static {
		if (System.getProperty("os.name").startsWith("Windows")) {
			//			final URL classResource = MPSolver.class.getResource(MPSolver.class.getSimpleName() + ".class");
			//
			//			final String url = classResource.toString();
			//			final String suffix = MPSolver.class.getCanonicalName().replace('.', '/') + ".class";
			//
			//			// strip the class's path from the URL string
			//			final String base = url.substring(0, url.length() - suffix.length());
			//
			//			String path = base;
			//
			//			// remove the "jar:" prefix and "!/" suffix, if present
			//			if (path.startsWith("jar:"))
			//				path = path.substring(4, path.length() - 2);
			//			try {
			//				File jar = new File(new URI(path));
			//				System.load(jar.getParent() + "/pthreadVC2.dll");
			//				System.load(jar.getParent() + "/jniortools.dll");
			//			} catch (URISyntaxException e) {
			//				e.printStackTrace();
			//			}
			System.loadLibrary("pthreadVC2");
			System.loadLibrary("jniortools");
		} else {
			try {
				System.loadLibrary("jniortools");
			} catch (Exception e) {
				ILPSolver.logger.error(
						"Could not load library: jniortools \n The library can be obtained following the instructions at https://developers.google.com/optimization/introduction/installing/binary",
						e);
				throw e;
			}
		}
	}

	/**
	 * Creates a new CBCWrapper to solve the problem
	 *
	 * @param ilpProblem
	 *            The ILP to solve
	 * @param onlyBinaryVariables
	 *            Whether the problem contains only binary variables
	 */
	CBCWrapper(final ILPProblem ilpProblem, final boolean onlyBinaryVariables) {
		super(ilpProblem);
		this.onlyBinaryVariables = onlyBinaryVariables;
	}

	@Override
	public ILPSolution solveILP() throws Exception {
		ILPSolver.logger.info(this.ilpProblem.getProblemInformation());
		long currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = CBCWrapper.MIN_TIMEOUT + (long) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = CBCWrapper.MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, CBCWrapper.MAX_TIMEOUT);

		this.prepareModel();
		ResultStatus result = this.solveModel(currentTimeout);
		ILPSolution solution = this.retrieveSolution(result);
		return solution;
	}

	/**
	 * Registers all problem contnets at the solver
	 */
	private void prepareModel() {
		this.solver = new MPSolver("ILPSolver", MPSolver.OptimizationProblemType.CBC_MIXED_INTEGER_PROGRAMMING);

		for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			this.registerVariable(this.ilpProblem.getVariable(variableId), variableId);
		}
		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			this.registerConstraint(constraint);
		}
		this.registerObjective(this.ilpProblem.getObjective());
	}

	/**
	 * Sloves the model using the CBC wrapper
	 *
	 * @param timeout
	 *            Maximum time to take for solving the problem
	 * @return the result
	 */
	private ResultStatus solveModel(long timeout) {
		this.solver.enableOutput();
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		String parameters = "-threads " + numberOfThreads;
		if(this.getSolutionTolerance() != 0) {
			parameters += " -ratio "+this.getSolutionTolerance();
		}
		this.solver.setSolverSpecificParametersAsString(parameters);
		ILPSolver.logger.debug("Setting time-limit for each step to " + timeout + " seconds.");
		timeout *= 1000;
		this.solver.setTimeLimit(timeout);
		return this.solver.solve();
	}

	/**
	 * Retrieves the solution from the solver
	 *
	 * @param result
	 *            Result of the solving operation
	 * @return the variable mapping and objective value
	 * @throws InvalidAttributeValueException
	 */
	private ILPSolution retrieveSolution(final ResultStatus result) throws InvalidAttributeValueException {
		boolean optimal = result == ResultStatus.OPTIMAL;
		boolean feasible = optimal || result == ResultStatus.FEASIBLE;
		if (!feasible) {
			ILPSolver.logger.error("No optimal or feasible solution found.");
			throw new RuntimeException("No optimal or feasible solution found.");
		}

		Int2IntOpenHashMap variableSolutions = new Int2IntOpenHashMap();
		double optimum = this.solver.objective().value();
		ILPSolver.logger.debug("CBC found solution: " + optimum + " - Optimal: " + optimal);
		for (int variable : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
			double value = this.variableIdToCBCVar.get(variable).solutionValue();
			if (value != 0 && value != 1) {
				throw new InvalidAttributeValueException("Solution may only be 0 or 1");
			}
			variableSolutions.put(variable, (int) value);
		}
		ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
		ILPSolver.logger.info(solution.getSolutionInformation());
		return solution;
	}

	/**
	 * Registers a variable at the solver
	 *
	 * @param variable
	 *            name of the variable
	 * @param variableID
	 *            ID of the variable
	 */
	private void registerVariable(final String variable, final int variableID) {
		MPVariable var;
		if (this.onlyBinaryVariables) {
			var = this.solver.makeBoolVar(variable);
		} else {
			var = this.solver.makeIntVar(-MPSolver.infinity(), MPSolver.infinity(), variable);
		}
		this.variableIdToCBCVar.put(variableID, var);
	}

	/**
	 * Registers a constraint at the solver
	 *
	 * @param constraint
	 *            the constrint to register
	 */
	private void registerConstraint(final ILPConstraint constraint) {
		MPConstraint constr = this.solver.makeConstraint(constraint.getName());

		for (int variableId : constraint.getLinearExpression().getVariables()) {
			double coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			constr.setCoefficient(this.variableIdToCBCVar.get(variableId), coefficient);
		}

		switch (constraint.getComparator()) {
		case ge:
			constr.setBounds(constraint.getValue(), MPSolver.infinity());
			break;
		case le:
			constr.setBounds(-MPSolver.infinity(), constraint.getValue());
			break;
		case eq:
			constr.setBounds(constraint.getValue(), constraint.getValue());
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
	private void registerObjective(final ILPObjective objective) {
		MPObjective obj = this.solver.objective();
		switch (objective.getObjectiveOperation()) {
		case maximize:
			obj.setMaximization();
			break;
		case minimize:
			obj.setMinimization();
			break;
		default:
			throw new IllegalArgumentException(
					"Unsupported operation: " + objective.getObjectiveOperation().toString());
		}

		// define terms
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			obj.setCoefficient(this.variableIdToCBCVar.get(variableId), coefficient);
		}
	}
}
