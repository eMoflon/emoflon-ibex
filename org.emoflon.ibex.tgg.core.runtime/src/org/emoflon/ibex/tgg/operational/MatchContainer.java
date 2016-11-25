package org.emoflon.ibex.tgg.operational;

import java.util.HashMap;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import language.TGG;
import language.TGGRule;

public class MatchContainer {

	private HashMap<String, Integer> ruleNameToId = new HashMap<>();
	private HashMap<Integer, String> idToRuleName = new HashMap<>();

	private TObjectIntMap<IPatternMatch> matchToRuleNameID = new TObjectIntHashMap<>();

	protected MatchContainer(TGG tgg) {
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
		return (IPatternMatch) matchToRuleNameID.keys()[0];
	}

	protected boolean isEmpty() {
		return matchToRuleNameID.isEmpty();
	}

	protected String getRuleName(IPatternMatch match) {
		return idToRuleName.get(matchToRuleNameID.get(match));
	}

}
