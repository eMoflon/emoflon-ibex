package org.emoflon.ibex.common.operational;

import java.util.Collection;

public interface IMatch {
	Object get(String name);
	
	default void put(String name, Object o) {
		throw new UnsupportedOperationException("This match does not support adding new mappings!");
	}

	Collection<String> parameterNames();

	String patternName();

	default boolean isInMatch(String name) {
		return parameterNames().contains(name);
	}
}
