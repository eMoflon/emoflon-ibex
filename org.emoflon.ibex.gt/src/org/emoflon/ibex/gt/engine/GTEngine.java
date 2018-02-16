package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import IBeXLanguage.IBeXPatternSet;

/**
 * Engine for unidirectional graph transformations which needs to be implemented
 * for a concrete pattern matcher.
 */
public abstract class GTEngine {
	protected Optional<String> debugPath = Optional.empty();

	/**
	 * Sets the debug path.
	 * 
	 * @param debugPath
	 *            the path for the debugging output. If it is <code>null</null>,
	 *            debugging is disabled.
	 * 
	 */
	public void setDebugPath(final String debugPath) {
		this.debugPath = Optional.ofNullable(debugPath);
	}

	/**
	 * Transforms the IBeXPattern into the patterns of the engine.
	 * 
	 * @param ibexPatternSet
	 *            the IBeXPatternSet to transform
	 */
	public abstract void transformPatterns(final IBeXPatternSet ibexPatternSet);
}
