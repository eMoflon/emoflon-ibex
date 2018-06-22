package org.emoflon.ibex.gt.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * This is the abstraction for all patterns.
 * 
 * Concrete Implementations must have a constructor and methods which set the
 * parameters required for finding matches. In addition methods for to bind
 * context and deleted nodes to a specific value must be provided.
 * 
 * @param <M>
 *            the type of matches returned by this pattern
 * @param <P>
 *            the own type
 */
public abstract class GraphTransformationPattern<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> {
	/**
	 * The API.
	 */
	protected GraphTransformationAPI api;

	/**
	 * The interpreter.
	 */
	protected GraphTransformationInterpreter interpreter;

	/**
	 * The pattern name
	 */
	private String patternName;

	/**
	 * The parameters.
	 */
	protected Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * The mapping between consumers for typed and untyped consumers.
	 */
	private Map<Consumer<M>, Consumer<IMatch>> consumers = new HashMap<Consumer<M>, Consumer<IMatch>>();

	/**
	 * Creates a new pattern.
	 * 
	 * @param api
	 *            the API the pattern belongs to
	 * @param interpreter
	 *            the interpreter
	 * @param patternName
	 *            the name of the pattern
	 */
	public GraphTransformationPattern(final GraphTransformationAPI api,
			final GraphTransformationInterpreter interpreter, final String patternName) {
		this.api = api;
		this.interpreter = interpreter;
		this.patternName = patternName;
	}

	/**
	 * Returns the parameters.
	 * 
	 * @return the parameters
	 */
	public final Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * Returns the names of the parameters which can be bound for this pattern.
	 * 
	 * @return the parameter names
	 */
	protected abstract List<String> getParameterNames();

	/**
	 * Binds the parameters of the pattern if there is a parameter of the same name
	 * in the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final P bind(final IMatch match) {
		getParameterNames().forEach(parameterName -> {
			if (match.isInMatch(parameterName)) {
				getParameters().put(parameterName, (EObject) match.get(parameterName));
			}
		});
		return (P) this;
	}

	/**
	 * Binds the parameters of the pattern if there is a parameter of the same name
	 * in the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final P bind(final GraphTransformationMatch<?, ?> match) {
		bind(match.toIMatch());
		return (P) this;
	}

	/**
	 * Finds and returns an arbitrary match for the pattern if a match exists.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public final Optional<M> findAnyMatch() {
		return interpreter.matchStream(patternName, getParameters()) //
				.findAny() //
				.map(m -> convertMatch(m));
	}

	/**
	 * Finds and returns all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public final Collection<M> findMatches() {
		return interpreter.matchStream(patternName, getParameters()) //
				.map(m -> convertMatch(m)) //
				.collect(Collectors.toList());
	}

	/**
	 * Executes the <code>accept</code> of the given {@link Consumer} for all
	 * matches found for the pattern.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public final void forEachMatch(final Consumer<M> action) {
		findMatches().forEach(action);
	}

	/**
	 * Returns whether any matches for the pattern exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public final boolean hasMatches() {
		return findAnyMatch().isPresent();
	}

	/**
	 * Returns the number of matches found for the pattern.
	 * 
	 * @return the number of matches
	 */
	public final long countMatches() {
		return interpreter.matchStream(patternName, getParameters()).count();
	}

	/**
	 * Subscribes notifications of all new matches found for the pattern. Whenever a
	 * new match for this pattern appears, the <code>accept</code> methods of all
	 * {@link Consumer}s will be called immediately.
	 * 
	 * @param action
	 *            the {@link Consumer} to notify
	 */
	public final void subscribeAppearing(final Consumer<M> action) {
		interpreter.subscribeAppearing(patternName, convertConsumer(action));
	}

	/**
	 * Removes the subscription of notifications of new matches for the given
	 * {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer} to remove
	 */
	public final void unsubscribeAppearing(final Consumer<M> action) {
		if (consumers.containsKey(action)) {
			interpreter.unsubscibeAppearing(patternName, consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before!");
		}
	}

	/**
	 * Subscribes notifications of all disappearing matches found for the pattern.
	 * Whenever a match for this pattern disappears, the <code>accept</code> methods
	 * of all {@link Consumer}s will be called immediately.
	 * 
	 * @param action
	 *            the {@link Consumer} to notify
	 */
	public final void subscribeDisappearing(final Consumer<M> action) {
		interpreter.subscribeDisappearing(patternName, convertConsumer(action));
	}

	/**
	 * Removes the subscription of notifications of all disappearing matches for the
	 * given {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer} to remove
	 */
	public final void unsubscribeDisappearing(final Consumer<M> action) {
		if (consumers.containsKey(action)) {
			interpreter.unsubscibeDisappearing(patternName, consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before!");
		}
	}

	/**
	 * Subscribe a notification when the given match disappears.
	 * 
	 * @param match
	 *            the match to observe
	 * @param action
	 *            the {@link Consumer} to notify
	 */
	public final void subscribeMatchDisappears(final M match, final Consumer<M> action) {
		interpreter.subscribeMatchDisappears(match.toIMatch(), convertConsumer(action));
	}

	/**
	 * Removes the subscription of a notification when the given match disappears.
	 * 
	 * @param match
	 *            the match to observe
	 * @param action
	 *            the {@link Consumer} to remove
	 */
	public final void unsubscribeMatchDisappears(final M match, final Consumer<M> action) {
		if (consumers.containsKey(action)) {
			interpreter.unsubscribeMatchDisappears(match.toIMatch(), consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before!");
		}
	}

	/**
	 * Converts the consumer of a typed match to a consumer of an untyped
	 * {@link IMatch} which converts the match and calls the consumer of the typed
	 * match afterwards.
	 * 
	 * @param action
	 *            the {@link Consumer} of a typed match
	 * @return the {@link IMatch} Consumer
	 */
	private Consumer<IMatch> convertConsumer(final Consumer<M> action) {
		Consumer<IMatch> consumer = m -> action.accept(convertMatch(m));
		consumers.put(action, consumer);
		return consumer;
	}

	/**
	 * Convert the untyped to a typed match.
	 * 
	 * @param match
	 *            the untyped match
	 * @return the typed match
	 */
	protected abstract M convertMatch(final IMatch match);
}
