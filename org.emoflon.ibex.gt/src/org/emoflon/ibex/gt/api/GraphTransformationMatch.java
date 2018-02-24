package org.emoflon.ibex.gt.api;

import org.emoflon.ibex.common.operational.IMatch;

/**
 * This is the abstraction for all matches.
 * 
 * Concrete implementations must have typed getters and setters for all nodes in
 * the rule which are not fixed by a parameter in the rule application.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 *
 * @param <M>
 *            the own type
 * @param <R>
 *            the type of the rule application the match is for
 */
public abstract class GraphTransformationMatch<M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>> {
	/**
	 * The rule application
	 */
	private R rule;

	/**
	 * The untyped match.
	 */
	private IMatch match;

	/**
	 * Creates a new Match for the given rule application.
	 * 
	 * @param rule
	 *            the rule application
	 * @param match
	 *            the untyped match
	 */
	public GraphTransformationMatch(final R rule, final IMatch match) {
		this.rule = rule;
		this.match = match;
	}

	/**
	 * Returns the rule application the match is for.
	 * 
	 * @return the rule application the match is for
	 */
	public R getRule() {
		return this.rule;
	}

	/**
	 * Returns the untyped {@link IMatch}.
	 * 
	 * @return the untyped match
	 */
	public IMatch toIMatch() {
		return this.match;
	}
}
