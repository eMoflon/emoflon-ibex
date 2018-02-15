package org.emoflon.ibex.common.operational;

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
	 * Notifies about a match which is not valid anymore.
	 * 
	 * @param match
	 *            the match
	 */
	public void removeMatch(IMatch match);

	/**
	 * Checks whether the pattern with the given name is relevant for the observer.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 * @return <code>true</code> if the pattern is relevant, <code>false</code>
	 *         otherwise
	 */
	public boolean isPatternRelevantForCompiler(String patternName);
}
