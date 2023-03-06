package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Set;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;

public class DefaultMatchContainer implements IMatchContainer {
	private TGGModel tgg;
	private Set<ITGGMatch> matches;

	private DefaultMatchContainer(DefaultMatchContainer old) {
		this.matches = cfactory.createObjectSet();
		this.tgg = old.tgg;
	}
	
	public DefaultMatchContainer(TGGModel tgg) {
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
