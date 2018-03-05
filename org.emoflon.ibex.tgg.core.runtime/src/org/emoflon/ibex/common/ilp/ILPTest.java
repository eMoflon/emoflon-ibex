package org.emoflon.ibex.common.ilp;

import org.emoflon.ibex.common.ilp.ILPFactory.SupportedILPSolver;
import org.emoflon.ibex.common.ilp.ILPSolver;
import org.emoflon.ibex.common.ilp.ILPSolver.ILPSolution;
import org.emoflon.ibex.common.ilp.ILPSolver.Operation;

public class ILPTest {

	public static void main(String[] args) {
		ILPSolver solver = ILPFactory.createILPSolver(SupportedILPSolver.Sat4J);
		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 1)), 
				Operation.ge, 0, "a");
		solver.addConstraint(solver.createLinearExpression(solver.createTerm("y", 1)), 
				Operation.ge, 0, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 1), solver.createTerm("y", 2)), 
//				Operation.le, 20, "a");
//		solver.addConstraint(solver.createLinearExpression(solver.createTerm("x", 2), solver.createTerm("y", 1)), 
//				Operation.le, 13, "a");
		solver.setObjective(solver.createLinearExpression(solver.createTerm("x", -1), solver.createTerm("y", 1)), Operation.maximize);
		ILPSolution solution = solver.solveILP();
		System.out.println(solution);
	}

}
