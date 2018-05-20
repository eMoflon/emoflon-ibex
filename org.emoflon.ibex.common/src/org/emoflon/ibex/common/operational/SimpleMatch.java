package org.emoflon.ibex.common.operational;

import java.util.Collection;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

/**
 * A simple implementation of {@link IMatch}.
 */
public class SimpleMatch implements IMatch {
	/**
	 * The name of the pattern.
	 */
	private String patternName;

	/**
	 * The mapping between parameter names and objects.
	 */
	private final Object2ObjectLinkedOpenHashMap<String, Object> parameters;

	/**
	 * Initializes an empty match with the given pattern name.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 */
	public SimpleMatch(final String patternName) {
		this.patternName = patternName;
		this.parameters = new Object2ObjectLinkedOpenHashMap<String, Object>();
	}

	/**
	 * Initializes the match as a copy of the given match.
	 * 
	 * @param match
	 *            the match to copy
	 */
	public SimpleMatch(final IMatch match) {
		this.patternName = match.getPatternName();
		this.parameters = new Object2ObjectLinkedOpenHashMap<String, Object>((int) (match.getParameterNames().size() * Object2ObjectLinkedOpenHashMap.DEFAULT_LOAD_FACTOR)); 
		match.getParameterNames().forEach(parameterName -> {
			this.parameters.put(parameterName, match.get(parameterName));
		});
	}

	@Override
	public String getPatternName() {
		return this.patternName;
	}

	@Override
	public void setPatternName(final String patternName) {
		this.patternName = patternName;
	}

	@Override
	public Object get(final String name) {
		return this.parameters.get(name);
	}

	@Override
	public void put(final String name, final Object object) {
		this.parameters.put(name, object);
	}

	@Override
	public Collection<String> getParameterNames() {
		return this.parameters.keySet();
	}

	@Override
	public int hashCode() {
		return parameters.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) {
			return false;
		}
		if (object instanceof IMatch) {
			return isEqual((IMatch) object);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("IMatch for ").append(getPatternName()).append(" {").append(System.lineSeparator());

		for (final String parameterName : getParameterNames()) {
			s.append("	").append(parameterName);
			s.append(" -> ").append(get(parameterName)).append(System.lineSeparator());
		}
		s.append("}");

		return s.toString();
	}
	
	public IMatch copy() {
		SimpleMatch copy = new SimpleMatch(this);
		return copy;
	}
}
