package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;

/**
 * 
 * @author leblebici
 * maintains matches that are reported by the pattern matcher
 */
public class MatchContainer {
	private Object2IntOpenHashMap<String> ruleNameToId;
	private Int2ObjectOpenHashMap<String> idToRuleName;

	private Object2IntLinkedOpenHashMap<IMatch> matchToRuleNameID;

	private Random random;

	public MatchContainer(TGG tgg) {
		this.ruleNameToId = new Object2IntOpenHashMap<>(tgg.getRules().size());
		this.idToRuleName = new Int2ObjectOpenHashMap<>(tgg.getRules().size());
		this.matchToRuleNameID = new Object2IntLinkedOpenHashMap<>();
		this.random = new Random();
		assignIDsToRuleNames(tgg);
	}

	private void assignIDsToRuleNames(TGG tgg) {
		int id = 1;
		for (TGGRule rule : tgg.getRules()) {
			ruleNameToId.put(rule.getName(), id);
			idToRuleName.put(id, rule.getName());
			id++;
		}
	}

	public void addMatch(IMatch match) {
		matchToRuleNameID.put(match, ruleNameToId.getInt(match.getRuleName()));
	}

	public boolean removeMatch(IMatch match) {
		if (matchToRuleNameID.containsKey(match)) {
			matchToRuleNameID.removeInt(match);
			return true;
		}
		
		return false;
	}
	
	public void removeMatches(Collection<IMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	public IMatch getNext() {
		return matchToRuleNameID.keySet().iterator().next();
	}

	public Set<IMatch> getMatches() {
		return matchToRuleNameID.keySet();
	}

	public IMatch getNextRandom() {
		Iterator<IMatch> it = matchToRuleNameID.keySet().iterator();
		int randomIndex = random.nextInt(matchToRuleNameID.size());
		for(int count = 0; count < randomIndex; count++) {
			it.next();
		}
		return it.next();
	}
	
	public IMatch getNextRandomKernel(EList<TGGRule> rules) {
		Iterator<IMatch> it = matchToRuleNameID.keySet().iterator();
		
		try {
		while (it.hasNext()) {
			IMatch m = it.next();
			String ruleName = getRuleName(m);
			
			for (TGGRule r : rules) {
				if (r.getName().equals(ruleName) && !(r instanceof TGGComplementRule)) {
					return m;
				}
			}
		}
		} catch (Exception e) {
			System.err.println("No kernel matches found!");
		}
		return null;
	}
	
	public boolean isEmpty() {
		return matchToRuleNameID.isEmpty();
	}

	public String getRuleName(IMatch match) {
		if (isFusedPatternMatch(match.getPatternName()))
				return match.getPatternName();
		return idToRuleName.get(matchToRuleNameID.getInt(match));
	}

}
