package org.emoflon.ibex.common.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXModel;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;

public abstract class PatternMatchingEngine<IBEX_MODEL extends IBeXModel, ENGINE_MATCH extends Object> {

	final protected IBEX_MODEL ibexModel;
	final protected ResourceSet model;
	protected Map<String, IBeXPattern> name2pattern;
	protected MatchFilter<IBEX_MODEL> matchFilter;

	/**
	 * The matches (key: pattern name, value: list of matches).
	 */
	protected Map<String, Collection<IMatch>> matches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IMatch>> pendingMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IMatch>> filteredMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IMatch>> addedMatches = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, Collection<IMatch>> removedMatches = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Subscriptions for notification of new matches (key: pattern name, value: list
	 * of consumers).
	 */
	protected Map<String, Set<Consumer<IMatch>>> subscriptionsForAppearingMatchesOfPattern = new HashMap<>();

	/**
	 * Subscriptions for notification of disappearing matches (key: pattern name,
	 * value: list of consumers).
	 */
	protected Map<String, Set<Consumer<IMatch>>> subscriptionsForDisappearingMatchesOfPattern = new HashMap<>();

	/**
	 * Subscriptions for notification of disappearing matches (key: match, value:
	 * list of consumers).
	 */
	protected Map<IMatch, Set<Consumer<IMatch>>> subscriptionsForDisappearingMatches = new HashMap<>();

