package org.emoflon.ibex.tgg.operational.updatepolicy;

import java.util.HashMap;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

@FunctionalInterface
public interface IUpdatePolicy {	
	public ITGGMatch chooseOneMatch(ImmutableMatchContainer matchContainer);
	
	default HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules){
		HashMap<String, Integer> upperBounds = new HashMap<String, Integer>();
		complementRules.stream()
				       .forEach( name -> upperBounds.put(name, 0));
		return upperBounds;
	}
	
	default boolean matchShouldBeApplied(ITGGMatch match, String ruleName) {
		return true;
	}
	
	default void notifyMatchHasBeenApplied(ITGGMatch complementMatch, String ruleName) {
		
	}
}
