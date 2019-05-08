package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;
import java.util.Set;

public interface IMatchContainer {
	public void addMatch(IMatch match);
	
	public boolean removeMatch(IMatch match);
	
	public void removeMatches(Collection<IMatch> matches);
	
	default IMatch getNext() {
		return getMatches().iterator().next();
	}
	
	public Set<IMatch> getMatches();
	
	default boolean isEmpty() {
		return getMatches().isEmpty();
	}
	
	public String getRuleName(IMatch match);
	
	public void removeAllMatches();
	
	public void matchApplied(IMatch m);
}
