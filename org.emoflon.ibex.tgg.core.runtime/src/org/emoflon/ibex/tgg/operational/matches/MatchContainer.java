package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import language.TGG;

public class MatchContainer implements IMatchContainer {
	private TGG tgg;
	private Map<ITGGMatch, String> matchToRuleName;

	private MatchContainer(MatchContainer old) {
		this.matchToRuleName = cfactory.createObjectToObjectLinkedHashMap();
		this.matchToRuleName.putAll(old.matchToRuleName);
		this.tgg = old.tgg;
	}
	
	public MatchContainer(TGG tgg) {
		this.matchToRuleName = cfactory.createObjectToObjectHashMap();
		this.tgg = tgg;
	}

	public void addMatch(ITGGMatch match) {
		matchToRuleName.put(match, match.getRuleName());
	}

	public boolean removeMatch(ITGGMatch match) {
		if (matchToRuleName.containsKey(match)) {
			matchToRuleName.remove(match);
			return true;
		}

		return false;
	}

	public void removeMatches(Collection<ITGGMatch> matches) {
		matchToRuleName.keySet().removeAll(matches);
	}

	public Set<ITGGMatch> getMatches() {
		return matchToRuleName.keySet();
	}

	public String getRuleName(ITGGMatch match) {
		return matchToRuleName.get(match);
	}

	public void removeAllMatches() {
		matchToRuleName.clear();
	}

	public void matchApplied(ITGGMatch m) {
		// Default: do nothing
	}

	public IMatchContainer copy() {
		return new MatchContainer(this);
	}
}
