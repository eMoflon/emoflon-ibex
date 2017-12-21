package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;

public interface IMatch {

	Object get(String name);

	Collection<String> parameterNames();

	String patternName();

	default boolean isInMatch(String name) {
		return parameterNames().contains(name);
	}
}
