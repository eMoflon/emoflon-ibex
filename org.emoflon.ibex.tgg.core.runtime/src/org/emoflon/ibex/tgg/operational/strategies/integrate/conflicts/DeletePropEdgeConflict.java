package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.EdgeConflictingElt;

public class DeletePropEdgeConflict extends DeletePropConflict {
	
	private final Set<EdgeConflictingElt> subjects;

	public DeletePropEdgeConflict(INTEGRATE integrate, ITGGMatch match, Set<EdgeConflictingElt> subjects) {
		super(integrate, match);
		this.subjects = subjects;
	}
	
	public Set<EdgeConflictingElt> getSubjects() {
		return subjects;
	}

}
