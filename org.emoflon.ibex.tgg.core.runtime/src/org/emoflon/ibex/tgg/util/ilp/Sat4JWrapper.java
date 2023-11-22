package org.emoflon.ibex.tgg.util.ilp;

import java.math.BigInteger;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.common.collections.IntToIntMap;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.util.ConsoleUtil;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPConstraint;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPLinearExpression;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPObjective;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.ObjectiveFunction;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.pb.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

/**
 * This class is a wrapper around SAT4J allowing the usage of this ILPSolver
 * with the unified API of the {@link ILPSolver} class. SAT4J comes with eclipse
 * but is only able to solve pseudo-boolean ILP problems. To use SAT4J your
 * project has to have Sat4J as Plugin Dependencies.
 *
 * The SAT4J Javadocs can be found at:
 * <li>http://www.sat4j.org/maven234/org.ow2.sat4j.pb/apidocs/index.html</li>
 * <li>http://www.sat4j.org/maven234/org.ow2.sat4j.core/apidocs/index.html</li>
 *
 * @author Robin Oppermann
 *
 */
final class Sat4JWrapper extends ILPSolver {
	/**
	 * The SAT4J pseudo-boolean solver
	 */
	private IPBSolver solver;

	private static final int MIN_TIMEOUT = 3;
	private static final int MAX_TIMEOUT = 60 * 60;

	/**
	 * Contains the mapping of the problem's variable IDs to SAT4J's variable IDs
	 */
	private final IntToIntMap variableIdToSat4JVarId = CollectionFactory.cfactory.createIntToIntMap();

	/**
	 * Creates a new SAT4JWrapper
	 */
	Sat4JWrapper(final ILPProblem ilpProblem) {
		super(ilpProblem);
	}

	@Override
	public ILPSolution solveILP() throws ContradictionException {
		ILPSolver.logger.info(this.ilpProblem.getProblemInformation());
		int currentTimeout = this.ilpProblem.getVariableIdsOfUnfixedVariables().size();
		currentTimeout = Sat4JWrapper.MIN_TIMEOUT + (int) Math.ceil(Math.pow(1.16, Math.sqrt(currentTimeout)));
		if (currentTimeout < 0) {
			currentTimeout = Sat4JWrapper.MAX_TIMEOUT;
		}
		currentTimeout = Math.min(currentTimeout, Sat4JWrapper.MAX_TIMEOUT);
		ILPSolution solution = null;
		while (solution == null) {
			ILPSolver.logger.debug("Attempting to solve ILP. Timeout=" + currentTimeout + " seconds.");
			try {
				solution = this.solveILP(currentTimeout);
			} catch (TimeoutException e) {
				ILPSolver.logger.warn("Could not solve ILP within " + currentTimeout + " seconds");
				currentTimeout *= 2;
				if (currentTimeout > Sat4JWrapper.MAX_TIMEOUT)
					throw new RuntimeException("Could not solve ILP within " + currentTimeout + " seconds", e);
			}
		}
		return solution;
	}

