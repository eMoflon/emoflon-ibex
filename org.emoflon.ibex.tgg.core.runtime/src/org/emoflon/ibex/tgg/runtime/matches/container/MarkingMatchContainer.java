package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;

public class MarkingMatchContainer implements IMatchContainer, TimeMeasurable {

	protected final Times times = new Times();

	protected OperationalStrategy strategy;
	protected RuleHandler ruleHandler;

	protected Set<EObject> markedElements = cfactory.createObjectSet();
	protected Set<ITGGMatch> matches = cfactory.createLinkedObjectSet();

	public MarkingMatchContainer(OperationalStrategy opStrat) {
		this.strategy = opStrat;
		this.ruleHandler = opStrat.getOptions().tgg.ruleHandler();
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
			if (checkPositiveRAs(nextMatch)) {
				if (checkNegativeRAs(nextMatch)) {
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
				.filter(m -> checkPositiveRAs(m)) //
				.filter(m -> {
					if (checkNegativeRAs(m))
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
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());

		operationalRule.getCreateSource().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));
		operationalRule.getCreateTarget().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.remove(obj));

		return true;
	}

	private void consistencyMatchApplied(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());
		
		operationalRule.getCreateSource().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.add(obj));
		operationalRule.getCreateTarget().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.forEach(obj -> markedElements.add(obj));
	}

	private boolean checkPositiveRAs(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());

		boolean srcRAsExist = operationalRule.getContextSource().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.allMatch(obj -> markedElements.contains(obj));
		boolean trgRAsExist = operationalRule.getContextTarget().getNodes().stream() //
				.map(n -> (EObject) match.get(n.getName())) //
				.allMatch(obj -> markedElements.contains(obj));
		return srcRAsExist && trgRAsExist;
	}

	private boolean checkNegativeRAs(ITGGMatch match) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName());

		if (match.getType() == PatternType.FWD)
			return operationalRule. getCreateSource().getNodes().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.noneMatch(obj -> markedElements.contains(obj));
		if (match.getType() == PatternType.BWD)
			return operationalRule.getCreateTarget().getNodes().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.noneMatch(obj -> markedElements.contains(obj));
		return false;
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
