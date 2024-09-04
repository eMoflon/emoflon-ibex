package org.emoflon.ibex.tgg.runtime.matches.container;

import java.util.Collection;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;

public interface IMatchContainer {
	public void addMatch(ITGGMatch match);
	
	public boolean removeMatch(ITGGMatch match);
	
	public void removeMatches(Collection<ITGGMatch> matches);
	
	default ITGGMatch getNext() {
		return getMatches().iterator().next();
	}
	
	public Set<ITGGMatch> getMatches();
	
	default boolean isEmpty() {
		return getMatches().isEmpty();
	}
	
	public void removeAllMatches();
	
	public void matchApplied(ITGGMatch m);
}
