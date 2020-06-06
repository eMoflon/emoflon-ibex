package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public class RelatedConflict implements MatchConflict {
	
	private final Set<Conflict> relatedConflicts;
	private final ITGGMatch match;

	public RelatedConflict(Set<Conflict> relatedConflicts) {
		this.relatedConflicts = relatedConflicts;
		match = relatedConflicts.iterator().hasNext() ? relatedConflicts.iterator().next().getMatch() : null;
	}

	public Set<Conflict> getRelatedConflicts() {
		return relatedConflicts;
	}

	@Override
	public ITGGMatch getMatch() {
		return match;
	}

}
