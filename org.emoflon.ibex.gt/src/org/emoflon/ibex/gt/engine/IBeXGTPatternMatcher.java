package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public abstract class IBeXGTPatternMatcher<EM> extends PatternMatchingEngine<GTModel, EM, IBeXGTMatch<?, ?>> {

	protected Map<String, IBeXGTPattern<?, ?>> name2typedPattern;

	public IBeXGTPatternMatcher(GTModel ibexModel, ResourceSet model) {
		super(ibexModel, model);
	}

	protected abstract Map<String, Object> extractNodes(final EM match);

	protected abstract String extractPatternName(final EM match);

	@Override
	public void terminate() {
		name2typedPattern.values().forEach(p -> p.terminate());
	}

	@Override
	public IBeXGTMatch<?, ?> transformToIMatch(final EM match) {
		return name2typedPattern.get(extractPatternName(match)).createMatch(extractNodes(match));
	}

	public IBeXGTPattern<?, ?> getTypedPattern(final String patternName) {
		return name2typedPattern.get(patternName);
	}

	@Override
	protected void initialize() {
		name2typedPattern = Collections.synchronizedMap(new LinkedHashMap<>());
	}

	protected void registerTypedPattern(IBeXGTPattern<?, ?> typedPattern) {
		name2typedPattern.put(typedPattern.patternName, typedPattern);
	}

	protected void unRegisterTypedPattern(IBeXGTPattern<?, ?> typedPattern) {
		name2typedPattern.remove(typedPattern.patternName, typedPattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewMatchCollection(final String patternName) {
		Collection<IBeXGTMatch<?, ?>> m = (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName)
				.getUnfilteredMatches();
		matches.put(patternName, m);
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewPendingMatchCollection(final String patternName) {
		Collection<IBeXGTMatch<?, ?>> m = (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName)
				.getPendingMatches();
		pendingMatches.put(patternName, m);
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewFilteredMatchCollection(final String patternName) {
		Collection<IBeXGTMatch<?, ?>> m = (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName)
				.getFilteredMatches();
		filteredMatches.put(patternName, m);
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewAddedMatchCollection(final String patternName) {
		Collection<IBeXGTMatch<?, ?>> m = (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName)
				.getAddedMatches();
		addedMatches.put(patternName, m);
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewRemovedMatchCollection(final String patternName) {
		Collection<IBeXGTMatch<?, ?>> m = (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName)
				.getRemovedMatches();
		removedMatches.put(patternName, m);
		return m;
	}

	@Override
	protected IBeXGTMatchFilter createMatchFilter() {
		return new IBeXGTMatchFilter(this);
	}

	@Override
	protected synchronized void updateMatchesInternal(final String patternName) {
		if (!matches.containsKey(patternName)) {
			insertNewMatchCollection(patternName);
			insertNewFilteredMatchCollection(patternName);
		}

		IBeXPattern pattern = name2pattern.get(patternName);
		if (!pattern.isEmpty() && matches.get(patternName).isEmpty()) {
			return;
		} else {

			if (pattern.getDependencies().isEmpty()) {
				updateFilteredMatches(patternName);
			} else {
				// Check dependencies to prevent deadlocks
				pattern.getDependencies().forEach(depPattern -> {
					updateMatchesInternal(depPattern.getName());
				});
				updateFilteredMatches(patternName);
			}
		}

		name2typedPattern.values().forEach(p -> p.update());
	}

	@Override
	protected void updateFilteredMatches(final String patternName) {
		IBeXPattern pattern = name2pattern.get(patternName);
		if (!pattern.isEmpty()) {
			super.updateFilteredMatches(patternName);
			return;
		}

		Collection<IBeXGTMatch<?, ?>> patternMatches = filteredMatches.get(patternName);
		if (patternMatches == null) {
			patternMatches = insertNewFilteredMatchCollection(patternName);
		}

		// Check for any NACs or PACs that are attached to the empty-create pattern
		List<IBeXPatternInvocation> invocations = Collections.synchronizedList(new LinkedList<>());
		invocations.addAll(pattern.getInvocations());

		if (invocations.isEmpty()) {
			// Create a new empty match -> The pattern will know what to do
			if (patternMatches.isEmpty()) {
				IBeXGTMatch<?, ?> match = name2typedPattern.get(patternName).createMatch(new HashMap<>());
				patternMatches.add(match);
			}
		} else {
			boolean invocationsViolated = false;
			for (IBeXPatternInvocation invocation : invocations) {
				Collection<IBeXGTMatch<?, ?>> matches = filteredMatches.get(invocation.getInvocation().getName());
				if (invocation.isPositive() && (matches == null || matches.size() <= 0)) {
					invocationsViolated = true;
				}
				if (!invocation.isPositive() && matches.size() > 0) {
					invocationsViolated = true;
				}
			}
			if (!invocationsViolated) {
				// Create a new empty match -> The pattern will know what to do
				IBeXGTMatch<?, ?> match = name2typedPattern.get(patternName).createMatch(new HashMap<>());
				patternMatches.add(match);
			} else {
				patternMatches.clear();
			}
		}

	}

}
