package org.emoflon.ibex.common.operational;

import java.util.Map;
import java.util.Optional;

import IBeXLanguage.IBeXCreatePattern;

/**
 * The interface for the interpreter which applies an {@link IBeXCreatePattern}.
 */
public interface ICreatePatternInterpreter {
	/**
	 * Applies the given creations of the pattern on the given match.
	 * 
	 * @param createPattern
	 *            the pattern defining which elements are created
	 * @param match
	 *            the match
	 * @return the co-match
	 */
	Optional<IMatch> apply(IBeXCreatePattern createPattern, IMatch match, Map<String, Object> parameters);
}
