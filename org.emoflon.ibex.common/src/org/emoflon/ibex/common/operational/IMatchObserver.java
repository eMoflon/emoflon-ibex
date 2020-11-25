package org.emoflon.ibex.common.operational;

import java.util.Collection;

/**
 * Interface for getting notifications by the pattern matcher about new matches.
 */
public interface IMatchObserver {
	
	/**
	 * Notifies about a new match.
	 * 
	 * @param match
	 *            the match
	 */
	public void addMatch(IMatch match);

	/**
	 * Notifies about new matches.
	 * 
	 * @param match
	 *            the matches
	 */
	public void addMatches(Collection<IMatch> matches);
	
	/**
	 * Notifies about a match which is not valid anymore.
	 * 
	 * @param match
	 *            the match
	 */
	public void removeMatch(IMatch match);
	
	/**
	 * Notifies about a match which is not valid anymore.
	 * 
	 * @param match
	 *            the match
	 */
	public void removeMatches(Collection<IMatch> matches);

	/**
	 * Checks whether the pattern with the given name is relevant for the observer.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return <code>true</code> if the pattern is relevant, <code>false</code>
	 *         otherwise
	 */
	default public boolean isPatternRelevantForCompiler(String patternName) {
		return true;
	}
}
