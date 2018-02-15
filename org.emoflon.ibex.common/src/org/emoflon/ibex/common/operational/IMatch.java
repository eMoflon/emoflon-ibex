package org.emoflon.ibex.common.operational;

import java.util.Collection;

/**
 * An interface for matches for a pattern providing a mapping between parameter
 * names and objects.
 */
public interface IMatch {
	/**
	 * Returns the object with the given name.
	 * 
	 * @param name
	 *            the name to search for
	 * @return the object
	 */
	Object get(String name);

	/**
	 * Adds the mapping for the given name to the given object.
	 * 
	 * @param name
	 *            the name
	 * @param object
	 *            the object
	 */
	default void put(String name, Object object) {
		throw new UnsupportedOperationException("This match does not support adding new mappings!");
	}

	/**
	 * Return all parameter names.
	 * 
	 * @return the parameter names
	 */
	Collection<String> getParameterNames();

	/**
	 * Returns the name of the pattern.
	 * 
	 * @return the name of the pattern
	 */
	String getPatternName();

	/**
	 * Returns whether there is a parameter with the given name in the match.
	 * 
	 * @param name
	 *            the name of the paramete to search for
	 * @return <code>true</code> if and only if such a parameter exists
	 */
	default boolean isInMatch(String name) {
		return getParameterNames().contains(name);
	}
}
