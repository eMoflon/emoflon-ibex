package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;

public class OperationalMultiplicityConflict extends Conflict implements CRS_PreferSource, CRS_PreferTarget {

	protected Set<ITGGMatch> edgeAddingMatches;
	protected Set<ITGGMatch> edgeRemovingMatches;
	protected EObject subject;
	protected EReference reference;
	protected int violationCounter;

	public OperationalMultiplicityConflict(ConflictContainer container, EObject subject, EReference reference, //
			Set<ITGGMatch> edgeAddingMatches, Set<ITGGMatch> edgeRemovingMatches, int violationCounter) {
		super(container);
		this.subject = subject;
		this.reference = reference;
		this.edgeAddingMatches = edgeAddingMatches;
		this.edgeRemovingMatches = edgeRemovingMatches;
		this.violationCounter = violationCounter;
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> result = new HashSet<>();
		result.add(getMatch());
		result.addAll(edgeAddingMatches);
		return result;
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		return Collections.emptySet();
	}

	public EObject getSubject() {
		return subject;
	}

	public EReference getReference() {
		return reference;
	}

	//// CRS ////

	@Override
	public void crs_preferSource() {
		// TODO adrianm: only revoke/restore as many matches as needed to resolve the conflict
		if (violationCounter > 0) {
			for (ITGGMatch match : edgeAddingMatches) {
				if (match.getType() != PatternType.BWD)
					continue;
				revokeMatch(match);
			}
		} else {
			for (ITGGMatch match : edgeRemovingMatches) {
				// TODO adrianm: only restore matches which were modified on the respective domain
				// TODO adrianm: also restore matches underneath?
				restoreMatch(integrate().getClassifiedBrokenMatches().get(match));
			}
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_SOURCE");
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		if (violationCounter > 0) {
			for (ITGGMatch match : edgeAddingMatches) {
				if (match.getType() != PatternType.FWD)
					continue;
				revokeMatch(match);
			}
		} else {
			for (ITGGMatch match : edgeRemovingMatches) {
				restoreMatch(integrate().getClassifiedBrokenMatches().get(match));
			}
		}

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;
	}

}
