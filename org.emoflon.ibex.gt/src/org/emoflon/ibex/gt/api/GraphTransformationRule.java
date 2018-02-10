package org.emoflon.ibex.gt.api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * This is the abstraction for all rule applications.
 * 
 * Concrete Implementations must have a constructor to set the parameters
 * required for rule application and getters for all parameters.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 * 
 * @param <M>
 *            the type of matches returned by this rule
 * @param <R>
 *            the own type
 */
public abstract class GraphTransformationRule<M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>> {
	/**
	 * The model to query/transform with this rule application.
	 */
	private ResourceSet model;

	/**
	 * Creates a new rule application for the given model.
	 * 
	 * @param model
	 *            the model to query/transform
	 */
	public GraphTransformationRule(final ResourceSet model) {
		this.model = model;
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model to query/transform
	 */
	public final ResourceSet getModel() {
		return model;
	}

	/**
	 * Executes the rule application on the given match.
	 */
	public abstract void execute(final M match);

	/**
	 * Executes the rule application on an arbitrary match.
	 * 
	 * @return an {@link Optional} for the match the rule was executed on
	 */
	public final Optional<M> execute() {
		Optional<M> anyMatch = this.findAnyMatch();
		anyMatch.ifPresent(match -> this.execute(match));
		return anyMatch;
	}

	/**
	 * Executes the rule application on all matches.
	 * 
	 * @return the list of matches the rule was executed on
	 */
	public final Collection<M> executeAll() {
		Collection<M> matches = this.findMatches();
		matches.forEach(match -> this.execute(match));
		return matches;
	}

	/**
	 * Finds an arbitrary match for the rule application.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public abstract Optional<M> findAnyMatch();

	/**
	 * Finds all matches for the rule application.
	 * 
	 * @return the list of matches
	 */
	public abstract Collection<M> findMatches();

	/**
	 * Finds all matches for the rule application.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public final void forEachMatch(final Consumer<M> action) {
		this.findMatches().forEach(action);
	}

	/**
	 * Returns whether matches for the rule application exist.
	 * 
	 * @return <code>true</code> if and only if there is at least one match
	 */
	public final boolean hasMatches() {
		return this.findAnyMatch().isPresent();
	}

	/**
	 * Returns the number of matches found for the rule application.
	 * 
	 * @return the number of matches
	 */
	public final int countMatches() {
		return this.findMatches().size();
	}

	/**
	 * Returns whether the rule application is a query (i. e. only context to match)
	 * or a transformation.
	 * 
	 * @return <code>true</code> if the rule is a query, otherwise
	 *         <code>false</code>.
	 */
	public abstract boolean isQuery();
}
