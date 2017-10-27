package org.emoflon.ibex.tgg.operational.util;

import java.util.HashMap;
import java.util.Set;

public class NextMatchUpdatePolicy implements IUpdatePolicy {

	@Override
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer) {
		return matchContainer.getNext();
	}

	@Override
	public HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules) {
		// TODO Auto-generated method stub
		return null;
	}

}
