package org.emoflon.ibex.gt.engine;

import IBeXLanguage.IBeXPattern;

/**
 * Engine for Unidirectional Graph Transformations which needs to be implemented
 * for a concrete pattern matcher.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GTEngine {
	/**
	 * Adds the IBeXPattern to the patterns of the engine.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add
	 */
	public abstract void createPattern(final IBeXPattern ibexPattern);
}
