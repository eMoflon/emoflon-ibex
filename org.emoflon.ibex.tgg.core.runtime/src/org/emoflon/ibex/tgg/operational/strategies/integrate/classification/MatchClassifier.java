package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

public class MatchClassifier {

	private final INTEGRATE integrate;

	private Map<ITGGMatch, ClassifiedMatch> classifiedMatches;

	public MatchClassifier(INTEGRATE integrate) {
		this.integrate = integrate;
		classifiedMatches = new HashMap<>();
	}

	public ClassifiedMatch get(ITGGMatch match) {
		return classifiedMatches.computeIfAbsent(match, m -> new ClassifiedMatch(integrate, match));
	}

	public ClassifiedMatch get(PrecedenceNode node) {
		return classifiedMatches.computeIfAbsent(node.getMatch(), m -> new ClassifiedMatch(integrate, node));
	}

	public Set<ClassifiedMatch> classifyAll(Set<ITGGMatch> matches) {
		Set<ClassifiedMatch> newClassifiedMatches = matches.parallelStream() //
				.map(m -> new ClassifiedMatch(integrate, m)) //
				.collect(Collectors.toSet());

		classifiedMatches.putAll(newClassifiedMatches.stream() //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				)));

		return newClassifiedMatches;
	}

	public Set<ClassifiedMatch> classifyAllByNode(Set<PrecedenceNode> nodes) {
		Set<ClassifiedMatch> newClassifiedMatches = nodes.parallelStream() //
				.map(m -> new ClassifiedMatch(integrate, m)) //
				.collect(Collectors.toSet());

		classifiedMatches.putAll(newClassifiedMatches.stream() //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				)));

		return newClassifiedMatches;
	}

	public Collection<ClassifiedMatch> getAllClassifiedMatches() {
		return classifiedMatches.values();
	}

	public void clear() {
		classifiedMatches.clear();
	}

}
