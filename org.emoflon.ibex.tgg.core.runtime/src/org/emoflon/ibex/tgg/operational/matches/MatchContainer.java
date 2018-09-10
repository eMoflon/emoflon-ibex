package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGG;
import language.TGGComplementRule;
import language.TGGRule;

/**
 * 
 * @author leblebici maintains matches that are reported by the pattern matcher
 */
public class MatchContainer {
	private Map<IMatch, String> matchToRuleName;
	private OperationalStrategy op;
	private Random random;

	public MatchContainer(TGG tgg, OperationalStrategy op) {
		this.matchToRuleName = cfactory.createObjectToObjectLinkedHashMap();
		this.random = new Random();
		this.op = op;
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
		matches.removeAll(matches);
	}

	//FIXME:  Make more efficient by using precedence graph?
	private Set<IMatch> getReadyMatches() {
		return matchToRuleName.keySet()
				.stream()
				.filter(m -> op.matchIsReady(m))
				.collect(Collectors.toSet());
	}

	public IMatch getNext() {
		return getReadyMatches().iterator().next();
	}

	public Set<IMatch> getMatches() {
		return getReadyMatches();
	}

	public IMatch getNextRandom() {
		Set<IMatch> ready = getReadyMatches();
		Iterator<IMatch> it = ready.iterator();
		int randomIndex = random.nextInt(ready.size());
		for (int count = 0; count < randomIndex; count++) {
			it.next();
		}
		return it.next();
	}

	public IMatch getNextRandomKernel(EList<TGGRule> rules) {
		Iterator<IMatch> it = getReadyMatches().iterator();

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

	public void removeAllMatches() {
		matchToRuleName.clear();
	}
}
