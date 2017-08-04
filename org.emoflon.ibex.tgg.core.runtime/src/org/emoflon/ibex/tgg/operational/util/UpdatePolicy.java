package org.emoflon.ibex.tgg.operational.util;

public interface UpdatePolicy {
	public IMatch chooseOneMatch(MatchContainer matchContainer);
}
