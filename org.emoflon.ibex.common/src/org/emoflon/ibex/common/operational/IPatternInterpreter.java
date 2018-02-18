package org.emoflon.ibex.common.operational;

import IBeXLanguage.IBeXPatternSet;

/**
 * Interface for a pattern matcher.
 */
public interface IPatternInterpreter {
	/**
	 * Sets the debug path.
	 * 
	 * @param debugPath
	 *            the path for the debugging output. If it is <code>null</null>,
	 *            debugging is disabled.
	 */
	public void setDebugPath(final String debugPath);

	/**
	 * Initializes the patterns of the engine with the given IBeXPatterns, which
	 * need to be transformed into the patterns of the concrete pattern matcher.
	 * 
	 * @param ibexPatternSet
	 *            the IBeXPatternSet to transform
	 */
	public void initPatterns(final IBeXPatternSet ibexPatternSet);
}
