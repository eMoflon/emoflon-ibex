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
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public abstract class IBeXGTPatternMatcher<E extends IBeXGTPatternMatcher<E, ENGINE_MATCH>, ENGINE_MATCH extends Object>
		extends PatternMatchingEngine<GTModel, ENGINE_MATCH, IBeXGTMatch<?, ?>> {

	protected Map<String, IBeXGTPattern<?, ?>> name2typedPattern = Collections.synchronizedMap(new LinkedHashMap<>());

	public IBeXGTPatternMatcher(GTModel ibexModel, ResourceSet model) {
		super(ibexModel, model);
	}

	protected void registerTypedPattern(IBeXGTPattern<?, ?> typedPattern) {
		name2typedPattern.put(typedPattern.patternName, typedPattern);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewMatchCollection(final String patternName) {
		return (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName).getMatches();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewPendingMatchCollection(final String patternName) {
		return (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName).getPendingMatches();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewFilteredMatchCollection(final String patternName) {
		return (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName).getFilteredMatches();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewAddedMatchCollection(final String patternName) {
		return (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName).getAddedMatches();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<IBeXGTMatch<?, ?>> insertNewRemovedMatchCollection(final String patternName) {
		return (Collection<IBeXGTMatch<?, ?>>) name2typedPattern.get(patternName).getRemovedMatches();
	}

	@Override
	protected MatchFilter<GTModel, IBeXGTMatch<?, ?>> createMatchFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateFilteredMatches(final String patternName) {
		IBeXPattern pattern = name2pattern.get(patternName);
		if (!pattern.isEmpty())
			super.updateFilteredMatches(patternName);

		Collection<IBeXGTMatch<?, ?>> patternMatches = insertNewFilteredMatchCollection(patternName);
		// Check for any NACs or PACs that are attached to the empty-create pattern
		List<IBeXPatternInvocation> invocations = Collections.synchronizedList(new LinkedList<>());
		invocations.addAll(pattern.getInvocations());

		if (invocations.isEmpty()) {
			// Create a new empty match -> The pattern will know what to do
			IBeXGTMatch<?, ?> match = name2typedPattern.get(patternName).createMatch(new HashMap<>());
			patternMatches.add(match);
		} else {
			boolean invocationsViolated = false;
			for (IBeXPatternInvocation invocation : invocations) {
				updateMatchesInternal(invocation.getInvocation().getName());
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
