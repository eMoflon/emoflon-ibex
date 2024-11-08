package org.emoflon.ibex.common.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;

public abstract class PatternMatchingEngine<IBEX_MODEL extends IBeXModel, EM, IM extends IMatch> {

	final protected IBEX_MODEL ibexModel;
	protected ResourceSet model;
	protected Map<String, IBeXPattern> name2pattern = Collections.synchronizedMap(new LinkedHashMap<>());
	protected MatchFilter<?, IBEX_MODEL, IM> matchFilter;
	protected IBeXPMEngineInformation engineProperties;

	/**
	 * The matches (key: pattern name, value: list of matches).
	 */
	protected Map<String, Collection<IM>> matches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IM>> pendingMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IM>> filteredMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IM>> addedMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IM>> removedMatches = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Subscriptions for notification of new matches (key: pattern name, value: list
	 * of consumers).
	 */
	protected Map<String, Set<Consumer<IM>>> subscriptionsForAppearingMatchesOfPattern = new HashMap<>();

	/**
	 * Subscriptions for notification of disappearing matches (key: pattern name,
	 * value: list of consumers).
	 */
	protected Map<String, Set<Consumer<IM>>> subscriptionsForDisappearingMatchesOfPattern = new HashMap<>();

	/**
	 * Subscriptions for notification of disappearing matches (key: match, value:
	 * list of consumers).
	 */
	protected Map<IM, Set<Consumer<IM>>> subscriptionsForDisappearingMatches = new HashMap<>();

