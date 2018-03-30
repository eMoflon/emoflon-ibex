package org.emoflon.ibex.tgg.util.ilp;

import org.emoflon.ibex.tgg.util.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver.ILPSolution;
import org.emoflon.ibex.tgg.util.ilp.ILPSolver.Operation;

public class ShortTest {

	public ShortTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ILPSolver solver = ILPFactory.createBinaryILPSolver(SupportedILPSolver.GLPK);
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 0.01)), 
//				Operation.ge, 0, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("y", 1)), 
//				Operation.ge, 0, "a");
		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 1), solver.createTerm("y", 2)), 
				Operation.le, 20, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 2), solver.createTerm("y", 1)), 
//				Operation.ge, 13, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 2), solver.createTerm("y", 1)), 
//				Operation.ge, 3, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 1), solver.createTerm("y", 1)), 
//				Operation.le, 3, "a");
		solver.setObjective(solver.createLinearExpression(solver.createTerm("x", 1), solver.createTerm("y", 1)), Operation.maximize);
		ILPSolution solution = solver.solveILP();
		System.out.println(solution);
	}

}
