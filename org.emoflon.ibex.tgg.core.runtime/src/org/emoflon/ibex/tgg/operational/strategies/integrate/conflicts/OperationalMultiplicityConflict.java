package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_CustomizedOpMultiResolution;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.OpMultiConflictSolution;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

public class OperationalMultiplicityConflict extends Conflict implements CRS_PreferSource, CRS_PreferTarget, CRS_CustomizedOpMultiResolution {

	protected EObject subject;
	protected EReference reference;
	protected int violationCounter;
	protected Map<ITGGMatch, Integer> edgeAddingMatches2numOfEdges;
	protected Map<ITGGMatch, Integer> edgeRemovingMatches2numOfEdges;
	protected Map<ITGGMatch, Integer> intactMatches2numOfEdges;

	public OperationalMultiplicityConflict(ConflictContainer container, EObject subject, EReference reference, int violationCounter, //
			Map<ITGGMatch, Integer> addedMatches2numOfEdges, Map<ITGGMatch, Integer> removedMatches2numOfEdges) {
		super(container);
		this.subject = subject;
		this.reference = reference;
		this.violationCounter = violationCounter;
		this.edgeAddingMatches2numOfEdges = new HashMap<>();
		this.edgeRemovingMatches2numOfEdges = new HashMap<>(removedMatches2numOfEdges);
		this.intactMatches2numOfEdges = new HashMap<>();
		addedMatches2numOfEdges.forEach((match, numOfEdges) -> {
			if (match.getType() == PatternType.CONSISTENCY)
				intactMatches2numOfEdges.put(match, numOfEdges);
			else
				edgeAddingMatches2numOfEdges.put(match, numOfEdges);
		});
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> result = new HashSet<>();
		result.add(getMatch());
		return result;
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		Set<ITGGMatch> result = new HashSet<>();
		result.addAll(edgeAddingMatches2numOfEdges.keySet());
		result.addAll(edgeRemovingMatches2numOfEdges.keySet());
		return Collections.emptySet();
	}

	/**
	 * @return the runtime node those reference has a multiplicity violation.
	 */
	public EObject getSubject() {
		return subject;
	}

	/**
	 * @return the reference which has a multiplicity violation.
	 */
	public EReference getReference() {
		return reference;
	}

	/**
	 * @return the number of exceeded edges (if positive) or the number of missing edges (if negative).
	 *         If the number is <code>0</code>, there is no multiplicity violation.
	 */
	public int getViolationCounter() {
		return violationCounter;
	}

	public Map<ITGGMatch, Integer> getEdgeAddingMatches2numOfEdges() {
		return edgeAddingMatches2numOfEdges;
	}

	public Map<ITGGMatch, Integer> getEdgeRemovingMatches2numOfEdges() {
		return edgeRemovingMatches2numOfEdges;
	}

	public Map<ITGGMatch, Integer> getIntactMatches2numOfEdges() {
		return intactMatches2numOfEdges;
	}

	//// CRS ////

	@Override
	public void crs_preferSource() {
		// TODO adrianm: only revoke/restore as many matches as needed to resolve the conflict
		if (violationCounter > 0) {
			for (ITGGMatch match : edgeAddingMatches2numOfEdges.keySet()) {
				if (match.getType() == PatternType.BWD)
					revokeMatch(match);
			}
		} else {
			for (ITGGMatch match : edgeRemovingMatches2numOfEdges.keySet()) {
				// TODO adrianm: only restore matches which were modified on the respective domain
				restoreMatchAndAllRequired(match);
			}
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_SOURCE");
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		if (violationCounter > 0) {
			for (ITGGMatch match : edgeAddingMatches2numOfEdges.keySet()) {
				if (match.getType() == PatternType.FWD)
					revokeMatch(match);
			}
		} else {
			for (ITGGMatch match : edgeRemovingMatches2numOfEdges.keySet()) {
				restoreMatchAndAllRequired(match);
			}
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;
	}

	@Override
	public void crs_customizedOpMultiResolution(OpMultiConflictSolution solution) throws InvalidSolutionException {
		if (!solution.validate())
			throw new InvalidSolutionException(solution);

		for (ITGGMatch match : edgeAddingMatches2numOfEdges.keySet()) {
			if (!solution.getChosenMatches().contains(match))
				revokeMatch(match);
		}
		for (ITGGMatch match : edgeRemovingMatches2numOfEdges.keySet()) {
			if (solution.getChosenMatches().contains(match))
				restoreMatchAndAllRequired(match);
		}

		resolved = true;
	}

	private void restoreMatchAndAllRequired(ITGGMatch match) {
		List<PrecedenceNode> rollBackCauses = integrate().getPrecedenceGraph().getNode(match).computeSortedRollBackCauses();
		for (PrecedenceNode rollbackCause : rollBackCauses)
			restoreMatch(rollbackCause.getMatch());
	}

}
