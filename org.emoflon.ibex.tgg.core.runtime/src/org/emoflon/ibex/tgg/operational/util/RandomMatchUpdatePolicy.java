package org.emoflon.ibex.tgg.operational.util;

public class RandomMatchUpdatePolicy implements UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNextRandom();
	}

}