	protected Map<IM, Queue<Consumer<IM>>> appearingSubscriptionJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<IM, Queue<Consumer<IM>>> disappearingSubscriptionJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());

	public PatternMatchingEngine(final IBEX_MODEL ibexModel, final ResourceSet model) {
		engineProperties = createEngineProperties();
		this.ibexModel = ibexModel;
		this.model = model;
		if(ibexModel != null)
			for (IBeXPattern pattern : ibexModel.getPatternSet().getPatterns()) {
				name2pattern.put(pattern.getName(), pattern);
			}
		initialize();
		matchFilter = createMatchFilter();
	}

	protected abstract Collection<IM> insertNewMatchCollection(final String patternName);

	protected abstract Collection<IM> insertNewPendingMatchCollection(final String patternName);

	protected abstract Collection<IM> insertNewFilteredMatchCollection(final String patternName);

	protected abstract Collection<IM> insertNewAddedMatchCollection(final String patternName);

	protected abstract Collection<IM> insertNewRemovedMatchCollection(final String patternName);

	/**
	 * Initialize the actual underlying PM engine and inject resource listeners into
	 * the model.
	 */
	protected abstract void initialize();

	/**
	 * Update the index structures of the actual underlying pattern matching engine.
	 */
	protected abstract void fetchMatches();

	public abstract IM transformToIMatch(final EM match);

	/**
	 * Creates a match filter object that can be used to filter matches with
	 * additional parameters and arithmetic expressions not supported by the
	 * underlying pattern matcher.
	 * 
	 * @return IMatchFilter
	 */
	protected abstract MatchFilter<?, IBEX_MODEL, IM> createMatchFilter();

	protected abstract IBeXPMEngineInformation createEngineProperties();

	public IBeXPMEngineInformation getEngineProperties() {
		return engineProperties;
	}

	/**
	 * Clean up any allocated memory for match indexing purposes (aka. shut down
	 * engine).
	 */
	public abstract void terminate();

	/**
	 * Returns all currently available matches for the pattern, but without
	 * filtering any additional conditions (e.g., binding, parameterized constrains
	 * etc.).
	 * 
	 * @param patternName the name of the pattern
	 * @param doUpdate    triggers the incremental recalculation of all matches
	 *                    before filtering matches
	 * @return a {@link Collection} of matches
	 */
	public synchronized Collection<IM> getMatches(final String patternName, boolean doUpdate) {
		if (doUpdate)
			updateMatches();

		return matches.get(patternName);
	}

	/**
	 * Returns all currently available matches for the pattern, filtered using
	 * additional conditions (e.g., binding, parameterized constrains etc.).
	 * 
	 * @param patternName the name of the pattern
	 * @param doUpdate    triggers the incremental recalculation of all matches
	 *                    before filtering matches
	 * @return a {@link Collection} of matches
	 */
	public synchronized Collection<IM> getFilteredMatches(final String patternName, boolean doUpdate) {
		if (doUpdate)
			updateMatches();

		return filteredMatches.get(patternName);
	}

	/**
	 * Finds all matches for the pattern.
	 * 
	 * @param patternName the name of the pattern
	 * @param doUpdate    triggers the incremental recalculation of all matches
	 *                    before filtering matches
	 * @return a {@link Stream} of matches
	 */
	public Stream<IM> getMatchStream(final String patternName, boolean doUpdate) {
		// Hiding update calls from the user seems dangerous to me. In my experience
		// this practice more often than not leads to a huge amount of nested update
		// calls, leading to stack overflows.
		if (doUpdate)
			updateMatches();

		return filteredMatches.get(patternName).stream();
	}

	/**
	 * Adds a match to the set of valid matches.
	 * 
	 * @param match the match
	 */
	protected void addMatch(EM eMatch) {
		IM match = transformToIMatch(eMatch);

		String patternName = match.getPatternName();
		Collection<IM> matches = this.matches.get(patternName);
		if (matches == null) {
			matches = insertNewMatchCollection(patternName);
		}
		matches.add(match);

//		Check whether there are any subscribers, if not return. -> No need to track deltas.
		if (subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty()
				&& subscriptionsForDisappearingMatchesOfPattern.isEmpty())
			return;

		if (removedMatches.containsKey(patternName) && removedMatches.get(patternName).contains(match)) {
			removedMatches.get(patternName).remove(match);
			return;
		}

		Collection<IM> addedMatches = this.addedMatches.get(patternName);
		if (addedMatches == null) {
			addedMatches = insertNewAddedMatchCollection(patternName);
		}
		addedMatches.add(match);
	}

	/**
	 * Removes a match from the set of valid matches.
	 * 
	 * @param match the match
	 */
	protected void removeMatch(EM eMatch) {
		IM match = transformToIMatch(eMatch);

		String patternName = match.getPatternName();
		Collection<IM> matches = this.matches.get(patternName);
		if (matches != null) {
			matches.remove(match);

//			Check whether there are any subscribers, if not return. -> No need to track deltas.
			if (subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty()
					&& subscriptionsForDisappearingMatchesOfPattern.isEmpty())
				return;

			if (addedMatches.containsKey(patternName) && addedMatches.get(patternName).contains(match)) {
				addedMatches.get(patternName).remove(match);
				return;
			}

			Collection<IM> removedMatches = this.removedMatches.get(patternName);
			if (removedMatches == null) {
				removedMatches = insertNewRemovedMatchCollection(patternName);
			}
			removedMatches.add(match);

		} else {
			throw new IllegalArgumentException("Cannot remove a match which was never added!");
		}
	}

	/**
	 * Subscribes notifications whenever a new match for the pattern is found.
	 * 
	 * @param patternName the name of the pattern
	 * @param consumer    the consumer to add
	 */
	public void subscribeAppearing(final String patternName, final Consumer<IM> consumer) {
		Set<Consumer<IM>> subs = subscriptionsForAppearingMatchesOfPattern.get(patternName);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IM>>());
			subscriptionsForAppearingMatchesOfPattern.put(patternName, subs);
		}
		subs.add(consumer);
	}

	/**
	 * Deletes the subscription of the notifications for the given pattern and
	 * consumer.
	 * 
	 * @param patternName the name of the pattern
	 * @param consumer    the consumer to remove
	 */
	public void unsubscibeAppearing(final String patternName, final Consumer<IM> consumer) {
		if (subscriptionsForAppearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForAppearingMatchesOfPattern.get(patternName).remove(consumer);
		}
	}

	/**
	 * Subscribes notifications whenever a match for the pattern disappears.
	 * 
	 * @param patternName the name of the pattern
	 * @param consumer    the consumer to add
	 */
	public void subscribeDisappearing(final String patternName, final Consumer<IM> consumer) {
		Set<Consumer<IM>> subs = subscriptionsForDisappearingMatchesOfPattern.get(patternName);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IM>>());
			subscriptionsForDisappearingMatchesOfPattern.put(patternName, subs);
		}
		subs.add(consumer);
	}

	/**
	 * Deletes the subscription of the notifications for the given pattern and
	 * consumer.
	 * 
	 * @param patternName the name of the pattern
	 * @param consumer    the consumer to remove
	 */
	public void unsubscibeDisappearing(final String patternName, final Consumer<IM> consumer) {
		if (subscriptionsForDisappearingMatchesOfPattern.containsKey(patternName)) {
			subscriptionsForDisappearingMatchesOfPattern.get(patternName).remove(consumer);
		}
	}

	/**
	 * Subscribes notifications whenever the given match disappears.
	 * 
	 * @param match    the match
	 * @param consumer the consumer to add
	 */
	public void subscribeMatchDisappears(final IM match, final Consumer<IM> consumer) {
		Set<Consumer<IM>> subs = subscriptionsForDisappearingMatches.get(match);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IM>>());
			subscriptionsForDisappearingMatches.put(match, subs);
		}
		subs.add(consumer);
	}

	/**
	 * Deletes the subscription of notifications when the given match disappears.
	 * 
	 * @param match    the match
	 * @param consumer the consumer to add
	 */
	public void unsubscribeMatchDisappears(final IM match, final Consumer<IM> consumer) {
		if (subscriptionsForDisappearingMatches.containsKey(match)) {
			subscriptionsForDisappearingMatches.get(match).remove(consumer);
		}
	}

	protected synchronized void updateMatchesInternal(final String patternName) {
		if (!matches.containsKey(patternName)) {
			insertNewMatchCollection(patternName);
			insertNewFilteredMatchCollection(patternName);
			return;
		}

		if (matches.get(patternName).isEmpty()) {
			return;
		} else {
			IBeXPattern pattern = name2pattern.get(patternName);
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
	}

	protected void updateFilteredMatches(final String patternName) {
		Collection<IM> patternMatches = filteredMatches.get(patternName);
		if (patternMatches == null) {
			patternMatches = insertNewFilteredMatchCollection(patternName);
		}
		IBeXPattern pattern = name2pattern.get(patternName);
		patternMatches.addAll(matchFilter.filterMatches(pattern));
	}

	/**
	 * Updates the matches.
	 */
	public synchronized void updateMatches() {
		var updatedPatterns = new HashSet<String>();
		
		// Clear old state
		filteredMatches.forEach((patternName, collection) -> collection.clear());
		addedMatches.forEach((patternName, collection) -> collection.clear());
		removedMatches.forEach((patternName, collection) -> collection.clear());

		// Fetch matches from pm
		fetchMatches();
		
		// Update filtered matches
		name2pattern.keySet().forEach(patternName -> updateMatchesInternal(patternName));

		// (1) ADDED: Check for appearing match subscribers and filter matches
		subscriptionsForAppearingMatchesOfPattern.keySet().stream().forEach(patternName -> {
			// Check if pending matches became valid again due to attribute changes
			// Fill filtered matches Map by calling the match stream
			if(updatedPatterns.add(patternName))
				updateMatchesInternal(patternName);

			// Check if existing matches recently became valid (pending) and add removal
			// jobs

			Collection<IM> matchCollection;
			matchCollection = matches.get(patternName);
			matchCollection.parallelStream()
					.filter(match -> filteredMatches.containsKey(patternName)
							&& filteredMatches.get(patternName).contains(match))
					.filter(match -> pendingMatches.containsKey(patternName)
							&& pendingMatches.get(patternName).contains(match))
					.forEach(match -> {
						Collection<IM> pending = pendingMatches.get(patternName);
						pending.remove(match);

						Collection<IM> added = addedMatches.get(patternName);
						if (added == null) {
							added = insertNewAddedMatchCollection(patternName);
						}
						added.add(match);

						Queue<Consumer<IM>> subs = appearingSubscriptionJobs.get(match);
						if (subs == null) {
							subs = new LinkedBlockingQueue<>();
							appearingSubscriptionJobs.put(match, subs);
						}
						subs.addAll(subscriptionsForAppearingMatchesOfPattern.get(patternName));
					});

			if (addedMatches.containsKey(patternName)) {
				// Remove structurally valid matches that do not satisfy additional constraints
				Set<IM> pendingAdded = new HashSet<>();
				addedMatches.get(patternName).stream().filter(match -> !filteredMatches.containsKey(patternName)
						|| !filteredMatches.get(patternName).contains(match)).forEach(match -> {
							pendingAdded.add(match);
							Collection<IM> pending = pendingMatches.get(patternName);
							if (pending == null) {
								pending = insertNewPendingMatchCollection(patternName);
							}
							pending.add(match);
						});
				addedMatches.get(patternName).removeAll(pendingAdded);

				addedMatches.get(patternName).stream().forEach(match -> {
					Queue<Consumer<IM>> subs = appearingSubscriptionJobs.get(match);
					if (subs == null) {
						subs = new LinkedBlockingQueue<>();
						appearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForAppearingMatchesOfPattern.get(patternName));
				});
			}

		});

		// (2) DELETED: Check for disappearing match subscribers and filter matches
		subscriptionsForDisappearingMatchesOfPattern.keySet().stream().forEach(patternName -> {
			// Check if existing matches became invalid due to attribute changes
			// Fill filtered matches Map by calling the match stream
			if(updatedPatterns.add(patternName))
				updateMatchesInternal(patternName);

			// Check if existing matches recently became invalid (not pending) and add
			// removal jobs
			Collection<IM> matchCollection;
			matchCollection = matches.get(patternName);
			matchCollection.parallelStream()
					.filter(match -> !filteredMatches.containsKey(patternName)
							|| !filteredMatches.get(patternName).contains(match))
					.filter(match -> !pendingMatches.containsKey(patternName)
							|| !pendingMatches.get(patternName).contains(match))
					.forEach(match -> {
						Collection<IM> pending = pendingMatches.get(patternName);
						if (pending == null) {
							pending = insertNewPendingMatchCollection(patternName);
						}
						pending.add(match);

						Collection<IM> removed = removedMatches.get(patternName);
						if (removed == null) {
							removed = insertNewRemovedMatchCollection(patternName);
						}
						removed.add(match);

						Queue<Consumer<IM>> subs = disappearingSubscriptionJobs.get(match);
						if (subs == null) {
							subs = new LinkedBlockingQueue<>();
							disappearingSubscriptionJobs.put(match, subs);
						}
						subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
					});

			if (removedMatches.containsKey(patternName)) {
				// Ignore previously invalidated matches due to violation of additional
				// constraints
				Set<IM> pendingRemoved = new HashSet<>();
				removedMatches.get(patternName).stream().filter(match -> pendingMatches.containsKey(patternName)
						&& pendingMatches.get(patternName).contains(match)).forEach(match -> {
							pendingRemoved.add(match);
							pendingMatches.get(patternName).remove(match);
						});
				removedMatches.get(patternName).removeAll(pendingRemoved);

				removedMatches.get(patternName).forEach(match -> {
					Queue<Consumer<IM>> subs = disappearingSubscriptionJobs.get(match);
					if (subs == null) {
						subs = new LinkedBlockingQueue<>();
						disappearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
				});
			}
		});

		// Check if any match removed subscriptions are triggered by removals
		Set<IM> removals = subscriptionsForDisappearingMatches.keySet().parallelStream()
				.filter(match -> removedMatches.containsKey(match.getPatternName()))
				.filter(match -> removedMatches.get(match.getPatternName()).contains(match))
				.collect(Collectors.toSet());

		for (IM removal : removals) {
			Queue<Consumer<IM>> subs = disappearingSubscriptionJobs.get(removal);
			if (subs == null) {
				subs = new LinkedBlockingQueue<>();
				disappearingSubscriptionJobs.put(removal, subs);
			}
			subs.addAll(subscriptionsForDisappearingMatches.get(removal));
		}

		// Remove jobs for appearing matches for each disappeared match
		for (IM removal : removals) {
			appearingSubscriptionJobs.remove(removal);
		}

		notifySubscriptions();
	}

	public void notifySubscriptions() {
		while (!disappearingSubscriptionJobs.isEmpty()) {
			IM nextMatch = disappearingSubscriptionJobs.keySet().iterator().next();
			Queue<Consumer<IM>> subs = disappearingSubscriptionJobs.get(nextMatch);
			disappearingSubscriptionJobs.remove(nextMatch);
			while (!subs.isEmpty()) {
				subs.poll().accept(nextMatch);
			}
		}

		Set<IM> jobs = new LinkedHashSet<>(appearingSubscriptionJobs.keySet());
		for (IM nextMatch : jobs) {
			Queue<Consumer<IM>> subs = appearingSubscriptionJobs.get(nextMatch);
			if (subs == null || subs.isEmpty()) {
				appearingSubscriptionJobs.remove(nextMatch);
				continue;
			}

			appearingSubscriptionJobs.remove(nextMatch);
			while (!subs.isEmpty()) {
				subs.poll().accept(nextMatch);
			}
		}
	}
	
	public void clearMatches() {
		matches.forEach((patternName, collection) -> collection.clear());
	}

}
