package org.emoflon.ibex.common.ilp;

import org.emoflon.ibex.common.ilp.gurobiWrapper.GurobiWrapper;
import org.emoflon.ibex.common.ilp.sat4JWrapper.Sat4JWrapper;

import gurobi.GRBException;

public final class ILPFactory {
	
	private ILPFactory() {
		
	}
	
	public static ILPSolver createBinaryILPSolver(SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				try {
					return new GurobiWrapper(true);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			case Sat4J:
				return new Sat4JWrapper();
			default:
				throw new UnsupportedOperationException("Unknown Solver: "+solver.toString());
		}
	}
	
	public static ILPSolver createILPSolver(SupportedILPSolver solver) {
		switch(solver) {
			case Gurobi:
				try {
					return new GurobiWrapper(false);
				} catch (GRBException e) {
					e.printStackTrace();
				}
			case Sat4J:
				throw new UnsupportedOperationException("SAT4J does not support arbitrary ILP");
			default:
				throw new UnsupportedOperationException("Unknown Solver: "+solver.toString());
		}
	}
	
	
	public enum SupportedILPSolver {
		Gurobi,
		Sat4J
	}
}
