package org.emoflon.ibex.common.ilp;

import org.emoflon.ibex.common.ilp.gurobiWrapper.GurobiWrapper;

public final class ILPFactory {
	
	private ILPFactory() {
		
	}
	
	public static ILPSolver createILPSolver(SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				return new GurobiWrapper();
			default:
				throw new UnsupportedOperationException("Unknown Solver");
		}
	}
	
	
	public enum SupportedILPSolver {
		Gurobi,
		Sat4J
	}
}
