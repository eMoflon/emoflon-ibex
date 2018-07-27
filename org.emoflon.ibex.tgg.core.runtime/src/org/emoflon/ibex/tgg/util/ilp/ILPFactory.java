package org.emoflon.ibex.tgg.util.ilp;

/**
 * This static class offers methods to create ILP solvers and ILP problems
 *
 * @author Robin Oppermann
 */
public final class ILPFactory {

	/**
	 * Private constructor. Should never be used as this class should only be used
	 * in a static context
	 */
	private ILPFactory() {
		throw new UnsupportedOperationException("You cannot instantiate this factory class");
	}

	/**
	 * Creates an ILP Solver for a pseudo-boolean problem. Variable solutions are
	 * restricted to (0,1).
	 *
	 * @param solver
	 *            Specifies the solver to use Currently Gurobi and SAT4J are
	 *            supported.
	 * @return The created solver
	 */
	public static ILPSolver createBinaryILPSolver(final ILPProblem ilpProblem, final SupportedILPSolver solver) {
		switch (solver) {
		case Gurobi:
			return new GurobiWrapper(ilpProblem, true);
		case Sat4J:
			return new Sat4JWrapper(ilpProblem);
		case GLPK:
			return new GLPKWrapper(ilpProblem, true);
		case CBC:
			return new CBCWrapper(ilpProblem, true);
		case MIPCL:
			return new MIPCLWrapper2(ilpProblem, true);
		default:
			throw new UnsupportedOperationException("Unknown Solver: " + solver.toString());
		}
	}

	/**
	 * Creates an ILP Solver.
	 *
	 * @param solver
	 *            Specifies the solver to use. Currently only Gurobi is supported.
	 * @return The created solver.
	 */
	public static ILPSolver createILPSolver(final ILPProblem ilpProblem, final SupportedILPSolver solver) {
		if (ilpProblem instanceof BinaryILPProblem)
			return ILPFactory.createBinaryILPSolver(ilpProblem, solver);

		switch (solver) {
		case Gurobi:
			return new GurobiWrapper(ilpProblem, false);
		case GLPK:
			return new GLPKWrapper(ilpProblem, false);
		case Sat4J:
			throw new UnsupportedOperationException("SAT4J does not support arbitrary ILP");
		case CBC:
			return new CBCWrapper(ilpProblem, false);
		case MIPCL:
			return new MIPCLWrapper2(ilpProblem, false);
		default:
			throw new UnsupportedOperationException("Unknown Solver: " + solver.toString());
		}
	}

	/**
	 * Creates a new empty ILPProblem
	 *
	 * @return the created ILPProblem which can be filled with constraints and
	 *         objecitves afterwards
	 */
	public static ILPProblem createILPProblem() {
		return new ILPProblem();
	}

	/**
	 * Creates a {@link BinaryILPProblem}, in which variables can only be 0 or 1
	 *
	 * @return The created problem
	 */
	public static BinaryILPProblem createBinaryILPProblem() {
		return new BinaryILPProblem();
	}

	/**
	 * This enum contains identifiers of the supported ILP Solvers.
	 *
	 * @author Robin Oppermann
	 *
	 */
	public enum SupportedILPSolver {
		/**
		 * Use the Gurobi solver (must be manually installed)
		 */
		Gurobi,
		/**
		 * Use the SAT4J solver (distributed with eclipse)
		 */
		Sat4J,
		/**
		 * Use the GLPK solver (must be manually installed)
		 */
		GLPK,
		/**
		 * Use the Coin-OR CBC solver using Google-OR (must be manually installed for
		 * non-Windows systems)
		 */
		CBC, MIPCL
	}
}
