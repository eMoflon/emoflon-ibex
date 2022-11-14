package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.AttributeChangedAdapter;
import org.emoflon.ibex.common.emf.AttributeHistory;
import org.emoflon.ibex.common.engine.IBeXPMEngineInformation;
import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTWatchDog;

public abstract class IBeXGTPattern<P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>> {
	/**
	 * The API.
	 */
	protected final IBeXGtAPI<? extends IBeXGTPatternMatcher<?>, ?, ?> api;

	/**
	 * The pattern matcher.
	 */
	protected final IBeXGTPatternMatcher<?> patternMatcher;

	/**
	 * The gt engine.
	 */
	protected final IBeXGTEngine<? extends IBeXGTPatternMatcher<?>> gtEngine;

	/**
	 * The pattern's name.
	 */
	public final String patternName;

	/**
	 * The actual pattern.
	 */
	public final GTPattern pattern;

	protected Collection<M> matches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> pendingMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> filteredMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> addedMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> removedMatches = Collections.synchronizedSet(new LinkedHashSet<>());

	/**
	 * Consumer type conversion map
	 */
	protected Map<Consumer<M>, Consumer<IBeXGTMatch<?, ?>>> typed2genericConsumers = Collections
			.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Generic node bindings
	 */
	protected Map<String, Object> bindings = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Watch dog index structures
	 */
//	protected Map<GTWatchDog, BiFunction<M, Map<EObject, Set<M>>, EObject>> watchDog2insert = Collections
//			.synchronizedMap(new LinkedHashMap<>());
//	protected Map<GTWatchDog, Consumer<Notification>> watchDog2notifiers = Collections
//			.synchronizedMap(new LinkedHashMap<>());
	protected AttributeChangedAdapter attributeAdapter;
	protected Map<EObject, Set<M>> node2matches = Collections.synchronizedMap(new LinkedHashMap<>());
//	protected Map<EObject, AttributeHistory> node2changes = Collections
//			.synchronizedMap(new LinkedHashMap<>());
	protected Map<M, Set<EObject>> match2nodes = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<M, Map<EObject, AttributeHistory>> match2changes = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Creates a new pattern.
	 * 
	 * @param api         the API the pattern belongs to
	 * @param interpreter the interpreter
	 * @param patternName the name of the pattern
	 */
	public IBeXGTPattern(final IBeXGtAPI<? extends IBeXGTPatternMatcher<?>, ?, ?> api, final GTPattern pattern) {
		this.api = api;
		this.gtEngine = api.getGTEngine();
		this.patternMatcher = api.getGTEngine().getPatternMatcher();
		this.pattern = pattern;
		this.patternName = pattern.getName();
		patternMatcher.registerTypedPattern(this);
		if (pattern.getWatchDogs() != null) {
			registerWatchDogs(pattern.getWatchDogs());
		}
	}

	public abstract boolean checkBindings(final M match);

	public abstract boolean checkConditions(final M match);

	public abstract boolean hasArithmeticExpressions();

	public abstract boolean hasBooleanExpressions();

	public abstract boolean hasCountExpressions();

	public abstract boolean hasParameterExpressions();

	public abstract M createMatch(final Map<String, Object> nodes, Object... args);

	protected abstract Set<EObject> insertNodesAndMatch(final M match);

	protected Set<EObject> removeMatchAndMaybeNodes(final M match) {
		Set<EObject> removedNodes = match2nodes.get(match);
		match2nodes.remove(match);

		if (removedNodes == null) {
			return Collections.synchronizedSet(new LinkedHashSet<>());
		}
		for (EObject node : removedNodes) {
			Set<M> matches = node2matches.get(node);
			if (matches == null)
				continue;

			matches.remove(match);
			if (matches.isEmpty())
				node2matches.remove(node);
		}

		return removedNodes;
	}

	/**
	 * Returns the parameters.
	 * 
	 * @return the parameters
	 */
	public abstract Map<String, Object> getParameters();

	/**
	 * Returns the names of the parameters which can be bound for this pattern.
	 * 
	 * @return the parameter names
	 */
	public abstract Collection<String> getParameterNames();

	public abstract P setParameters(final Map<String, Object> parameters);

	public Map<String, Object> getBindings() {
		return new HashMap<>(bindings);
	}

