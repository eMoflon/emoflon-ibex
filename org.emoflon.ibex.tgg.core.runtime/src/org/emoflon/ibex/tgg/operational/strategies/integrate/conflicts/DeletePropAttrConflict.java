package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;

public class DeletePropAttrConflict extends DeletePropConflict {
	
	private final Set<AttrConflictingElt> subjects;

	public DeletePropAttrConflict(INTEGRATE integrate, ITGGMatch match, Set<AttrConflictingElt> subjects) {
		super(integrate, match);
		this.subjects = subjects;
	}
	
	public Set<AttrConflictingElt> getSubjects() {
		return subjects;
	}

}
