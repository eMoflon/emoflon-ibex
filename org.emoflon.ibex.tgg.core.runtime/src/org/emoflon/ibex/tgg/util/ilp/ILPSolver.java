package org.emoflon.ibex.tgg.util.ilp;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPProblem.ILPSolution;

/**
 * This class is used to abstract from the usage of a concrete ILP solver. It
 * provides common methods to define ILP problems, start finding the solution
 * and access to the found solution.
 * 
 * @author Robin Oppermann
 */
public abstract class ILPSolver {

	/**
	 * The problem to solve
	 */
	protected final ILPProblem ilpProblem;

	/**
	 * Logger for all ILPSolvers
	 */
	protected final static Logger logger = Logger.getLogger(ILPSolver.class);

	/**
	 * Creates an ILPSolver
	 * 
	 * @param ilpProblem
	 *            The {@link ILPProblem} to solve
	 */
	protected ILPSolver(ILPProblem ilpProblem) {
		this.ilpProblem = ilpProblem;
	}

	/**
	 * Starts solving the ILP
	 * 
	 * @return The solution of the ILP (if found)
	 * @throws Exception
	 */
	public abstract ILPSolution solveILP() throws Exception;

	/**
	 * Solves the ILPProblem using the given solver
	 * 
	 * @param ilpProblem
	 *            The ILPProblem to solve
	 * @param solver
	 *            The solver to use
	 * @return a valid solution for the ILP or null if no solution could be found
	 * @throws Exception
	 */
	public static ILPSolution solveILPProblem(ILPProblem ilpProblem, SupportedILPSolver solver) throws Exception {
		return ILPFactory.createILPSolver(ilpProblem, solver).solveILP();
	}

	/**
	 * Solves the ILPProblem using the given solver. The ILPProblem is restricted to
	 * binary variables.
	 * 
	 * @param ilpProblem
	 *            The ILPProblem to solve
	 * @param solver
	 *            The solver to use
	 * @return a valid solution for the ILP or null if no solution could be found
	 * @throws Exception
	 */
	public static ILPSolution solveBinaryILPProblem(ILPProblem ilpProblem, SupportedILPSolver solver) throws Exception {
		return ILPFactory.createBinaryILPSolver(ilpProblem, solver).solveILP();
	}
}