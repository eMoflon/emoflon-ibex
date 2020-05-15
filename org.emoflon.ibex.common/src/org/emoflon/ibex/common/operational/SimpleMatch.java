package org.emoflon.ibex.common.operational;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple implementation of {@link IMatch}.
 */
public class SimpleMatch implements IMatch {
	/**
	 * The name of the pattern.
	 */
	private String patternName;
	
	protected long hash;
	protected boolean hashInit = false;

	/**
	 * The mapping between parameter names and objects.
	 */
	private final Map<String, Object> parameters;

	/**
	 * Initializes an empty match with the given pattern name.
	 * 
	 * @param patternName
	 *            the name of the pattern
	 */
	public SimpleMatch(final String patternName) {
		this.patternName = patternName;
		this.parameters = new LinkedHashMap<>();
	}

	/**
	 * Initializes the match as a copy of the given match.
	 * 
	 * @param match
	 *            the match to copy
	 */
	public SimpleMatch(final IMatch match) {
		this.patternName = match.getPatternName();
		this.parameters = new LinkedHashMap<>();
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
		return parameters.get(name);
	}

	@Override
	public void put(final String name, final Object object) {
		parameters.put(name, object);
	}

	@Override
	public Collection<String> getParameterNames() {
		return parameters.keySet();
	}
	
	@Override
	public long getHashCode() {
		if(!hashInit) {
			hash = HashUtil.collectionToHash(parameters.values());
			hashInit = true;
		}
		return hash;
	}

	@Override
	public int hashCode() {
		return (int)getHashCode();
	}

	@Override
	public boolean equals(final Object object) {
		// TODO adrianm, lfritsche: bad performance
		return object instanceof IMatch && isEqual((IMatch) object);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("IMatch for ").append(getPatternName()).append("(" + this.hashCode()+ ") {").append(System.lineSeparator());

		for (final String parameterName : getParameterNames()) {
			s.append("	").append(parameterName);
			s.append(" -> ").append(get(parameterName)).append(System.lineSeparator());
		}
		s.append("}");

		return s.toString();
	}

	public IMatch copy() {
		return new SimpleMatch(this);
	}
}
