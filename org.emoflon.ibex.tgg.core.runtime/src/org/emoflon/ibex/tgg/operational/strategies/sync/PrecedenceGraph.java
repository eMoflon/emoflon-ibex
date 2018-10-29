package org.emoflon.ibex.tgg.operational.strategies.sync;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;
import static org.emoflon.ibex.tgg.util.MAUtil.isFusedPatternMatch;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public class PrecedenceGraph implements IMatchContainer {
	private SYNC strategy;

	private Collection<Object> translated;
	private Collection<Object> pendingElts;
	private Collection<IMatch> pending = cfactory.createObjectSet();

	private Map<IMatch, Collection<Object>> requires = cfactory.createObjectToObjectHashMap();
	private Map<Object, Collection<IMatch>> requiredBy = cfactory.createObjectToObjectHashMap();
	private Map<IMatch, Collection<Object>> translates = cfactory.createObjectToObjectHashMap();
	private Map<Object, Collection<IMatch>> translatedBy = cfactory.createObjectToObjectHashMap();

	private Set<IMatch> readySet = cfactory.createObjectSet();

	public PrecedenceGraph(SYNC strategy, Collection<Object> translated, Collection<Object> pendingElts) {
		this.strategy = strategy;
		this.translated = translated;
		this.pendingElts = pendingElts;
	}

	@Override
	public void addMatch(IMatch match) {
		pending.add(match);
	}

	private void handleMatch(IMatch m) {
		IGreenPatternFactory gFactory = strategy.getGreenFactory(m.getRuleName());
		IGreenPattern gPattern = gFactory.create(m.getPatternName());

		if (anElementHasAlreadyBeenTranslated(m, gPattern))
			return;
		
		if(anElementIsPending(m, gPattern))
			return;

		// Register nodes
		for (TGGRuleNode contextNode : gPattern.getMarkedContextNodes()) {
			Object contextObj = m.get(contextNode.getName());

			if (!translated.contains(contextObj)) {
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
				requiredBy.computeIfAbsent(contextObj, (x) -> cfactory.createObjectSet());

				requires.get(m).add(contextObj);
				requiredBy.get(contextObj).add(m);
			}
		}
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());

			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdObj, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdObj);
			translatedBy.get(createdObj).add(m);
		}

		// Register edges
		for (TGGRuleEdge contextEdge : gPattern.getMarkedContextEdges()) {
			Object contextRuntimeEdge = strategy.getRuntimeEdge(m, contextEdge);

			if (!translated.contains(contextRuntimeEdge)) {
				requiredBy.computeIfAbsent(contextRuntimeEdge, (x) -> cfactory.createObjectSet());
				requires.computeIfAbsent(m, (x) -> cfactory.createObjectSet());

				requiredBy.get(contextRuntimeEdge).add(m);
				requires.get(m).add(contextRuntimeEdge);
			}
		}
		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = strategy.getRuntimeEdge(m, createdEdge);
			translates.computeIfAbsent(m, (x) -> cfactory.createObjectSet());
			translatedBy.computeIfAbsent(createdRuntimeEdge, (x) -> cfactory.createObjectSet());

			translates.get(m).add(createdRuntimeEdge);
			translatedBy.get(createdRuntimeEdge).add(m);
		}

		Collection<Object> dependentElts = requires.get(m);
		if (dependentElts == null || dependentElts.isEmpty())
			readySet.add(m);
	}

	private boolean anElementHasAlreadyBeenTranslated(IMatch m, IGreenPattern gPattern) {
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());
			if (translated.contains(createdObj))
				return true;
		}

		for (TGGRuleEdge createdEdge : gPattern.getEdgesMarkedByPattern()) {
			Object createdRuntimeEdge = strategy.getRuntimeEdge(m, createdEdge);
			if (translated.contains(createdRuntimeEdge))
				return true;
		}

		return false;
	}
	
	private boolean anElementIsPending(IMatch m, IGreenPattern gPattern) {
		for (TGGRuleNode createdNode : gPattern.getNodesMarkedByPattern()) {
			Object createdObj = m.get(createdNode.getName());
			if (pendingElts.contains(createdObj))
				return true;
		}

		return false;
	}

	@Override
	public void matchApplied(IMatch m) {
		if (!translates.containsKey(m))
			return;

		Collection<Object> translatedElts = translates.get(m);
		for (Object translatedElement : translatedElts) {
			// Handle children: this parent has now been translated and can be removed
			Collection<IMatch> dependentMatches = requiredBy.remove(translatedElement);
			if (dependentMatches != null) {
				for (IMatch dependentMatch : dependentMatches) {
					Collection<Object> requiredElts = requires.get(dependentMatch);
					requiredElts.remove(translatedElement);
					if (requiredElts.isEmpty())
						readySet.add(dependentMatch);
				}
			}

			// Kill siblings
			if (translatedBy.containsKey(translatedElement)) {
				Collection<IMatch> siblings = cfactory.createObjectSet();
				siblings.addAll(translatedBy.get(translatedElement));
				siblings.remove(m);

				readySet.removeAll(siblings);
				removeMatches(siblings);
			}
		}

		translated.addAll(translatedElts);
	}

	@Override
	public Set<IMatch> getMatches() {
		pending.forEach(this::handleMatch);
		pending.clear();
		return validate(readySet);
	}
	
	private Set<IMatch> validate(Set<IMatch> readySet) {
		if(pendingElts.isEmpty())
			return readySet;
		
		Set<IMatch> filteredReadySet = cfactory.createObjectSet();
		for(IMatch m : readySet) {
			if(m.getParameterNames().stream().anyMatch(p -> pendingElts.contains(m.get(p))))
				continue;
			filteredReadySet.add(m);
		}
		return filteredReadySet;
	}

	@Override
	public boolean removeMatch(IMatch match) {
		if (pending.contains(match)) {
			pending.remove(match);
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

		return true;
	}

	@Override
	public void removeMatches(Collection<IMatch> matches) {
		matches.forEach(this::removeMatch);
	}

	@Override
	public IMatch getNextKernel() {
		return getMatches().stream()//
				.filter(m -> strategy.isComplementMatch(m.getRuleName()))//
				.findAny()//
				.get();
	}

	@Override
	public String getRuleName(IMatch match) {
		if (isFusedPatternMatch(match.getPatternName()))
			return match.getPatternName();
		return match.getRuleName();
	}

	@Override
	public void removeAllMatches() {
		translatedBy.clear();
		requiredBy.clear();
		requires.clear();
		translates.clear();

		readySet.clear();
	}
}
