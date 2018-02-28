package org.emoflon.ibex.gt.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * This is the abstraction for all rules.
 * 
 * Concrete Implementations must have a constructor to set the parameters
 * required for rule application and getters for all parameters.
 * 
 * @param <M>
 *            the type of matches returned by this rule
 * @param <R>
 *            the own type
 */
public abstract class GraphTransformationRule<M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>> {
	/**
	 * The interpreter.
	 */
	protected GraphTransformationInterpreter interpreter;

	/**
	 * The rule name
	 */
	private String ruleName;

	/**
	 * The mapping between consumers for typed and untyped matches.
	 */
	private Map<Consumer<M>, Consumer<IMatch>> consumers = new HashMap<Consumer<M>, Consumer<IMatch>>();

	/**
	 * Creates a new rule.
	 * 
	 * @param interpreter
	 *            the interpreter
	 */
	public GraphTransformationRule(final GraphTransformationInterpreter interpreter, final String ruleName) {
		this.interpreter = interpreter;
		this.ruleName = ruleName;
	}

	/**
	 * Returns the name of the rule.
	 * 
	 * @return the rule name
	 */
	protected final String getRuleName() {
		return this.ruleName;
	}

	/**
	 * Executes the rule on the given match.
	 * 
	 * @return an {@link Optional} for the the co-match after rule application
	 */
	public final Optional<M> execute(final M match) {
		return this.interpreter.execute(match.toIMatch()).map(m -> this.convertMatch(m));
	}

	/**
	 * Executes the rule on an arbitrary match if there is a match.
	 * 
	 * @return an {@link Optional} for the the co-match after rule application
	 */
	public final Optional<M> execute() {
		Optional<M> match = this.findAnyMatch();
		if (!match.isPresent()) {
			return Optional.empty();
		}
		return this.execute(match.get());
	}

	/**
	 * Finds an arbitrary match for the rule.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public final Optional<M> findAnyMatch() {
		return this.interpreter.findAnyMatch(this.ruleName) //
				.map(m -> this.convertMatch(m));
	}

	/**
	 * Finds all matches for the rule.
	 * 
	 * @return the list of matches
	 */
	public final Collection<M> findMatches() {
		return this.interpreter.findMatches(this.ruleName).stream() //
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
	 * Unsubscribes notifications of all new matches for the given {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer}
	 */
	public final void unsubscribeAppearing(final Consumer<M> action) {
		if (this.consumers.containsKey(action)) {
			this.interpreter.unsubscibeAppearing(this.ruleName, this.consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before");
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
	 * Unsubscribes notifications of all disappearing matches for the given
	 * {@link Consumer}.
	 * 
	 * @param action
	 *            the {@link Consumer}
	 */
	public final void unsubscribeDisappearing(final Consumer<M> action) {
		if (this.consumers.containsKey(action)) {
			this.interpreter.unsubscibeDisappearing(this.ruleName, this.consumers.get(action));
		} else {
			throw new IllegalArgumentException("Cannot remove a consumer which was not registered before");
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
	public final void whenMatchDisappears(final M match, final Consumer<M> action) {
		this.interpreter.subscribeMatchDisappears(match.toIMatch(), this.convertConsumer(action));
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
