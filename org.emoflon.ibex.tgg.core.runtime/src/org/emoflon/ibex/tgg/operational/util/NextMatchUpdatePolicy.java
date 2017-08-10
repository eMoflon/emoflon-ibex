package org.emoflon.ibex.tgg.operational.util;

public class NextMatchUpdatePolicy implements UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(MatchContainer matchContainer) {
		return matchContainer.getNext();
	}

}
