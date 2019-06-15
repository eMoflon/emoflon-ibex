package org.emoflon.ibex.tgg.operational.updatepolicy;

import java.util.HashMap;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;

@FunctionalInterface
public interface IUpdatePolicy {	
	public IMatch chooseOneMatch(ImmutableMatchContainer matchContainer);
	
	default IMatch chooseOneMatch(ImmutableMatchContainer applicableMatches, ImmutableMatchContainer inapplicableMatches) {
		return chooseOneMatch(applicableMatches);
	}
	
	default HashMap<String, Integer> getNumberOfApplications(Set<String> complementRules){
		HashMap<String, Integer> upperBounds = new HashMap<String, Integer>();
		complementRules.stream()
				       .forEach( name -> upperBounds.put(name, 0));
		return upperBounds;
	}
	
	default boolean matchShouldBeApplied(IMatch match, String ruleName) {
		return true;
	}
	
	default void notifyMatchHasBeenApplied(IMatch complementMatch, String ruleName) {
		
	}
}
