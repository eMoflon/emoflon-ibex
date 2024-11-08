package org.emoflon.ibex.tgg.runtime.matches.container;

import java.util.Collections;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;

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

}
