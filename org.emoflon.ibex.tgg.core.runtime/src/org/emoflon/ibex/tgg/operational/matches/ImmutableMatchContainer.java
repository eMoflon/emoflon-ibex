package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collections;
import java.util.Set;

public class ImmutableMatchContainer {
	
	private IMatchContainer matchContainer;
	
	public ImmutableMatchContainer(IMatchContainer matchContainer) {
		this.matchContainer = matchContainer;
	}
	
	public ITGGMatch getNext() {
		return matchContainer.getNext();
	}
	
	public Set<ITGGMatch> getMatches() {
		return Collections.unmodifiableSet(matchContainer.getMatches());
	}
	
	public boolean isEmpty() {
		return matchContainer.isEmpty();
	}

	public String getRuleName(ITGGMatch match) {
		return matchContainer.getRuleName(match);
	}

}
