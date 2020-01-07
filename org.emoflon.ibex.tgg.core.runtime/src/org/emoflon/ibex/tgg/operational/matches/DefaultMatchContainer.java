package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import language.TGG;

public class DefaultMatchContainer implements IMatchContainer {
	private TGG tgg;
	private Set<ITGGMatch> matches;

	private DefaultMatchContainer(DefaultMatchContainer old) {
		this.matches = cfactory.createObjectSet();
		this.tgg = old.tgg;
	}
	
	public DefaultMatchContainer(TGG tgg) {
		this.matches = cfactory.createObjectSet();
		this.tgg = tgg;
	}

	public void addMatch(ITGGMatch match) {
		matches.add(match);
	}

	public boolean removeMatch(ITGGMatch match) {
		return matches.remove(match);
	}

	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.removeAll(matches);
	}

	public Set<ITGGMatch> getMatches() {
		return matches;
	}

	public void removeAllMatches() {
		matches.clear();
	}

	public void matchApplied(ITGGMatch m) {
		// Default: do nothing
	}

	public IMatchContainer copy() {
		return new DefaultMatchContainer(this);
	}
}
