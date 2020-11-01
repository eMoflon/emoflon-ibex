package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.OpMultiConflictSolution;

public interface CRS_customizedOpMultiResolution extends ConflictResolutionStrategy {

	void crs_customizedOpMultiResolution(OpMultiConflictSolution solution) throws InvalidSolutionException;

	public class InvalidSolutionException extends Exception {

		private static final long serialVersionUID = 7500118292808802920L;
		public final OpMultiConflictSolution solution;

		public InvalidSolutionException(OpMultiConflictSolution solution) {
			super("The provided solution for the operational multiplicity conflict is invalid");
			this.solution = solution;
		}

	}

}
