package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.OperationalMultiplicityConflict;

public class OpMultiConflictSolution {

	private final OperationalMultiplicityConflict conflict;
	private final Set<ITGGMatch> chosenMatches;

	/**
	 * Creates a container providing a solution for the given {@link OperationalMultiplicityConflict} in
	 * terms of chosen matches.
	 * 
	 * @param conflict      the conflict it refers to
	 * @param chosenMatches matches those elements will be preserved, all other matches will be revoked
	 */
	public OpMultiConflictSolution(OperationalMultiplicityConflict conflict, Set<ITGGMatch> chosenMatches) {
		this.conflict = conflict;
		this.chosenMatches = chosenMatches;
	}

	/**
	 * Validates this solution. <br>
	 * A solution is valid, if all chosen matches are edge adding or edge removing matches of the
	 * appropriate conflict. Moreover, this solution with its chosen matches must guaranty, that after
	 * conflict resolution the multiplicity of the conflicted reference is not violated anymore.
	 * 
	 * @return <code>true</code>, if this solution is valid, otherwise <code>false</code>.
	 */
	public boolean validate() {
		int totalNumOfEdges = 0;

		for (ITGGMatch chosenMatch : chosenMatches) {
			if (conflict.getEdgeAddingMatches2numOfEdges().containsKey(chosenMatch)) {
				totalNumOfEdges += conflict.getEdgeAddingMatches2numOfEdges().get(chosenMatch);
				continue;
			}
			if (conflict.getEdgeRemovingMatches2numOfEdges().containsKey(chosenMatch)) {
				totalNumOfEdges += conflict.getEdgeRemovingMatches2numOfEdges().get(chosenMatch);
				continue;
			}
			return false;
		}
		totalNumOfEdges += conflict.getIntactMatches2numOfEdges().values().stream().reduce(0, Integer::sum);

		if (conflict.integrate().multiplicityCounter().violatesMultiplicity(conflict.getReference(), totalNumOfEdges) != 0)
			return false;

		return true;
	}

	/**
	 * @return the edge adding/removing matches those elements will be preserved. All other matches will
	 *         be revoked.
	 */
	public Set<ITGGMatch> getChosenMatches() {
		return chosenMatches;
	}

}
