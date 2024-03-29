package org.emoflon.ibex.gt.api;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;


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
	protected Map<String, Object> parameters = new LinkedHashMap<String, Object>();

	/**
	 * The mapping between consumers for typed and untyped consumers.
	 */
	private Map<Consumer<M>, Consumer<IMatch>> consumers = new LinkedHashMap<Consumer<M>, Consumer<IMatch>>();

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
		interpreter.registerGraphTransformationPattern(patternName, this);
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
	 * Unbinds the parameters of the pattern if there is a parameter of the same name
	 * in the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final P unbind(final IMatch match) {
		getParameterNames().forEach(parameterName -> {
			if (match.isInMatch(parameterName)) {
				getParameters().remove(parameterName);
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
	 * Unbinds the parameters of the pattern if there is a parameter of the same name
	 * in the match.
	 * 
	 * @param match
	 *            the match
	 */
	@SuppressWarnings("unchecked")
	public final P unbind(final GraphTransformationMatch<?, ?> match) {
		unbind(match.toIMatch());
		return (P) this;
	}

	/**
	 * Finds and returns an arbitrary match for the pattern if a match exists.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public final Optional<M> findAnyMatch(boolean doUpdate) {
		return interpreter.findAnyMatch(patternName, getParameters(), doUpdate).map(m -> convertMatch(m));
	}
	
	/**
	 * Finds and returns an arbitrary match for the pattern if a match exists.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public final Optional<M> findAnyMatch() {
		return interpreter.findAnyMatch(patternName, getParameters(), true).map(m -> convertMatch(m));
	}

	/**
	 * Finds and returns all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public final Collection<M> findMatches(boolean doUpdate) {
		return matchStream(doUpdate).collect(Collectors.toSet());
	}
	
	/**
	 * Finds and returns all matches for the pattern.
	 * 
	 * @return the list of matches (can be empty if no matches exist)
	 */
	public final Collection<M> findMatches() {
		return matchStream(true).collect(Collectors.toSet());
	}
	
	/**
	 * Finds and returns all untyped matches for the pattern.
	 * 
	 * @return the Stream of matches
	 */
	protected Stream<IMatch> untypedMatchStream(boolean doUpdate){
		return interpreter.matchStream(patternName, getParameters(), doUpdate);
	}
	
	public boolean isMatchValid(IMatch match) {
		return true;
	}
	
	public boolean containsArithmeticExpressions() {
		return false;
	}
	
	public boolean containsCountExpressions() {
		return false;
	}
	
	/**
	 * Finds and returns all matches for the pattern as a Stream.
	 * 
	 * @return the Stream of matches
	 */
	public Stream<M> matchStream(boolean doUpdate) {
		return untypedMatchStream(doUpdate) //
				.map(m -> convertMatch(m));
	}
	
	/**
	 * Finds and returns all matches for the pattern as a Stream.
	 * 
	 * @return the Stream of matches
	 */
	public Stream<M> matchStream() {
		return untypedMatchStream(true) //
				.map(m -> convertMatch(m));
	}

	/**
	 * Executes the <code>accept</code> of the given {@link Consumer} for all
	 * matches found for the pattern.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public final void forEachMatch(boolean doUpdate, final Consumer<M> action) {
		matchStream(doUpdate).forEach(action);
	}
	
	/**
	 * Executes the <code>accept</code> of the given {@link Consumer} for all
	 * matches found for the pattern.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public final void forEachMatch(final Consumer<M> action) {
		matchStream(true).forEach(action);
	}

	/**
	 * Returns whether any matches for the pattern exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public final boolean hasMatches(boolean doUpdate) {
		return findAnyMatch(doUpdate).isPresent();
	}
	
	/**
	 * Returns whether any matches for the pattern exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public final boolean hasMatches() {
		return findAnyMatch(true).isPresent();
	}

	/**
	 * Returns the number of matches found for the pattern.
	 * 
	 * @return the number of matches
	 */
	public final long countMatches(boolean doUpdate) {
		return interpreter.countMatches(patternName, getParameters(), doUpdate);
	}
	
	/**
	 * Returns the number of matches found for the pattern.
	 * 
	 * @return the number of matches
	 */
	public final long countMatches() {
		return interpreter.countMatches(patternName, getParameters(), true);
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
	
	public IBeXPatternSet getPatternSet() {
		return interpreter.getPatternSet();
	}
	
	public boolean isEmptyPattern() {
		return interpreter.getPatternSet().getContextPatterns().stream()
				.filter(pattern -> pattern.getName().equals(patternName))
				.filter(pattern -> IBeXPatternUtils.isEmptyPattern(pattern))
				.findFirst()
				.isPresent();
	}

	/**
	 * Convert the untyped to a typed match.
	 * 
	 * @param match
	 *            the untyped match
	 * @return the typed match
	 */
	public abstract M convertMatch(final IMatch match);
	
	public String getPatternName() {
		return patternName;
	}
	
}
