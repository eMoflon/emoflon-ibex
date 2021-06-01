package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;

public class MatchClassifier {

	private final INTEGRATE integrate;

	private Map<ITGGMatch, ClassifiedMatch> classifiedMatches;

	public MatchClassifier(INTEGRATE integrate) {
		this.integrate = integrate;
		classifiedMatches = new HashMap<>();
	}

	public ClassifiedMatch get(ITGGMatch match) {
		return classifiedMatches.computeIfAbsent(match, m -> new ClassifiedMatch(integrate, match, false));
	}

	public Set<ClassifiedMatch> getAll(Set<ITGGMatch> matches) {
		Set<ClassifiedMatch> newClassifiedMatches = matches.parallelStream() //
				.filter(m -> !classifiedMatches.containsKey(m)) //
				.map(m -> new ClassifiedMatch(integrate, m, false)) //
				.collect(Collectors.toSet());

		classifiedMatches.putAll(newClassifiedMatches.stream() //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				)));

		return newClassifiedMatches;
	}

	public void clear() {
		classifiedMatches.clear();
	}

}
