package org.emoflon.ibex.tgg.operational.matches;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class MarkingMatchContainer implements IMatchContainer, TimeMeasurable {

	protected final Times times = new Times();

	protected OperationalStrategy opStrat;

	protected Set<EObject> markedElements = cfactory.createObjectSet();
	protected Set<ITGGMatch> matches = cfactory.createLinkedObjectSet();

	public MarkingMatchContainer(OperationalStrategy opStrat) {
		this.opStrat = opStrat;
		TimeRegistry.register(this);
	}

	@Override
	public void addMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY)
			consistencyMatchApplied(match);
		else
			matches.add(match);

		times.addTo("addMatch", Timer.stop());
	}

	@Override
	public boolean removeMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY) {
			boolean removed = removeConsistencyMatch(match);

			times.addTo("removeMatch", Timer.stop());
			return removed;
		} else {
			matches.remove(match);

			times.addTo("removeMatch", Timer.stop());
			return true;
		}
	}

	@Override
	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public ITGGMatch getNext() {
		Timer.start();

		List<ITGGMatch> notApplicable = new LinkedList<>();
		ITGGMatch match = null;

		Iterator<ITGGMatch> it = matches.iterator();
		while (it.hasNext()) {
			ITGGMatch nextMatch = it.next();
			IGreenPatternFactory gFactory = opStrat.getGreenFactories().get(nextMatch.getRuleName());
			if (checkPositiveRAs(nextMatch, gFactory)) {
				if (checkNegativeRAs(nextMatch, gFactory)) {
					match = nextMatch;
					break;
				} else {
					it.remove();
				}
			} else {
				notApplicable.add(nextMatch);
				it.remove();
			}
		}
		matches.addAll(notApplicable);

		times.addTo("getNext", Timer.stop());
		return match;
	}

	@Override
	public Set<ITGGMatch> getMatches() {
		Timer.start();

		Collection<ITGGMatch> neverApplicable = new LinkedList<>();

		Set<ITGGMatch> nextMatches = matches.stream() //
				.filter(m -> checkPositiveRAs(m, opStrat.getGreenFactories().get(m.getRuleName()))) //
				.filter(m -> {
					if (checkNegativeRAs(m, opStrat.getGreenFactories().get(m.getRuleName())))
						return true;
					else {
						neverApplicable.add(m);
						return false;
					}
				}) //
				.collect(Collectors.toSet());

		matches.removeAll(neverApplicable);

		times.addTo("getMatches", Timer.stop());
		return nextMatches;
	}

	@Override
	public boolean isEmpty() {
		return matches.isEmpty() ? true : getNext() == null;
	}

	@Override
	public void removeAllMatches() {
		markedElements.clear();
		matches.clear();
	}

	@Override
	public void matchApplied(ITGGMatch match) {
		Timer.start();

		matches.remove(match);

		times.addTo("matchApplied", Timer.stop());
	}

	private boolean removeConsistencyMatch(ITGGMatch match) {
		IGreenPatternFactory gFactory = opStrat.getGreenFactories().get(match.getRuleName());

		gFactory.getGreenSrcNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));
		gFactory.getGreenTrgNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));

		return true;
	}

	private void consistencyMatchApplied(ITGGMatch match) {
		IGreenPatternFactory gFactory = opStrat.getGreenFactories().get(match.getRuleName());

		gFactory.getGreenSrcNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.add(obj));
		gFactory.getGreenTrgNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.add(obj));
	}

	private boolean checkPositiveRAs(ITGGMatch match, IGreenPatternFactory gFactory) {
		boolean srcRAsExist = gFactory.getBlackSrcNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.allMatch(obj -> markedElements.contains(obj));
		boolean trgRAsExist = gFactory.getBlackTrgNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.allMatch(obj -> markedElements.contains(obj));
		return srcRAsExist && trgRAsExist;
	}

	private boolean checkNegativeRAs(ITGGMatch match, IGreenPatternFactory gFactory) {
		if (match.getType() == PatternType.FWD)
			return gFactory.getGreenSrcNodesInRule().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.noneMatch(obj -> markedElements.contains(obj));
		if (match.getType() == PatternType.BWD)
			return gFactory.getGreenTrgNodesInRule().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.noneMatch(obj -> markedElements.contains(obj));
		return false;
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