	protected IBeXGTPattern<P, M> setBinding(final String name, final Object binding) {
		bindings.put(name, binding);
		return this;
	}

	protected IBeXGTPattern<P, M> unsetBinding(final String name) {
		bindings.remove(name);
		return this;
	}

	public boolean requiresChecks(final IBeXPMEngineInformation properties) {
		if (hasArithmeticExpressions() && !properties.supports_arithmetic_attr_constraints())
			return true;
		if (hasBooleanExpressions() && !properties.supports_boolean_attr_constraints())
			return true;
		if (hasCountExpressions() && !properties.supports_count_matches())
			return true;
		if (hasParameterExpressions() && !properties.supports_parameter_attr_constraints())
			return true;

		return false;
	}

	public boolean isEmptyPattern() {
		return pattern.isEmpty();
	}

	/**
	 * Finds and returns an arbitrary match for the pattern if a match exists.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public Optional<M> findAnyMatch(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return filteredMatches.stream().findAny();
	}

	public Optional<M> findAnyMatch() {
		if (gtEngine.alwaysUpdatePrior)
			patternMatcher.updateMatches();

		return filteredMatches.stream().findAny();
	}

	/**
	 * Finds and returns the set of all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public Collection<M> getMatches(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return new LinkedHashSet<>(filteredMatches);
	}

	public Collection<M> getMatches() {
		if (gtEngine.alwaysUpdatePrior)
			patternMatcher.updateMatches();

		return new LinkedHashSet<>(filteredMatches);
	}

	/**
	 * Finds and returns all matches for the pattern as a Stream.
	 * 
	 * @return the Stream of matches
	 */
	public Stream<M> matchStream(boolean doUpdate) {
		return getMatches(doUpdate).stream();
	}

	public Stream<M> matchStream() {
		return getMatches().stream();
	}

	/**
	 * Returns whether any matches for the pattern exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public boolean hasMatches(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return !filteredMatches.isEmpty();
	}

	public boolean hasMatches() {
		if (gtEngine.alwaysUpdatePrior)
			patternMatcher.updateMatches();

		return !filteredMatches.isEmpty();
	}

	/**
	 * Returns the number of matches found for the pattern.
	 * 
	 * @return the number of matches
	 */
	public long countMatches(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return filteredMatches.size();
	}

	public long countMatches() {
		if (gtEngine.alwaysUpdatePrior)
			patternMatcher.updateMatches();

		return filteredMatches.size();
	}

	public Collection<M> getMatchesWithAttributeChanges() {
		return new LinkedHashSet<>(match2changes.keySet());
	}

	public Collection<AttributeHistory> getAttributeChanges(final M match) {
		Map<EObject, AttributeHistory> changes = match2changes.get(match);
		if (changes == null)
			return new LinkedHashSet<>();

		return new LinkedHashSet<>(changes.values());
	}

