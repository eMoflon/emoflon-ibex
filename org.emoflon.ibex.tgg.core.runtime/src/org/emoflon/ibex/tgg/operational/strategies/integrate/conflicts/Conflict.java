package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public abstract class Conflict implements MatchConflict {

	private ITGGMatch match;
	
	public Conflict(ITGGMatch match) {
		this.match = match;
	}

	@Override
	public ITGGMatch getMatch() {
		return match;
	}

}
