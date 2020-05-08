package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public abstract class Conflict {

	private ITGGMatch match;
	
	public Conflict(ITGGMatch match) {
		this.match = match;
	}

	public ITGGMatch getMatch() {
		return match;
	}

}
