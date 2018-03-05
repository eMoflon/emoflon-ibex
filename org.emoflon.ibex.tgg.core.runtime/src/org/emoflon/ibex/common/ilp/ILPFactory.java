package org.emoflon.ibex.common.ilp;

import org.emoflon.ibex.common.ilp.gurobiWrapper.GurobiWrapper;
import org.emoflon.ibex.common.ilp.sat4JWrapper.Sat4JWrapper;

public final class ILPFactory {
	
	private ILPFactory() {
		
	}
	
	public static ILPSolver createILPSolver(SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				return new GurobiWrapper();
			case Sat4J:
				return new Sat4JWrapper();
			default:
				throw new UnsupportedOperationException("Unknown Solver");
		}
	}
	
	
	public enum SupportedILPSolver {
		Gurobi,
		Sat4J
	}
}
