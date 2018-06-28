package org.emoflon.ibex.gt.api;

import org.emoflon.ibex.common.operational.IMatch;

/**
 * This is the abstraction for all matches. Concrete implementations must have
 * typed getters and setters for all nodes in the pattern.
 *
 * @param <M>
 *            the own type
 * @param <P>
 *            the type of the pattern the match is for
 */
public abstract class GraphTransformationMatch<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> {
	/**
	 * The pattern.
	 */
	private P pattern;

	/**
	 * The untyped match.
	 */
	private IMatch match;

	/**
	 * Creates a new Match for the given pattern.
	 * 
	 * @param pattern
	 *            the pattern
	 * @param match
	 *            the untyped match
	 */
	public GraphTransformationMatch(final P pattern, final IMatch match) {
		this.pattern = pattern;
		this.match = match;
	}

	/**
	 * Returns the pattern the match is for.
	 * 
	 * @return the pattern the match is for
	 */
	public final P getPattern() {
		return pattern;
	}

	/**
	 * Returns the untyped {@link IMatch}.
	 * 
	 * @return the untyped match
	 */
	public final IMatch toIMatch() {
		return match;
	}
}
