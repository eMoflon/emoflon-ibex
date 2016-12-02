package org.emoflon.ibex.tgg.operational;

import java.util.Iterator;
import java.util.Random;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;

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

	private TObjectIntMap<IPatternMatch> matchToRuleNameID;

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

	protected void addMatch(String ruleName, IPatternMatch match) {
		matchToRuleNameID.put(match, ruleNameToId.get(ruleName));
	}

	protected void removeMatch(IPatternMatch match) {
		if (matchToRuleNameID.containsKey(match))
			matchToRuleNameID.remove(match);
	}

	protected IPatternMatch getNext() {
		return (IPatternMatch) matchToRuleNameID.keySet().iterator().next();
	}

	protected IPatternMatch getNextRandom() {
		Iterator<IPatternMatch> it = matchToRuleNameID.keySet().iterator();
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

	protected String getRuleName(IPatternMatch match) {
		return idToRuleName.get(matchToRuleNameID.get(match));
	}

}
