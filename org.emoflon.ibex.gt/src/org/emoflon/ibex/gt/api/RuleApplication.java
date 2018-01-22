package org.emoflon.ibex.gt.api;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

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
public abstract class RuleApplication<M extends Match<M, R>, R extends RuleApplication<M, R>> {
	/**
	 * The model to query/transform with this rule application.
	 */
	private File model;

	/**
	 * Creates a new rule application for the given model.
	 * 
	 * @param model
	 *            the model to query/transform
	 */
	public RuleApplication(final File model) {
		this.model = model;
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model to query/transform
	 */
	public File getModel() {
		return model;
	}

	/**
	 * Executes the rule application on an arbitrary match.
	 * 
	 * @return an {@link Optional} for the match the rule was executed on
	 */
	public abstract Optional<M> execute();

	/**
	 * Executes the rule application on all matches.
	 * 
	 * @return the list of matches the rule was executed on
	 */
	public abstract Collection<M> executeAll();

	/**
	 * Executes the rule application on all matches.
	 * 
	 * @param action
	 *            a Consumer for the matches
	 */
	public void forEachExecution(final Consumer<M> action) {
		this.executeAll().forEach(action);
	}

	/**
	 * Finds an arbitrary match for the rule application.
	 * 
	 * @return an {@link Optional} for the match
	 */
	public abstract Optional<M> match();

	/**
	 * Finds all matches for the rule application.
	 * 
	 * @return the list of matches
	 */
	public abstract Collection<M> matchAll();

	/**
	 * Finds all matches for the rule application.
	 * 
	 * @param action
	 *            a Consumer for the matches found
	 */
	public void forEachMatch(final Consumer<M> action) {
		this.matchAll().forEach(action);
	}

	/**
	 * Returns the number of matches found for the rule application.
	 * 
	 * @return the number of matches
	 */
	public int countMatches() {
		return this.matchAll().size();
	}

	/**
	 * Registers a Consumer for any matches which may be found in the future.
	 * 
	 * @param action
	 *            the Consumer for the matches
	 */
	public abstract void notifyIfMatched(final Consumer<M> action);

	/**
	 * Registers a Consumer to notify if there are no matches found.
	 * 
	 * @param action
	 *            the Consumer
	 */
	public abstract void notifyIfNotMatchingAnymore(final Consumer<M> action);

	/**
	 * Returns whether the rule application is a query (i. e. only context to match)
	 * or a transformation.
	 * 
	 * @return <code>true</code> if the rule is a query, otherwise
	 *         <code>false</code>.
	 */
	public abstract boolean isQuery();
}
