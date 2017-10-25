package org.emoflon.ibex.tgg.operational.util;

import java.util.HashMap;
import java.util.Set;

@FunctionalInterface
public interface IUpdatePolicy {	
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer);
	
	default HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules){
		HashMap<String, Integer> upperBounds = new HashMap<String, Integer>();
		complementRules.stream()
				        .forEach( name -> upperBounds.put(name, 0));
		return upperBounds;
	}

}
