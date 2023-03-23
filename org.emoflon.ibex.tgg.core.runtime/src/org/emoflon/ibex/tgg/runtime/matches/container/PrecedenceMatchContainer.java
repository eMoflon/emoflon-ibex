package org.emoflon.ibex.tgg.runtime.matches.container;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;

public class PrecedenceMatchContainer implements IMatchContainer, TimeMeasurable {

	protected final Times times = new Times();

	protected PropagatingOperationalStrategy strategy;
	protected RuleHandler ruleHandler;
	
	protected Collection<Object> translated = cfactory.createObjectSet();
	protected Collection<ITGGMatch> pending = cfactory.createObjectSet();

	/**
	 * We use this set to store all elements of broken rule applications. We need this to prevent
	 * translation of these elements by other rules until repair has taken place.
	 */
	protected Collection<Object> pendingElts = cfactory.createObjectSet();

	protected Map<ITGGMatch, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<ITGGMatch>> requiredBy = cfactory.createObjectToObjectHashMap();
	protected Map<ITGGMatch, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	protected Map<Object, Collection<ITGGMatch>> translatedBy = cfactory.createObjectToObjectHashMap();

	protected Map<TGGRuleApplication, Collection<Object>> raToTranslated = cfactory.createObjectToObjectHashMap();
	protected Map<TGGRuleApplication, ITGGMatch> raToMatch = cfactory.createObjectToObjectHashMap();

	protected Set<ITGGMatch> readySet = cfactory.createObjectSet();

	public PrecedenceMatchContainer(PropagatingOperationalStrategy strategy) {
		this.strategy = strategy;
		ruleHandler = strategy.getOptions().tgg.ruleHandler();
		TimeRegistry.register(this);
	}

