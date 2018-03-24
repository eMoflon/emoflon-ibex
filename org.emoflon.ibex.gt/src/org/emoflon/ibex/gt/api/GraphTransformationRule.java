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
 * This is the abstraction for all rules.
 * 
 * Concrete Implementations must have a constructor and methods which set the
 * parameters required for finding matches. In addition methods for to bind
 * context and deleted nodes to a specific value must be provided.
 * 
 * @param <M>
 *            the type of matches returned by this rule
 * @param <R>
 *            the own type
 */
public abstract class GraphTransformationRule<M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>> {
	/**
	 * The API.
	 */
	protected GraphTransformationAPI api;

	/**
	 * The interpreter.
	 */
	protected GraphTransformationInterpreter interpreter;

	/**
	 * The rule name
	 */
	private String ruleName;

	/**
	 * The parameters.
	 */
	protected HashMap<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * The mapping between consumers for typed and untyped matches.
	 */
	private Map<Consumer<M>, Consumer<IMatch>> consumers = new HashMap<Consumer<M>, Consumer<IMatch>>();

	/**
	 * Creates a new rule.
	 * 
	 * @param api
	 *            the API the rule belongs to
	 * @param interpreter
	 *            the interpreter
	 * @param ruleName
	 *            the name of the rule
	 */
	public GraphTransformationRule(final GraphTransformationAPI api, final GraphTransformationInterpreter interpreter,
			final String ruleName) {
		this.api = api;
		this.interpreter = interpreter;
		this.ruleName = ruleName;
	}

	/**
	 * Returns the parameters.
	 * 
	 * @return the parameters
	 */
	public final HashMap<String, Object> getParameters() {
		return this.parameters;
	}

	/**
	 * Returns the names of the parameters which can be bound for this rule.
	 * 
	 * @return the parameter names
	 */
	protected abstract List<String> getParameterNames();

	/**
	 * Binds the parameters of the rule if there is a parameter of the same name in
	 * the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final R bind(final IMatch match) {
		this.getParameterNames().forEach(parameterName -> {
			if (match.isInMatch(parameterName)) {
				this.parameters.put(parameterName, (EObject) match.get(parameterName));
			}
		});
		return (R) this;
	}

	/**
	 * Binds the parameters of the rule if there is a parameter of the same name in
	 * the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final R bind(final GraphTransformationMatch<?, ?> match) {
		this.bind(match.toIMatch());
		return (R) this;
	}

	/**
	 * Finds an arbitrary match for the rule.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public final Optional<M> findAnyMatch() {
		return this.interpreter.findAnyMatch(this.ruleName, this.parameters) //
				.map(m -> this.convertMatch(m));
	}

	/**
	 * Finds all matches for the rule.
	 * 
	 * @return the list of matches
	 */
	public final Collection<M> findMatches() {
		return this.interpreter.findMatches(this.ruleName, this.parameters).stream() //
				.map(m -> this.convertMatch(m)) //
				.collect(Collectors.toList());
	}

	/**
	 * Finds all matches for the rule.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public final void forEachMatch(final Consumer<M> action) {
		this.findMatches().forEach(action);
	}

	/**
	 * Returns whether matches for the rule exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public final boolean hasMatches() {
		return this.findAnyMatch().isPresent();
	}

	/**
	 * Returns the number of matches found for the rule.
	 * 
	 * @return the number of matches
	 */
	public final int countMatches() {
		return this.findMatches().size();
	}

	/**
	 * Subscribes notifications of all new matches found for the rule.
	 * 
	 * @param action
	 *            the {@link Consumer} to notify
	 */
	public final void subscribeAppearing(final Consumer<M> action) {
		this.interpreter.subscribeAppearing(this.ruleName, this.convertConsumer(action));
	}

	/**
	 * Removes the subscription of notifications of all new matches for the given
	 * {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer} to remove
	 */
	public final void unsubscribeAppearing(final Consumer<M> action) {
		if (this.consumers.containsKey(action)) {
			this.interpreter.unsubscibeAppearing(this.ruleName, this.consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before!");
		}
	}

	/**
	 * Subscribes notifications of all disappearing matches found for the rule.
	 * 
	 * @param action
	 *            the {@link Consumer} to notify
	 */
	public final void subscribeDisappearing(final Consumer<M> action) {
		this.interpreter.subscribeDisappearing(this.ruleName, this.convertConsumer(action));
	}

	/**
	 * Removes the subscription of notifications of all disappearing matches for the
	 * given {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer} to remove
	 */
	public final void unsubscribeDisappearing(final Consumer<M> action) {
		if (this.consumers.containsKey(action)) {
			this.interpreter.unsubscibeDisappearing(this.ruleName, this.consumers.get(action));
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
		this.interpreter.subscribeMatchDisappears(match.toIMatch(), this.convertConsumer(action));
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
		if (this.consumers.containsKey(action)) {
			this.interpreter.unsubscribeMatchDisappears(match.toIMatch(), this.convertConsumer(action));
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
		Consumer<IMatch> consumer = new Consumer<IMatch>() {
			@Override
			public void accept(IMatch m) {
				action.accept(convertMatch(m));
			}
		};
		this.consumers.put(action, consumer);
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
