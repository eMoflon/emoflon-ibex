package org.emoflon.ibex.tgg.util.ilp;

import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;

/**
 * This class is used to abstract from the usage of a concrete ILP solver. It provides common methods to define ILP problems, 
 * start finding the solution and access to the found solution.
 * 
 * @author Robin Oppermann
 */
public abstract class ILPSolver {
	
	protected final ILPProblem ilpProblem;
	
	/**
	 * 
	 */
	protected ILPSolver(ILPProblem ilpProblem) {
		this.ilpProblem = ilpProblem;
	}
	
	/**
	 * Starts solving the ILP
	 * @return The solution of the ILP (if found)
	 * @throws Exception 
	 */
	public abstract ILPSolution solveILP() throws Exception;
	
	/**
	 * Solves the ILPProblem using the given solver
	 * @param ilpProblem The ILPProblem to solve
	 * @param solver The solver to use
	 * @return a valid solution for the ILP or null if no solution could be found
	 * @throws Exception
	 */
	public static ILPSolution solveILPProblem(ILPProblem ilpProblem, SupportedILPSolver solver) throws Exception {
		return ILPFactory.createILPSolver(ilpProblem, solver).solveILP();
	}
	
	/**
	 * Solves the ILPProblem using the given solver. The ILPProblem is restricted to binary variables.
	 * @param ilpProblem The ILPProblem to solve
	 * @param solver The solver to use
	 * @return a valid solution for the ILP or null if no solution could be found
	 * @throws Exception
	 */
	public static ILPSolution solveBinaryILPProblem(ILPProblem ilpProblem, SupportedILPSolver solver) throws Exception {
		if(solver == SupportedILPSolver.GLPK) { //TODO remove this when GLPK works correctly
			//double check with Gurobi
			ILPSolution glpkSolution = ILPFactory.createBinaryILPSolver(ilpProblem, solver).solveILP();
			double calculatedSolutionValue = ilpProblem.getSolutionValue(glpkSolution);
			if(!ilpProblem.checkValidity(glpkSolution)) {
				throw new RuntimeException("Invalid solution?");
			}
			if(calculatedSolutionValue != glpkSolution.getSolutionValue()) {
				throw new RuntimeException("Invalid solution?");
			}
			ILPSolution gurobiSolution = ILPFactory.createBinaryILPSolver(ilpProblem, SupportedILPSolver.Gurobi).solveILP();
			if(glpkSolution.getSolutionValue() < gurobiSolution.getSolutionValue()) {
				throw new RuntimeException("Different solution?");
			}
			
		}
		return ILPFactory.createBinaryILPSolver(ilpProblem, solver).solveILP();
	}
}