package org.emoflon.ibex.tgg.operational.util;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
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

	private TObjectIntMap<String> ruleNameToId;
	private TIntObjectMap<String> idToRuleName;

	private TObjectIntMap<IMatch> matchToRuleNameID;

	private Random random;

	public MatchContainer(TGG tgg) {
		this.ruleNameToId = new TObjectIntHashMap<>(tgg.getRules().size());
		this.idToRuleName = new TIntObjectHashMap<>(tgg.getRules().size());
		this.matchToRuleNameID = new TObjectIntHashMap<>();
		this.random = new Random();
		assignIDsToRuleNames(tgg);
	}

	private void assignIDsToRuleNames(TGG tgg) {
		int id = 0;
		for (TGGRule rule : tgg.getRules()) {
			ruleNameToId.put(rule.getName(), id);
			idToRuleName.put(id, rule.getName());
			id++;
		}
	}

	public void addMatch(String ruleName, IMatch match) {
		matchToRuleNameID.put(match, ruleNameToId.get(ruleName));
	}

	public void removeMatch(IMatch match) {
		if (matchToRuleNameID.containsKey(match))
			matchToRuleNameID.remove(match);
	}

	public IMatch getNext() {
		return (IMatch) matchToRuleNameID.keySet().iterator().next();
	}

	public Set<IMatch> getMatches() {
		return matchToRuleNameID.keySet();
	}

	public IMatch getNextRandom() {
		Iterator<IMatch> it = matchToRuleNameID.keySet().iterator();
		int randomIndex = random.nextInt(matchToRuleNameID.size());
		int count = 0;
		while (count < randomIndex) {
			count++;
			it.next();
		}
		return it.next();
	}

	public boolean isEmpty() {
		return matchToRuleNameID.isEmpty();
	}

	public String getRuleName(IMatch match) {
		return idToRuleName.get(matchToRuleNameID.get(match));
	}

}
