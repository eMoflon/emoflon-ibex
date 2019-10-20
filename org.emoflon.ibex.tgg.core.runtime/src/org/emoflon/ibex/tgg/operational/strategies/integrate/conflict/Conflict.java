package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public abstract class Conflict {

	private IMatch match;

	public Conflict(IMatch match) {
		this.match = match;
	}

	public IMatch getMatch() {
		return match;
	}

}
