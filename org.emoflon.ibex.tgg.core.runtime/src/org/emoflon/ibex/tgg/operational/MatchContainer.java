package org.emoflon.ibex.tgg.operational;

import java.util.Iterator;
import java.util.Random;

import org.emoflon.ibex.tgg.operational.util.IMatch;

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

	protected MatchContainer(TGG tgg) {
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

	protected void addMatch(String ruleName, IMatch match) {
		matchToRuleNameID.put(match, ruleNameToId.get(ruleName));
	}

	protected void removeMatch(IMatch match) {
		if (matchToRuleNameID.containsKey(match))
			matchToRuleNameID.remove(match);
	}

	protected IMatch getNext() {
		return (IMatch) matchToRuleNameID.keySet().iterator().next();
	}

	protected IMatch getNextRandom() {
		Iterator<IMatch> it = matchToRuleNameID.keySet().iterator();
		int randomIndex = random.nextInt(matchToRuleNameID.size());
		int count = 0;
		while (count < randomIndex) {
			count++;
			it.next();
		}
		return it.next();
	}

	protected boolean isEmpty() {
		return matchToRuleNameID.isEmpty();
	}

	protected String getRuleName(IMatch match) {
		return idToRuleName.get(matchToRuleNameID.get(match));
	}

}
