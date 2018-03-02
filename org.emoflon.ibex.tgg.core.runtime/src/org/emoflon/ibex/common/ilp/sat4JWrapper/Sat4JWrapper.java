package org.emoflon.ibex.common.ilp.sat4JWrapper;

import org.emoflon.ibex.common.ilp.ILPSolver;
import org.emoflon.ibex.common.ilp.ILPSolver.ILPSolution;
import org.sat4j.pb.IPBSolver;
import org.sat4j.pb.OptToPBSATAdapter;
import org.sat4j.pb.PseudoOptDecorator;
import org.sat4j.pb.SolverFactory;
import org.sat4j.specs.IOptimizationProblem;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class Sat4JWrapper extends ILPSolver {
	
	private final IPBSolver solver;
	
	//http://www.sat4j.org/maven234/org.ow2.sat4j.pb/apidocs/index.html
	//addPseudoBoolean
	public Sat4JWrapper() {
		solver = SolverFactory.newDefaultOptimizer();
	}

	@Override
	public ILPLinearExpression createLinearExpression(ILPTerm... terms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addConstraint(ILPLinearExpression linearExpression, Operation comparator, double value, String name) {
		// TODO Auto-generated method stub
		solver.addPseudoBoolean(arg0, arg1, arg2, arg3);
	}

	@Override
	public void setObjective(ILPLinearExpression linearExpression, Operation operation) {
		// TODO Auto-generated method stub
		solver.setObjectiveFunction(arg0);
	}

	@Override
	public ILPSolution solveILP() {
		// TODO Auto-generated method stub
		OptToPBSATAdapter optimizer = new OptToPBSATAdapter(new PseudoOptDecorator(solver));
		optimizer.setTimeout(600);
		optimizer.setVerbose(true);
		try {
			if(optimizer.isSatisfiable()) {
				int[] model = optimizer.model();
			}
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
