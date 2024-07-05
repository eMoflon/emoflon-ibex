package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;
import java.util.Set;

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