	public Optional<AttributeHistory> getAttributeChanges(final M match, final EObject node) {
		Map<EObject, AttributeHistory> changes = match2changes.get(match);
		if (changes == null)
			return Optional.empty();

		if (changes.containsKey(node)) {
			return Optional.of(changes.get(node));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Subscribes notifications of all new matches found for the pattern. Whenever a
	 * new match for this pattern appears, the <code>accept</code> methods of all
	 * {@link Consumer}s will be called immediately.
	 * 
	 * @param action the {@link Consumer} to notify
	 */
	public IBeXGTPattern<P, M> subscribeAppearing(final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeAppearing(patternName, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeAppearing(patternName, consumer);
		return this;
	}

	/**
	 * Removes the subscription of notifications of new matches for the given
	 * {@link Consumer}.
	 * 
	 * @param action the {@link Consumer} to remove
	 */
	public IBeXGTPattern<P, M> unsubscribeAppearing(final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return this;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscibeAppearing(patternName, consumer);
		return this;
	}

	/**
	 * Subscribes notifications of all disappearing matches found for the pattern.
	 * Whenever a match for this pattern disappears, the <code>accept</code> methods
	 * of all {@link Consumer}s will be called immediately.
	 * 
	 * @param action the {@link Consumer} to notify
	 */
	public IBeXGTPattern<P, M> subscribeDisappearing(final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeDisappearing(patternName, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeDisappearing(patternName, consumer);

		return this;
	}

	/**
	 * Removes the subscription of notifications of all disappearing matches for the
	 * given {@link Consumer}.
	 * 
	 * @param action the {@link Consumer} to remove
	 */
	public IBeXGTPattern<P, M> unsubscribeDisappearing(final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return this;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscibeDisappearing(patternName, consumer);

		return this;
	}

	/**
	 * Subscribe a notification when the given match disappears.
	 * 
	 * @param match  the match to observe
	 * @param action the {@link Consumer} to notify
	 */
	public IBeXGTPattern<P, M> subscribeMatchDisappears(final M match, final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscribeMatchDisappears(match, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeMatchDisappears(match, consumer);

		return this;
	}

	/**
	 * Removes the subscription of a notification when the given match disappears.
	 * 
	 * @param match  the match to observe
	 * @param action the {@link Consumer} to remove
	 */
	public IBeXGTPattern<P, M> unsubscribeMatchDisappears(final M match, final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return this;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscribeMatchDisappears(match, consumer);

		return this;
	}

	protected Collection<M> getUnfilteredMatches() {
		return matches;
	}

	protected Collection<M> getPendingMatches() {
		return pendingMatches;
	}

	protected Collection<M> getFilteredMatches() {
		return filteredMatches;
	}

	protected Collection<M> getAddedMatches() {
		return addedMatches;
	}

	protected Collection<M> getRemovedMatches() {
		return removedMatches;
	}

	@SuppressWarnings("unchecked")
	public Consumer<IBeXGTMatch<?, ?>> toIMatchConsumer(final Consumer<M> consumer) {
		return (m) -> consumer.accept((M) m);
	}

	protected void update() {
		if (pattern.getWatchDogs() != null && pattern.getWatchDogs().size() > 0) {
			match2changes.clear();
		}
	}

	protected void terminate() {
		List<Consumer<M>> subscribers = new LinkedList<>(typed2genericConsumers.keySet());
		for (Consumer<M> subscriber : subscribers) {
			unsubscribeAppearing(subscriber);
			unsubscribeDisappearing(subscriber);
		}
		if (attributeAdapter != null) {
			attributeAdapter.removeAdapter();
			attributeAdapter = null;
		}
	}

	protected void registerWatchDogs(final Collection<GTWatchDog> watchDogs) {
		if (watchDogs == null || watchDogs.size() <= 0)
			return;

		if (attributeAdapter == null) {
			attributeAdapter = new AttributeChangedAdapter(gtEngine.getModel().getResources());
		}

		for (M match : getFilteredMatches()) {
			Set<EObject> nodes = insertNodesAndMatch(match);
			for (EObject node : nodes) {
				attributeAdapter.addNodeToWatch(node);
			}
		}

		for (GTWatchDog watchDog : watchDogs) {
			attributeAdapter.addAttributeToWatch(watchDog.getAttribute());
		}

		attributeAdapter.addNotificationConsumer(this::registerAttributeChange);
		subscribeAppearing(this::registerAppearingMatchForWD);
		subscribeDisappearing(this::registerDisappearingMatchForWD);
	}

	protected void registerAppearingMatchForWD(final M match) {
		Set<EObject> nodes = insertNodesAndMatch(match);
		for (EObject node : nodes) {
			attributeAdapter.addNodeToWatch(node);
		}
	}

	protected void registerDisappearingMatchForWD(final M match) {
		Set<EObject> nodes = removeMatchAndMaybeNodes(match);
		for (EObject node : nodes) {
			attributeAdapter.removeNodeToWatch(node);
		}
		match2changes.remove(match);
	}

	protected void registerAttributeChange(final Notification notification) {
		EObject node = (EObject) notification.getNotifier();
		Set<M> matches = node2matches.get(node);

		for (M match : matches) {
			Map<EObject, AttributeHistory> node2changes = match2changes.get(match);
			if (node2changes == null) {
				node2changes = Collections.synchronizedMap(new LinkedHashMap<>());
				match2changes.put(match, node2changes);
			}

			AttributeHistory history = node2changes.get(node);
			if (history == null) {
				history = new AttributeHistory(node, (EAttribute) notification.getFeature(), notification.getOldValue(),
						notification.getNewValue());
				node2changes.put(node, history);
			} else {
				history.addNewValue(notification.getNewValue());
			}
		}
	}

}