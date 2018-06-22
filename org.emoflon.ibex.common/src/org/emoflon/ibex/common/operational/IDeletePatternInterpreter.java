package org.emoflon.ibex.common.operational;

import java.util.Optional;

import IBeXLanguage.IBeXDeletePattern;

/**
 * The interface for the interpreter which applies an {@link IBeXDeletePattern}.
 */
public interface IDeletePatternInterpreter {

	/**
	 * Applies the deletions of the pattern on the given match.
	 * 
	 * @param deletePattern
	 *            the pattern defining which elements are deleted
	 * @param match
	 *            the match
	 * @param po
	 *            the pushout semantics (DPO or SPO)
	 * @return the match after the pattern application
	 */
	Optional<IMatch> apply(IBeXDeletePattern deletePattern, IMatch match, PushoutApproach po);
}
