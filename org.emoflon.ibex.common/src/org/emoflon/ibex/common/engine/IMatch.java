package org.emoflon.ibex.common.engine;

import java.util.Collection;

/**
 * An interface for matches for a pattern providing a mapping between parameter names and
 * objects.
 */
public interface IMatch {

	/**
	 * Returns the name of the pattern.
	 * 
	 * @return the name of the pattern
	 */
	String getPatternName();

	/**
	 * Sets the name of the pattern
	 * 
	 * @param patternName the name of the pattern.
	 */
	default void setPatternName(String patternName) {
		throw new UnsupportedOperationException("Cannot change pattern name!");
	}

	/**
	 * Returns the object with the given name.
	 * 
	 * @param name the name to search for
	 * @return the object
	 */
	Object get(String name);

	/**
	 * Adds the mapping for the given name to the given object.
	 * 
	 * @param name   the name
	 * @param object the object
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
	 * Return all objects.
	 * 
	 * @return the objects
	 */
	Collection<Object> getObjects();

	/**
	 * Returns whether there is a parameter with the given name in the match.
	 * 
	 * @param name the name of the parameter to search for
	 * @return <code>true</code> if and only if such a parameter exists
	 */
	default boolean isInMatch(String name) {
		return getParameterNames().contains(name);
	}

	long getHashCode();

	@Override
	int hashCode();

	/**
	 * Checks whether this match is equal to the given match.
	 * 
	 * @param match the other match
	 * @return <code>true</code> if and only if the two matches are for the same pattern and
	 *         map all parameters to the same name
	 */
	default boolean isEqual(final IMatch match) {
		if (getParameterNames().size() != match.getParameterNames().size()) {
			return false;
		}

		if (getHashCode() != match.getHashCode()) {
			return false;
		}

		if (!getPatternName().equals(match.getPatternName())) {
			return false;
		}

		// Parameters of given match must exist in this match as well.
		for (final String parameterName : match.getParameterNames()) {
			try {
				if (!get(parameterName).equals(match.get(parameterName))) {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
