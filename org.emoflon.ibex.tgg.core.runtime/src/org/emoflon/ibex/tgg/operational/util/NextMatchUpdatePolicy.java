package org.emoflon.ibex.tgg.operational.util;

public class NextMatchUpdatePolicy implements UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}

}
