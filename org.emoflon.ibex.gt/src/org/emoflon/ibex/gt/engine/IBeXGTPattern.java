package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.emoflon.ibex.common.operational.IPatternInterpreterProperties;
import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

public abstract class IBeXGTPattern<P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>> {
	/**
	 * The API.
	 */
	protected final IBeXGtAPI<? extends IBeXGTPatternMatcher<?, ?>, ?, ?> api;

	/**
	 * The pattern matcher.
	 */
	protected final IBeXGTPatternMatcher<?, ?> patternMatcher;

	/**
	 * The gt engine.
	 */
	protected final IBeXGTEngine<? extends IBeXGTPatternMatcher<?, ?>> gtEngine;

	/**
	 * The pattern's name.
	 */
	public final String patternName;

	/**
	 * The actual pattern.
	 */
	protected final GTPattern pattern;

	protected Collection<M> matches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> pendingMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> filteredMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> addedMatches = Collections.synchronizedSet(new LinkedHashSet<>());
	protected Collection<M> removedMatches = Collections.synchronizedSet(new LinkedHashSet<>());

	/**
	 * 
	 */
	protected Map<Consumer<M>, Consumer<IBeXGTMatch<?, ?>>> typed2genericConsumers = Collections
			.synchronizedMap(new LinkedHashMap<>());

	protected Map<String, Object> bindings = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Creates a new pattern.
	 * 
	 * @param api         the API the pattern belongs to
	 * @param interpreter the interpreter
	 * @param patternName the name of the pattern
	 */
	public IBeXGTPattern(final IBeXGtAPI<?, ?, ?> api, final GTPattern pattern) {
		this.api = api;
		this.gtEngine = api.getGTEngine();
		this.patternMatcher = api.getGTEngine().getPatternMatcher();
		this.pattern = pattern;
		this.patternName = pattern.getName();
		patternMatcher.registerTypedPattern(this);
	}

	public abstract boolean checkBindings(final M match);

	public abstract boolean checkConditions(final M match);

	public abstract boolean hasArithmeticExpressions();

	public abstract boolean hasBooleanExpressions();

	public abstract boolean hasCountExpressions();

	public abstract boolean hasParameterExpressions();

	protected abstract M createMatch(final Map<String, Object> nodes);

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
	protected abstract Collection<String> getParameterNames();

	public abstract void setParameters(final Map<String, Object> parameters);

	public Map<String, Object> getBindings() {
		return new HashMap<>(bindings);
	}

	protected void setBinding(final String name, final Object binding) {
		bindings.put(name, binding);
	}

	protected void unsetBinding(final String name) {
		bindings.remove(name);
	}

	public boolean requiresChecks(final IPatternInterpreterProperties properties) {
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

	/**
	 * Finds and returns the set of all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public Collection<M> getMatches(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return filteredMatches;
	}

	/**
	 * Finds and returns all matches for the pattern as a Stream.
	 * 
	 * @return the Stream of matches
	 */
	public Stream<M> matchStream(boolean doUpdate) {
		if (doUpdate)
			patternMatcher.updateMatches();

		return filteredMatches.stream();
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

	/**
	 * Subscribes notifications of all new matches found for the pattern. Whenever a
	 * new match for this pattern appears, the <code>accept</code> methods of all
	 * {@link Consumer}s will be called immediately.
	 * 
	 * @param action the {@link Consumer} to notify
	 */
	public void subscribeAppearing(final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeAppearing(patternName, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeAppearing(patternName, consumer);
	}

	/**
	 * Removes the subscription of notifications of new matches for the given
	 * {@link Consumer}.
	 * 
	 * @param action the {@link Consumer} to remove
	 */
	public void unsubscribeAppearing(final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscibeAppearing(patternName, consumer);
	}

	/**
	 * Subscribes notifications of all disappearing matches found for the pattern.
	 * Whenever a match for this pattern disappears, the <code>accept</code> methods
	 * of all {@link Consumer}s will be called immediately.
	 * 
	 * @param action the {@link Consumer} to notify
	 */
	public void subscribeDisappearing(final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeDisappearing(patternName, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeDisappearing(patternName, consumer);
	}

	/**
	 * Removes the subscription of notifications of all disappearing matches for the
	 * given {@link Consumer}.
	 * 
	 * @param action the {@link Consumer} to remove
	 */
	public void unsubscribeDisappearing(final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscibeDisappearing(patternName, consumer);

	}

	/**
	 * Subscribe a notification when the given match disappears.
	 * 
	 * @param match  the match to observe
	 * @param action the {@link Consumer} to notify
	 */
	public void subscribeMatchDisappears(final M match, final Consumer<M> action) {
		if (typed2genericConsumers.containsKey(action)) {
			Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscribeMatchDisappears(match, consumer);
		}

		Consumer<IBeXGTMatch<?, ?>> consumer = toIMatchConsumer(action);
		typed2genericConsumers.put(action, consumer);
		patternMatcher.subscribeMatchDisappears(match, consumer);
	}

	/**
	 * Removes the subscription of a notification when the given match disappears.
	 * 
	 * @param match  the match to observe
	 * @param action the {@link Consumer} to remove
	 */
	public void unsubscribeMatchDisappears(final M match, final Consumer<M> action) {
		if (!typed2genericConsumers.containsKey(action))
			return;

		Consumer<IBeXGTMatch<?, ?>> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscribeMatchDisappears(match, consumer);
	}

	protected Collection<M> getMatches() {
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

}
