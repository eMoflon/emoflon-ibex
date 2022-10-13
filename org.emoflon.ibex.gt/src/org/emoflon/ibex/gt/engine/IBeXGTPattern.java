package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;

public abstract class IBeXGTPattern<P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>> {
	/**
	 * The API.
	 */
	protected final IBeXGtAPI<? extends IBeXGTPatternMatcher<?, ?>> api;

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

	/**
	 * The map containing potential node bindings.
	 */
	protected Map<String, EObject> bindings = Collections.synchronizedMap(new LinkedHashMap<>());

	/**
	 * 
	 */
	protected Map<Consumer<M>, Consumer<IMatch>> typed2genericConsumers = Collections
			.synchronizedMap(new LinkedHashMap<>());

	/**
	 * Creates a new pattern.
	 * 
	 * @param api         the API the pattern belongs to
	 * @param interpreter the interpreter
	 * @param patternName the name of the pattern
	 */
	public IBeXGTPattern(final IBeXGtAPI<? extends IBeXGTPatternMatcher<?, ?>> api, final GTPattern pattern) {
		this.api = api;
		this.gtEngine = api.getGTEngine();
		this.patternMatcher = api.getGTEngine().getPatternMatcher();
		this.pattern = pattern;
		this.patternName = pattern.getName();
		api.getGTEngine().getPatternMatcher().registerTypedPattern(this);
	}

	/**
	 * Returns the names of the parameters which can be bound for this pattern.
	 * 
	 * @return the parameter names
	 */
	protected abstract Collection<String> getParameterNames();

	protected abstract boolean checkConditions(M match);

	public abstract boolean hasArithmeticExpressions();

	public abstract boolean hasCountExpressions();

	public boolean isEmptyPattern() {
		return pattern.isEmpty();
	}

	/**
	 * Returns the parameters.
	 * 
	 * @return the parameters
	 */
	public abstract Map<String, Object> getParameters();

	/**
	 * Finds and returns an arbitrary match for the pattern if a match exists.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public Optional<M> findAnyMatch(boolean doUpdate) {
		return patternMatcher.<M>getMatchStream(patternName, doUpdate).findAny();
	}

	/**
	 * Finds and returns a list of all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public List<M> getMatches(boolean doUpdate) {
		return patternMatcher.<M>getMatchStream(patternName, doUpdate).collect(Collectors.toList());
	}

	/**
	 * Finds and returns the set of all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public Set<M> getMatchSet(boolean doUpdate) {
		return patternMatcher.<M>getMatchStream(patternName, doUpdate).collect(Collectors.toSet());
	}

	/**
	 * Finds and returns all matches for the pattern as a Stream.
	 * 
	 * @return the Stream of matches
	 */
	public Stream<M> matchStream(boolean doUpdate) {
		return patternMatcher.<M>getMatchStream(patternName, doUpdate);
	}

	/**
	 * Returns whether any matches for the pattern exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public boolean hasMatches(boolean doUpdate) {
		if (doUpdate) {
			return patternMatcher.getMatchStream(patternName, doUpdate).findAny().isPresent();
		} else {
			return !patternMatcher.getFilteredMatches(patternName, doUpdate).isEmpty();
		}
	}

	/**
	 * Returns the number of matches found for the pattern.
	 * 
	 * @return the number of matches
	 */
	public long countMatches(boolean doUpdate) {
		if (doUpdate) {
			return patternMatcher.getMatchStream(patternName, doUpdate).count();
		} else {
			return patternMatcher.getFilteredMatches(patternName, doUpdate).size();
		}
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
			Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeAppearing(patternName, consumer);
		}

		Consumer<IMatch> consumer = toIMatchConsumer(action);
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

		Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
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
			Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscibeDisappearing(patternName, consumer);
		}

		Consumer<IMatch> consumer = toIMatchConsumer(action);
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

		Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
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
			Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
			patternMatcher.unsubscribeMatchDisappears(match, consumer);
		}

		Consumer<IMatch> consumer = toIMatchConsumer(action);
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

		Consumer<IMatch> consumer = typed2genericConsumers.remove(action);
		patternMatcher.unsubscribeMatchDisappears(match, consumer);
	}

	@SuppressWarnings("unchecked")
	public Consumer<IMatch> toIMatchConsumer(final Consumer<M> consumer) {
		return (m) -> consumer.accept((M) m);
	}

}
