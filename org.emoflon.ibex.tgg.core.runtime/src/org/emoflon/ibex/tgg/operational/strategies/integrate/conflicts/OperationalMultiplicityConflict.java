package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;

public class OperationalMultiplicityConflict extends Conflict implements CRS_PreferSource, CRS_PreferTarget {

	protected Set<ITGGMatch> violatingMatches;
	protected EObject subject;
	protected EReference reference;

	public OperationalMultiplicityConflict(ConflictContainer container, EObject subject, EReference reference, Set<ITGGMatch> violatingMatches) {
		super(container);
		this.subject = subject;
		this.reference = reference;
		this.violatingMatches = violatingMatches;
	}

	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> result = new HashSet<>();
		result.add(getMatch());
		result.addAll(violatingMatches);
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
		// TODO implement
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		// TODO implement
		resolved = true;
	}

}
