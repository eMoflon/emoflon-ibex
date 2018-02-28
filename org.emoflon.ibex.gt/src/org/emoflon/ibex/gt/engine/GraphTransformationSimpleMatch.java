package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.HashMap;

import org.emoflon.ibex.common.operational.IMatch;

/**
 * A simple implementation of {@link IMatch}.
 */
public class GraphTransformationSimpleMatch implements IMatch {
	/**
	 * The name of the pattern.
	 */
	private String patternName;

	/**
	 * The mapping between parameter names and objects.
	 */
	private HashMap<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * Initializes a match with the given pattern name.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 */
	public GraphTransformationSimpleMatch(final String patternName) {
		this.patternName = patternName;
	}

	/**
	 * Initializes the match as a copy of the given match.
	 * 
	 * @param match
	 *            the match to copy
	 */
	public GraphTransformationSimpleMatch(final IMatch match) {
		this.patternName = match.getPatternName();
		match.getParameterNames().forEach(parameterName -> {
			this.parameters.put(parameterName, match.get(parameterName));
		});
	}

	@Override
	public Object get(final String name) {
		if (!this.parameters.containsKey(name)) {
			throw new IllegalArgumentException(String.format("No parameter %s.", name));
		}
		return this.parameters.get(name);
	}

	@Override
	public Collection<String> getParameterNames() {
		return this.parameters.keySet();
	}

	@Override
	public String getPatternName() {
		return this.patternName;
	}

	@Override
	public void put(final String name, final Object object) {
		this.parameters.put(name, object);
	}
}