	/**
	 * Starts the solver with the specified timeout.
	 *
	 * @param timeout The timeout for the solver. If the timeout is too low it might
	 *                happen that
	 *                <li>the solver does not find a solution even though there is
	 *                one</li>
	 *                <li>the solver finds a solution but it is not the optimal
	 *                solution yet</li>
	 * @return
	 * @throws ContradictionException
	 * @throws TimeoutException
	 */
	private ILPSolution solveILP(final int timeout) throws ContradictionException, TimeoutException {
		this.solver = SolverFactory.newDefaultOptimizer();

		for (ILPConstraint constraint : this.ilpProblem.getConstraints()) {
			this.registerConstraint(constraint);
		}
		this.registerObjective(this.ilpProblem.getObjective());

		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(this.solver));
		optimizer.setTimeout(timeout);
		optimizer.setVerbose(true);
		if (ConsoleUtil.suppressUnwantedPrints(!LoggerConfig.log_ilp_extended(), () -> optimizer.isSatisfiable())) {
			int[] model = this.solver.model();
			IntToIntMap variableSolutions = CollectionFactory.cfactory.createIntToIntMap();
			for (int i : model) {
				int solution = i > 0 ? 1 : 0;
				for (int variableId : this.ilpProblem.getVariableIdsOfUnfixedVariables()) {
					int sat4JVariable = this.variableIdToSat4JVarId.get(variableId);
					if (Math.abs(i) == sat4JVariable) {
						variableSolutions.put(variableId, solution);
						break;
					}
				}
			}
			boolean optimal = optimizer.isOptimal();
			ILPSolution solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, -1);
			double optimum = this.ilpProblem.getObjective().getSolutionValue(solution);
			ILPSolver.logger.debug("SAT4J found solution: " + optimum + " - Optimal: " + optimal);
			solution = this.ilpProblem.createILPSolution(variableSolutions, optimal, optimum);
			ILPSolver.logger.info(solution.getSolutionInformation());
			return solution;
		}
		return null;
	}

	/**
	 * Converts the term representation into the integer vector of variable literals
	 * of SAT4J The string identifiers are replaced by integer identifiers
	 *
	 * @return The integer vector representing the literals of the linear expression
	 */
	private IVecInt getLiterals(final ILPLinearExpression linearExpression) {
		IVecInt vec = new VecInt();
		for (int variableId : linearExpression.getVariables()) {
			if (!this.variableIdToSat4JVarId.containsKey(variableId)) {
				// first usage of variable -> reserve internal SAT4J variable and save it
				this.variableIdToSat4JVarId.put(variableId, this.solver.nextFreeVarId(true));
			}
			int sat4JVariableId = this.variableIdToSat4JVarId.get(variableId);
			vec.push(sat4JVariableId);
		}
		return vec;
	}

	/**
	 * Converts the term representation into the integer vector of coefficients of
	 * SAT4J
	 *
	 * @return The BigInteger vector of the coefficients
	 */
	private IVec<BigInteger> getCoefs(final ILPLinearExpression linearExpression) {
		IVec<BigInteger> vec = new Vec<>();
		for (int variableId : linearExpression.getVariables()) {
			double coefficient = linearExpression.getCoefficient(variableId);
			vec.push(BigInteger.valueOf((long) coefficient));
		}
		return vec;
	}

	/**
	 * Registers the constraint for SAT4J
	 *
	 * @throws ContradictionException
	 */
	private void registerConstraint(final ILPConstraint constraint) throws ContradictionException {
		if (constraint.getLinearExpression().getVariables().size() < 1)
			// Do not register empty clauses
			return;
		for (int variableId : constraint.getLinearExpression().getVariables()) {
			double coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			while (Math.abs(coefficient - ((long) coefficient)) >= 0.00000000001) {
				constraint.multiplyBy(10);
				coefficient = constraint.getLinearExpression().getCoefficient(variableId);
			}
		}
		while (Math.abs(constraint.getValue() - ((long) constraint.getValue())) >= 0.00000000001) {
			constraint.multiplyBy(10);
		}
		long value = (long) constraint.getValue();
		IVecInt literals = this.getLiterals(constraint.getLinearExpression());
		IVec<BigInteger> coeffs = this.getCoefs(constraint.getLinearExpression());
		switch (constraint.getComparator()) {
			case ge -> this.solver.addPseudoBoolean(literals, coeffs, true, BigInteger.valueOf(value));
			case le -> this.solver.addPseudoBoolean(literals, coeffs, false, BigInteger.valueOf(value));
			case eq -> {
				this.solver.addPseudoBoolean(literals, coeffs, true, BigInteger.valueOf(value));
				this.solver.addPseudoBoolean(literals, coeffs, false, BigInteger.valueOf(value));
			}
			default -> throw new IllegalArgumentException("Unsupported comparator: " + constraint.getComparator().toString());
		}
	}

	/**
	 * Register the objective for SAT4J
	 */
	private void registerObjective(final ILPObjective objective) {
		for (int variableId : objective.getLinearExpression().getVariables()) {
			double coefficient = objective.getLinearExpression().getCoefficient(variableId);
			while (Math.abs(coefficient - ((long) coefficient)) >= 0.00000000001) {
				objective.getLinearExpression().multiplyBy(10);
				coefficient = objective.getLinearExpression().getCoefficient(variableId);
			}
		}
		ILPLinearExpression expr = objective.getLinearExpression();
		switch (objective.getObjectiveOperation()) {
			case maximize -> {
				ILPLinearExpression invertedExpression = this.ilpProblem.createLinearExpression();
				for (int variableId : objective.getLinearExpression().getVariables()) {
					double coefficient = objective.getLinearExpression().getCoefficient(variableId);
					invertedExpression.addTerm(variableId, -coefficient);
				}
				expr = invertedExpression;
			}
			case minimize -> {
			}
			default -> throw new IllegalArgumentException("Unsupported comparator: " + objective.getObjectiveOperation().toString());
		}
		IVecInt literals = this.getLiterals(expr);
		IVec<BigInteger> coeffs = this.getCoefs(expr);
		this.solver.setObjectiveFunction(new ObjectiveFunction(literals, coeffs));
	}
}