	@Override
	public void addMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY) {
			consistencyMatchApplied(match);
		} else if (match.getType() == PatternType.FWD || match.getType() == PatternType.BWD) {
			pending.add(match);
		}

		times.addTo("addMatch", Timer.stop());
	}

	private void handleMatch(ITGGMatch m) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(m.getRuleName());
		
		if (anElementHasAlreadyBeenTranslated(m, operationalRule))
			return;

		// Register nodes
		for (IBeXNode contextNode : operationalRule.getAlreadyMarked().getNodes()) {
			Object contextObj = m.get(contextNode.getName());

			if (!translated.contains(contextObj)) {
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
				requiredBy.computeIfAbsent(contextObj, (x) -> cfactory.createObjectSet());

				requires.get(m).add(contextObj);
				requiredBy.get(contextObj).add(m);
			}
		}
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());

			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdObj, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdObj);
			translatedBy.get(createdObj).add(m);
		}

		// Register edges
		for (IBeXEdge contextEdge : operationalRule.getAlreadyMarked().getEdges()) {
			Object contextRuntimeEdge = getRuntimeEdge(m, contextEdge);

			if (!translated.contains(contextRuntimeEdge)) {
				requiredBy.computeIfAbsent(contextRuntimeEdge, (x) -> cfactory.createObjectSet());
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());

				requiredBy.get(contextRuntimeEdge).add(m);
				requires.get(m).add(contextRuntimeEdge);
			}
		}
		for (IBeXEdge createdEdge : operationalRule.getToBeMarked().getEdges()) {
			Object createdRuntimeEdge = getRuntimeEdge(m, createdEdge);
			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdRuntimeEdge, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdRuntimeEdge);
			translatedBy.get(createdRuntimeEdge).add(m);
		}

		Collection<Object> dependentElts = requires.get(m);
		if (dependentElts == null || dependentElts.isEmpty())
			readySet.add(m);
	}

	private boolean anElementHasAlreadyBeenTranslated(ITGGMatch m, TGGOperationalRule operationalRule) {
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());
			if (translated.contains(createdObj))
				return true;
		}

		for (IBeXEdge createdEdge : operationalRule.getToBeMarked().getEdges()) {
			Object createdRuntimeEdge = getRuntimeEdge(m, createdEdge);
			if (translated.contains(createdRuntimeEdge))
				return true;
		}

		return false;
	}

	private boolean noElementIsPending(ITGGMatch m) {
		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(m.getRuleName());
		
		for (IBeXNode createdNode : operationalRule.getToBeMarked().getNodes()) {
			Object createdObj = m.get(createdNode.getName());
			if (pendingElts.contains(createdObj))
				return false;
		}

		for (IBeXNode contextNode : operationalRule.getAlreadyMarked().getNodes()) {
			Object contextObj = m.get(contextNode.getName());
			if (pendingElts.contains(contextObj))
				return false;
		}

		return true;
	}

	@Override
	public void matchApplied(ITGGMatch m) {
		Timer.start();

		if (!translates.containsKey(m)) {
			times.addTo("matchApplied", Timer.stop());
			return;
		}

		Collection<Object> translatedElts = translates.get(m);
		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<ITGGMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (ITGGMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
				}
			}

			// Kill siblings
			if (translatedBy.containsKey(translatedElement)) {
				Collection<ITGGMatch> siblings = cfactory.createObjectSet();
				siblings.addAll(translatedBy.get(translatedElement));
				siblings.remove(m);

				readySet.removeAll(siblings);
				removeMatches(siblings);
			}
		}

		translated.addAll(translatedElts);

		times.addTo("matchApplied", Timer.stop());
	}

	private void consistencyMatchApplied(ITGGMatch m) {
		TGGRuleApplication ra = m.getRuleApplicationNode();
		if (raToTranslated.containsKey(ra))
			return;

		TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(m.getRuleName());

		// Add translated elements
		Collection<Object> translatedElts = cfactory.createObjectSet();

		
		
		operationalRule.getCreateSource().getNodes().forEach(n -> translatedElts.add(m.get(n.getName())));
		operationalRule.getCreateTarget().getNodes().forEach(n -> translatedElts.add(m.get(n.getName())));
		operationalRule.getCreateSource().getEdges().forEach(e -> translatedElts.add(getRuntimeEdge(m, e)));
		operationalRule.getCreateTarget().getEdges().forEach(e -> translatedElts.add(getRuntimeEdge(m, e)));

		raToTranslated.put(ra, translatedElts);
		raToMatch.put(ra, m);

		translated.addAll(translatedElts);
		pendingElts.removeAll(translatedElts);

		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<ITGGMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (ITGGMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
				}
			}
		}
	}

	public void clearPendingElements() {
		pendingElts.clear();
	}

	@Override
	public Set<ITGGMatch> getMatches() {
		Timer.start();

		Collection<ITGGMatch> notPendingMatches = pending.parallelStream() //
				.filter(this::noElementIsPending) //
				.collect(Collectors.toList());
		notPendingMatches.forEach(this::handleMatch);
		if (notPendingMatches.size() == pending.size())
			pending.clear();
		else
			pending.removeAll(notPendingMatches);
		Set<ITGGMatch> validate = validate(readySet);

		times.addTo("getMatches", Timer.stop());
		return validate;
	}

	private Set<ITGGMatch> validate(Set<ITGGMatch> readySet) {
		if (pendingElts.isEmpty())
			return readySet;

		Set<ITGGMatch> filteredReadySet = cfactory.createObjectSet();
		for (ITGGMatch m : readySet) {
			if (m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p))))
				continue;
			filteredReadySet.add(m);
		}
		return filteredReadySet;
	}

	@Override
	public boolean removeMatch(ITGGMatch match) {
		Timer.start();

		if (match.getType() == PatternType.CONSISTENCY) {
			boolean removed = removeConsistencyMatch(match);

			times.addTo("removeMatch", Timer.stop());
			return removed;
		}

		if (pending.contains(match)) {
			pending.remove(match);

			times.addTo("removeMatch", Timer.stop());
			return true;
		}

		Collection<Object> dependentObjects = requires.remove(match);
		if (dependentObjects != null) {
			for (Object o : dependentObjects) {
				if (requiredBy.containsKey(o))
					requiredBy.get(o).remove(match);
			}
		}

		Collection<Object> translatedElements = translates.remove(match);
		if (translatedElements != null) {
			for (Object o : translatedElements) {
				if (translatedBy.containsKey(o)) {
					translatedBy.get(o).remove(match);
					if (translatedBy.get(o).isEmpty())
						translatedBy.remove(o);
				}
			}
		}

		readySet.remove(match);

		times.addTo("removeMatch", Timer.stop());
		return true;
	}

	public boolean removeConsistencyMatch(ITGGMatch m) {
		// Transfer elements to the pending collection
		TGGRuleApplication ra = m.getRuleApplicationNode();
		if (!raToTranslated.containsKey(ra))
			return true;

		translated.removeAll(raToTranslated.get(ra));
		pendingElts.addAll(raToTranslated.remove(ra));
		raToMatch.remove(ra);

		return true;
	}

	@Override
	public void removeMatches(Collection<ITGGMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public void removeAllMatches() {
		translatedBy.clear();
		requiredBy.clear();
		requires.clear();
		translates.clear();

		readySet.clear();
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
