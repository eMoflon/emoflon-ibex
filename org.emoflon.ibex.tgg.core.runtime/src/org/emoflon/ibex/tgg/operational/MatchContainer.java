package org.emoflon.ibex.tgg.operational;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import language.TGG;
import language.TGGRule;

/**
 * 
 * @author leblebici
 * maintains matches that are reported by the pattern matcher
 */
public class MatchContainer {
	


	private THashMap<IPatternMatch, String> matchToRuleName;

	private Random random;

	protected MatchContainer() {
		this.matchToRuleName = new THashMap<>();
		this.random = new Random();
	}

	protected void addMatch(String ruleName, IPatternMatch match) {
		matchToRuleName.put(match, ruleName);
	}

	protected void removeMatch(IPatternMatch match) {
		if (matchToRuleName.containsKey(match))
			matchToRuleName.remove(match);
	}

	protected IPatternMatch getNext() {
		return (IPatternMatch) matchToRuleName.keySet().iterator().next();
	}

	protected IPatternMatch getNextRandom() {
		return matchToRuleName.keySet().stream().findAny().get();
	}

	protected boolean isEmpty() {
		return matchToRuleName.isEmpty();
	}

	protected String getRuleName(IPatternMatch match) {
		return matchToRuleName.get(match);
	}

}