	protected Map<IMatch, Queue<Consumer<IMatch>>> appearingSubscriptionJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<IMatch, Queue<Consumer<IMatch>>> disappearingSubscriptionJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());

	public PatternMatchingEngine(final IBEX_MODEL ibexModel, final ResourceSet model) {
		this.ibexModel = ibexModel;
		this.model = model;
		for (IBeXPattern pattern : ibexModel.getPatternSet().getPatterns()) {
			name2pattern.put(pattern.getName(), pattern);
			matches.put(pattern.getName(), Collections.synchronizedCollection(new LinkedHashSet<>()));
		}

		initialize();
		matchFilter = createMatchFilter();
	}

	/**
	 * Initialize the actual underlying PM engine and inject resource listeners into
	 * the model.
	 */
	protected abstract void initialize();

	/**
	 * Update the index structures of the actual underlying pattern matching engine.
	 */
	protected abstract void fetchMatchesFromEngine();

	/**
	 * Creates a match filter object that can be used to filter matches with
	 * additional parameters and arithmetic expressions not supported by the
	 * underlying pattern matcher.
	 * 
	 * @return IMatchFilter
	 */
	protected abstract MatchFilter<IBEX_MODEL> createMatchFilter();

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
	public synchronized Collection<IMatch> getMatches(final String patternName, boolean doUpdate) {
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
	public synchronized Collection<IMatch> getFilteredMatches(final String patternName, boolean doUpdate) {
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
	@SuppressWarnings("unchecked")
	public <M extends IMatch> Stream<M> getMatchStream(final String patternName, boolean doUpdate) {
		// Hiding update calls from the user seems dangerous to me. In my experience
		// this practice more often than not leads to a huge amount of nested update
		// calls, leading to stack overflows.
		if (doUpdate)
			updateMatches();

		if (filteredMatches.containsKey(patternName)) {
			return filteredMatches.get(patternName).stream().map(m -> (M) m);
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
			return filteredMatches.get(patternName).stream().map(m -> (M) m);
		}
	}

	/**
	 * Clean up any allocated memory for match indexing purposes (aka. shut down
	 * engine).
	 */
	public abstract void terminate();

	public abstract IMatch transformToIMatch(ENGINE_MATCH match);

	/**
	 * Adds a match to the set of valid matches.
	 * 
	 * @param match the match
	 */
	protected void addMatch(ENGINE_MATCH eMatch) {
		IMatch match = transformToIMatch(eMatch);

		String patternName = match.getPatternName();
		if (!matches.containsKey(patternName)) {
			matches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
		}
		matches.get(patternName).add(match);

//		Check whether there are any subscribers, if not return. -> No need to track deltas.
		if (subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty()
				&& subscriptionsForDisappearingMatchesOfPattern.isEmpty())
			return;

		if (removedMatches.containsKey(patternName) && removedMatches.get(patternName).contains(match)) {
			removedMatches.get(patternName).remove(match);
			return;
		}

		if (!addedMatches.containsKey(patternName)) {
			addedMatches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
		}
		addedMatches.get(patternName).add(match);
	}

	/**
	 * Removes a match from the set of valid matches.
	 * 
	 * @param match the match
	 */
	protected void removeMatch(ENGINE_MATCH eMatch) {
		IMatch match = transformToIMatch(eMatch);

		String patternName = match.getPatternName();
		if (matches.containsKey(patternName)) {
			matches.get(patternName).remove(match);

//			Check whether there are any subscribers, if not return. -> No need to track deltas.
			if (subscriptionsForAppearingMatchesOfPattern.isEmpty() && subscriptionsForDisappearingMatches.isEmpty()
					&& subscriptionsForDisappearingMatchesOfPattern.isEmpty())
				return;

			if (addedMatches.containsKey(patternName) && addedMatches.get(patternName).contains(match)) {
				addedMatches.get(patternName).remove(match);
				return;
			}

			if (!removedMatches.containsKey(patternName)) {
				removedMatches.put(patternName, Collections.synchronizedSet(new HashSet<IMatch>()));
			}
			removedMatches.get(patternName).add(match);

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
	public void subscribeAppearing(final String patternName, final Consumer<IMatch> consumer) {
		Set<Consumer<IMatch>> subs = subscriptionsForAppearingMatchesOfPattern.get(patternName);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IMatch>>());
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
	public void unsubscibeAppearing(final String patternName, final Consumer<IMatch> consumer) {
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
	public void subscribeDisappearing(final String patternName, final Consumer<IMatch> consumer) {
		Set<Consumer<IMatch>> subs = subscriptionsForDisappearingMatchesOfPattern.get(patternName);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IMatch>>());
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
	public void unsubscibeDisappearing(final String patternName, final Consumer<IMatch> consumer) {
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
	public void subscribeMatchDisappears(final IMatch match, final Consumer<IMatch> consumer) {
		Set<Consumer<IMatch>> subs = subscriptionsForDisappearingMatches.get(match);
		if (subs == null) {
			subs = Collections.synchronizedSet(new LinkedHashSet<Consumer<IMatch>>());
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
	public void unsubscribeMatchDisappears(final IMatch match, final Consumer<IMatch> consumer) {
		if (subscriptionsForDisappearingMatches.containsKey(match)) {
			subscriptionsForDisappearingMatches.get(match).remove(consumer);
		}
	}

	public IMatch createEmptyMatchForPattern(final IBeXPattern pattern) {
		// TODO: Use the pattern to create proper typed match!
		SimpleMatch match = new SimpleMatch(pattern.getName());
		if (pattern.isEmpty()) {
			return match;
		} else {
			pattern.getSignatureNodes().forEach(node -> match.put(node.getName(), null));
		}

		match.setHashCode(Objects.hash(match.getParameterNames()));

		return match;
	}

	protected synchronized void updateMatchesInternal(final String patternName) {
		if (filteredMatches.containsKey(patternName)) {
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
			return;
		}
	}

	protected void updateFilteredMatches(final String patternName) {
		Collection<IMatch> patternMatches = Collections.synchronizedSet(new HashSet<IMatch>());
		filteredMatches.put(patternName, patternMatches);

		IBeXPattern pattern = name2pattern.get(patternName);
		if (pattern.isEmpty()) {
			// Check for any NACs or PACs that are attached to the empty-create pattern
			List<IBeXPatternInvocation> invocations = Collections.synchronizedList(new LinkedList<>());
			invocations.addAll(pattern.getInvocations());

			if (invocations.isEmpty()) {
				IMatch match = createEmptyMatchForPattern(name2pattern.get(patternName));
				patternMatches.add(match);
			} else {
				boolean invocationsViolated = false;
				for (IBeXPatternInvocation invocation : invocations) {
					updateMatchesInternal(invocation.getInvocation().getName());
					Collection<IMatch> matches = filteredMatches.get(invocation.getInvocation().getName());
					if (invocation.isPositive() && (matches == null || matches.size() <= 0)) {
						invocationsViolated = true;
					}
					if (!invocation.isPositive() && matches.size() > 0) {
						invocationsViolated = true;
					}
				}
				if (!invocationsViolated) {
					IMatch match = createEmptyMatchForPattern(name2pattern.get(patternName));
					patternMatches.add(match);
				} else {
					patternMatches.clear();
				}
			}
		} else {
			patternMatches.addAll(matchFilter.filterMatches(pattern));
		}
	}

	/**
	 * Updates the matches.
	 */
	public synchronized void updateMatches() {
		// Clear old state
		filteredMatches.clear();
		addedMatches.clear();
		removedMatches.clear();

		// Fetch matches from pm
		fetchMatchesFromEngine();

		// (1) ADDED: Check for appearing match subscribers and filter matches
		subscriptionsForAppearingMatchesOfPattern.keySet().stream().forEach(patternName -> {
			// Check if pending matches became valid again due to attribute changes
			// Fill filtered matches Map by calling the match stream
			getMatchStream(patternName, false);

			// Check if existing matches recently became valid (pending) and add removal
			// jobs

			Collection<IMatch> matchCollection;
			matchCollection = matches.get(patternName);
			matchCollection.parallelStream()
					.filter(match -> filteredMatches.containsKey(patternName)
							&& filteredMatches.get(patternName).contains(match))
					.filter(match -> pendingMatches.containsKey(patternName)
							&& pendingMatches.get(patternName).contains(match))
					.forEach(match -> {
						Collection<IMatch> pending = pendingMatches.get(patternName);
						pending.remove(match);

						Collection<IMatch> added = addedMatches.get(patternName);
						if (added == null) {
							added = Collections.synchronizedSet(new HashSet<>());
							addedMatches.put(patternName, added);
						}
						added.add(match);

						Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(match);
						if (subs == null) {
							subs = new LinkedBlockingQueue<>();
							appearingSubscriptionJobs.put(match, subs);
						}
						subs.addAll(subscriptionsForAppearingMatchesOfPattern.get(patternName));
					});

			if (addedMatches.containsKey(patternName)) {
				// Remove structurally valid matches that do not satisfy additional constraints
				Set<IMatch> pendingAdded = new HashSet<>();
				addedMatches.get(patternName).stream().filter(match -> !filteredMatches.containsKey(patternName)
						|| !filteredMatches.get(patternName).contains(match)).forEach(match -> {
							pendingAdded.add(match);
							Collection<IMatch> pending = pendingMatches.get(patternName);
							if (pending == null) {
								pending = Collections.synchronizedSet(new HashSet<>());
								pendingMatches.put(patternName, pending);
							}
							pending.add(match);
						});
				addedMatches.get(patternName).removeAll(pendingAdded);

				addedMatches.get(patternName).stream().forEach(match -> {
					Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(match);
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
			getMatchStream(patternName, false);

			// Check if existing matches recently became invalid (not pending) and add
			// removal jobs
			Collection<IMatch> matchCollection;
			matchCollection = matches.get(patternName);
			matchCollection.parallelStream()
					.filter(match -> !filteredMatches.containsKey(patternName)
							|| !filteredMatches.get(patternName).contains(match))
					.filter(match -> !pendingMatches.containsKey(patternName)
							|| !pendingMatches.get(patternName).contains(match))
					.forEach(match -> {
						Collection<IMatch> pending = pendingMatches.get(patternName);
						if (pending == null) {
							pending = Collections.synchronizedSet(new HashSet<>());
							pendingMatches.put(patternName, pending);
						}
						pending.add(match);

						Collection<IMatch> removed = removedMatches.get(patternName);
						if (removed == null) {
							removed = Collections.synchronizedSet(new HashSet<>());
							removedMatches.put(patternName, removed);
						}
						removed.add(match);

						Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(match);
						if (subs == null) {
							subs = new LinkedBlockingQueue<>();
							disappearingSubscriptionJobs.put(match, subs);
						}
						subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
					});

			if (removedMatches.containsKey(patternName)) {
				// Ignore previously invalidated matches due to violation of additional
				// constraints
				Set<IMatch> pendingRemoved = new HashSet<>();
				removedMatches.get(patternName).stream().filter(match -> pendingMatches.containsKey(patternName)
						&& pendingMatches.get(patternName).contains(match)).forEach(match -> {
							pendingRemoved.add(match);
							pendingMatches.get(patternName).remove(match);
						});
				removedMatches.get(patternName).removeAll(pendingRemoved);

				removedMatches.get(patternName).forEach(match -> {
					Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(match);
					if (subs == null) {
						subs = new LinkedBlockingQueue<>();
						disappearingSubscriptionJobs.put(match, subs);
					}
					subs.addAll(subscriptionsForDisappearingMatchesOfPattern.get(patternName));
				});
			}
		});

		// Check if any match removed subscriptions are triggered by removals
		Set<IMatch> removals = subscriptionsForDisappearingMatches.keySet().parallelStream()
				.filter(match -> removedMatches.containsKey(match.getPatternName()))
				.filter(match -> removedMatches.get(match.getPatternName()).contains(match))
				.collect(Collectors.toSet());

		for (IMatch removal : removals) {
			Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(removal);
			if (subs == null) {
				subs = new LinkedBlockingQueue<>();
				disappearingSubscriptionJobs.put(removal, subs);
			}
			subs.addAll(subscriptionsForDisappearingMatches.get(removal));
		}

		// Remove jobs for appearing matches for each disappeared match
		for (IMatch removal : removals) {
			appearingSubscriptionJobs.remove(removal);
		}

		notifySubscriptions();
	}

	public void notifySubscriptions() {
		while (!disappearingSubscriptionJobs.isEmpty()) {
			IMatch nextMatch = disappearingSubscriptionJobs.keySet().iterator().next();
			Queue<Consumer<IMatch>> subs = disappearingSubscriptionJobs.get(nextMatch);
			disappearingSubscriptionJobs.remove(nextMatch);
			while (!subs.isEmpty()) {
				subs.poll().accept(nextMatch);
			}
		}

		Set<IMatch> jobs = new LinkedHashSet<>(appearingSubscriptionJobs.keySet());
		for (IMatch nextMatch : jobs) {
			Queue<Consumer<IMatch>> subs = appearingSubscriptionJobs.get(nextMatch);
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

}
