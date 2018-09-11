package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;

public class MatchContainer {
	private TGG tgg;
	private Map<IMatch, String> matchToRuleName;
	private Set<IMatch> kernelMatches;

	public MatchContainer(TGG tgg, OperationalStrategy op) {
		this.matchToRuleName = cfactory.createObjectToObjectHashMap();
		this.kernelMatches = cfactory.createObjectSet();
		this.tgg = tgg;
	}

	public void addMatch(IMatch match) {
		matchToRuleName.put(match, match.getRuleName());

		String ruleName = getRuleName(match);
		for (TGGRule r : tgg.getRules()) {
			if (r.getName().equals(ruleName) && !(r instanceof TGGComplementRule)) {
				kernelMatches.add(match);
			}
		}
	}

	public boolean removeMatch(IMatch match) {
		if (matchToRuleName.containsKey(match)) {
			matchToRuleName.remove(match);
			kernelMatches.remove(match);
			return true;
		}

		return false;
	}

	public void removeMatches(Collection<IMatch> matches) {
		matchToRuleName.keySet().removeAll(matches);
		kernelMatches.removeAll(matches);
	}

	public IMatch getNext() {
		return getMatches().iterator().next();
	}

	public Set<IMatch> getMatches() {
		return matchToRuleName.keySet();
	}

	public IMatch getNextKernel() {
		return kernelMatches.iterator().next();
	}

	public boolean isEmpty() {
		return matchToRuleName.isEmpty();
	}

	public String getRuleName(IMatch match) {
		if (isFusedPatternMatch(match.getPatternName()))
			return match.getPatternName();
		return matchToRuleName.get(match);
	}

	public void removeAllMatches() {
		matchToRuleName.clear();
		kernelMatches.clear();
	}
}
