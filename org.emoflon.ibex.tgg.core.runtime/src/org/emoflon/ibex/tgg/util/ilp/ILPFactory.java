package org.emoflon.ibex.tgg.util.ilp;

/**
 * This static class offers methods to create ILP solvers and ILP problems
 * @author Robin Oppermann
 */
public final class ILPFactory {
	
	/**
	 * Private constructor. Should never be used as this class should only be used in a static context
	 */
	private ILPFactory() {
		throw new UnsupportedOperationException("You cannot instantiate this factory class");
	}
	
	/**
	 * Creates an ILP Solver for a pseudo-boolean problem. Variable solutions are restricted to (0,1).
	 * 
	 * @param 	solver Specifies the solver to use Currently Gurobi and SAT4J are supported.
	 * @return The created solver
	 */
	public static ILPSolver createBinaryILPSolver(ILPProblem ilpProblem, SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				return new GurobiWrapper(ilpProblem, true);
			case Sat4J:
				return new Sat4JWrapper(ilpProblem);
			case GLPK:
				return new GLPKWrapper(ilpProblem, true);
			case CBC:
				return new CBCWrapper(ilpProblem, true);
			default:
				throw new UnsupportedOperationException("Unknown Solver: "+solver.toString());
		}
	}
	
	/**
	 * Creates an ILP Solver.
	 * @param	solver Specifies the solver to use. Currently only Gurobi is supported.
	 * @return	The created solver.
	 */
	public static ILPSolver createILPSolver(ILPProblem ilpProblem, SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				return new GurobiWrapper(ilpProblem, false);
			case GLPK:
				return new GLPKWrapper(ilpProblem, false);
			case Sat4J:
				throw new UnsupportedOperationException("SAT4J does not support arbitrary ILP");
			case CBC:
				return new CBCWrapper(ilpProblem, false);
			default:
				throw new UnsupportedOperationException("Unknown Solver: "+solver.toString());
		}
	}
	
	/**
	 * Creates a new empty ILPProblem
	 * @return the created ILPProblem which can be filled with constraints and objecitves afterwards
	 */
	public static ILPProblem createILPProblem() {
		return new ILPProblem();
	}
	
	/**
	 * This enum contains identifiers of the supported ILP Solvers. 
	 * @author Robin Oppermann
	 *
	 */
	public enum SupportedILPSolver {
		Gurobi,
		Sat4J,
		GLPK,
		CBC
	}
}
