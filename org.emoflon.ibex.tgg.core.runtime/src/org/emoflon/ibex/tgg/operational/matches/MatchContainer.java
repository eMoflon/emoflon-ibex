package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;

/**
 * 
 * @author leblebici maintains matches that are reported by the pattern matcher
 */
public class MatchContainer {
	private Map<IMatch, String> matchToRuleName;

	private Random random;

	public MatchContainer(TGG tgg) {
		this.matchToRuleName = cfactory.createObjectToObjectLinkedHashMap();
		this.random = new Random();
	}
	
	private MatchContainer(MatchContainer old) {
		this.matchToRuleName = cfactory.createObjectToObjectLinkedHashMap();
		matchToRuleName.putAll(old.matchToRuleName);
		this.random = new Random();
	}

	public void addMatch(IMatch match) {
		matchToRuleName.put(match, match.getRuleName());
	}

	public boolean removeMatch(IMatch match) {
		if (matchToRuleName.containsKey(match)) {
			matchToRuleName.remove(match);
			return true;
		}

		return false;
	}

	public void removeMatches(Collection<IMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	public IMatch getNext() {
		if(matchToRuleName.isEmpty())
			return null;
		return matchToRuleName.keySet().iterator().next();
	}

	public Set<IMatch> getMatches() {
		return matchToRuleName.keySet();
	}

	public IMatch getNextRandom() {
		if(matchToRuleName.isEmpty())
			return null;
		
		Iterator<IMatch> it = matchToRuleName.keySet().iterator();
		int randomIndex = random.nextInt(matchToRuleName.size());
		for (int count = 0; count < randomIndex; count++) {
			it.next();
		}
		return it.next();
	}

	public IMatch getNextRandomKernel(EList<TGGRule> rules) {
		Iterator<IMatch> it = matchToRuleName.keySet().iterator();

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
		return matchToRuleName.isEmpty();
	}

	public String getRuleName(IMatch match) {
		if (isFusedPatternMatch(match.getPatternName()))
			return match.getPatternName();
		return matchToRuleName.get(match);
	}
	
	public MatchContainer copy() {
		return new MatchContainer(this);
	}
}
