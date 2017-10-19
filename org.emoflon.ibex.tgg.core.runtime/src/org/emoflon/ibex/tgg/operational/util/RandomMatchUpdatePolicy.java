package org.emoflon.ibex.tgg.operational.util;

public class RandomMatchUpdatePolicy extends UpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNextRandom();
	}

}