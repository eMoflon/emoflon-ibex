package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

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

	IMatch copy();

	default Collection<RuntimeEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keep track of matched edges!");
	}
}
