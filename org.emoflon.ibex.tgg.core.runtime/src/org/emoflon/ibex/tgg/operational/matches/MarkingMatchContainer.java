package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggingMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class MarkingMatchContainer extends LoggingMatchContainer implements IMatchContainer {

	protected OperationalStrategy opStrat;

	protected Set<EObject> markedElements;
	protected LinkedHashSet<ITGGMatch> matches;

	public MarkingMatchContainer(OperationalStrategy opStrat) {
		this.opStrat = opStrat;
		this.markedElements = new HashSet<>();
		this.matches = new LinkedHashSet<>();
	}

	@Override
	public void addMatch(ITGGMatch match) {
		long tic = System.nanoTime();
		matches.add(match);
		addMatchTime += System.nanoTime() - tic;
	}

	@Override
	public boolean removeMatch(ITGGMatch match) {
		long tic = System.nanoTime();
		if (match.getType() == PatternType.CONSISTENCY) {
			boolean removed = removeConsistencyMatch(match);
			removeMatchTime += System.nanoTime() - tic;
			return removed;
		}
		else {
			matches.remove(match);
			removeMatchTime += System.nanoTime() - tic;
			return true;
		}
	}

	@Override
	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public ITGGMatch getNext() {
		long tic = System.nanoTime();
		List<ITGGMatch> notApplicable = new LinkedList<>();
		ITGGMatch match = null;

		Iterator<ITGGMatch> it = matches.iterator();
		while (it.hasNext()) {
			ITGGMatch nextMatch = it.next();
			IGreenPatternFactory gFactory = opStrat.getGreenFactory(nextMatch.getRuleName());
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
		getMatchTime += System.nanoTime() - tic;
		return match;
	}

	@Override
	public Set<ITGGMatch> getMatches() {
		long tic = System.nanoTime();
		Collection<ITGGMatch> neverApplicable = new LinkedList<>();

		Set<ITGGMatch> nextMatches = matches.stream() //
				.filter(m -> checkPositiveRAs(m, opStrat.getGreenFactory(m.getRuleName()))) //
				.filter(m -> {
					if (checkNegativeRAs(m, opStrat.getGreenFactory(m.getRuleName())))
						return true;
					else {
						neverApplicable.add(m);
						return false;
					}
				}) //
				.collect(Collectors.toSet());

		matches.removeAll(neverApplicable);
		getMatchTime += System.nanoTime() - tic;
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
		long tic = System.nanoTime();
		if (match.getType() == PatternType.CONSISTENCY)
			consistencyMatchApplied(match);
		else
			matches.remove(match);
		matchAppliedTime += System.nanoTime() - tic;
	}

	private boolean removeConsistencyMatch(ITGGMatch match) {
		IGreenPatternFactory gFactory = opStrat.getGreenFactory(match.getRuleName());

		gFactory.getGreenSrcNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));
		gFactory.getGreenTrgNodesInRule().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));

		return true;
	}

	private void consistencyMatchApplied(ITGGMatch match) {
		IGreenPatternFactory gFactory = opStrat.getGreenFactory(match.getRuleName());

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

}
