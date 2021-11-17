package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;

public class MatchClassifier implements TimeMeasurable {

	private final Times times;

	private final INTEGRATE integrate;

	private Map<ITGGMatch, ClassifiedMatch> classifiedMatches;

	public MatchClassifier(INTEGRATE integrate) {
		this.integrate = integrate;
		classifiedMatches = new HashMap<>();

		this.times = new Times();
	}

	public ClassifiedMatch get(ITGGMatch match) {
		return classifiedMatches.computeIfAbsent(match, m -> new ClassifiedMatch(integrate, match));
	}

	public ClassifiedMatch get(PrecedenceNode node) {
		return classifiedMatches.computeIfAbsent(node.getMatch(), m -> new ClassifiedMatch(integrate, node));
	}

	public Set<ClassifiedMatch> classifyAll(Set<ITGGMatch> matches) {
		Timer.start();

		Set<ClassifiedMatch> newClassifiedMatches = matches.parallelStream() //
				.map(m -> new ClassifiedMatch(integrate, m)) //
				.collect(Collectors.toSet());

		classifiedMatches.putAll(newClassifiedMatches.stream() //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				)));

		times.addTo("classifyAll", Timer.stop());
		return newClassifiedMatches;
	}

	public Set<ClassifiedMatch> classifyAllByNodes(Set<PrecedenceNode> nodes) {
		Timer.start();

		Set<ClassifiedMatch> newClassifiedMatches = nodes.parallelStream() //
				.map(n -> new ClassifiedMatch(integrate, n)) //
				.collect(Collectors.toSet());

		classifiedMatches.putAll(newClassifiedMatches.stream() //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				)));

		times.addTo("classifyAll", Timer.stop());
		return newClassifiedMatches;
	}

	public void clearAndClassifyAll(Set<ITGGMatch> matches) {
		Timer.start();

		this.classifiedMatches = matches.parallelStream() //
				.map(m -> new ClassifiedMatch(integrate, m)) //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				));

		times.addTo("classifyAll", Timer.stop());
	}

	public void clearAndClassifyAllByNodes(Set<PrecedenceNode> nodes) {
		Timer.start();

		this.classifiedMatches = nodes.parallelStream() //
				.map(n -> new ClassifiedMatch(integrate, n)) //
				.collect(Collectors.toMap( //
						cm -> cm.getMatch(), //
						cm -> cm //
				));

		times.addTo("classifyAll", Timer.stop());
	}

	public Set<ClassifiedMatch> classifyAll(Collection<ITGGMatch> matches) {
		return this.classifyAll(new HashSet<>(matches));
	}

	public Set<ClassifiedMatch> classifyAllByNodes(Collection<PrecedenceNode> nodes) {
		return this.classifyAllByNodes(new HashSet<>(nodes));
	}

	public void clearAndClassifyAll(Collection<ITGGMatch> matches) {
		this.clearAndClassifyAll(new HashSet<>(matches));
	}

	public void clearAndClassifyAllByNodes(Collection<PrecedenceNode> nodes) {
		this.clearAndClassifyAllByNodes(new HashSet<>(nodes));
	}

	public Collection<ClassifiedMatch> getAllClassifiedMatches() {
		return classifiedMatches.values();
	}

	public void clear() {
		classifiedMatches.clear();
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
