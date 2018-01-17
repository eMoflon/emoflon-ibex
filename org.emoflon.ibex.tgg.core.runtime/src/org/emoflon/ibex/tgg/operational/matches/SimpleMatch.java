package org.emoflon.ibex.tgg.operational.matches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

public class SimpleMatch implements IMatch {
	private HashMap<String, Object> mappings;
	private Collection<RuntimeEdge> edges;
	
	private String patternName;
	
	public SimpleMatch(String patternName) {
		this.patternName = patternName;
		mappings = new HashMap<>();
		edges = new ArrayList<>();
	}

	@Override
	public Object get(String name) {
		if(!mappings.containsKey(name))
			throw new IllegalArgumentException(name + " is not in this match!");
		return mappings.get(name);
	}

	@Override
	public Collection<String> parameterNames() {
		return mappings.keySet();
	}
	
	@Override
	public String patternName() {
		return patternName;
	}

	@Override
	public void put(String name, Object o) {
		mappings.put(name, o);
	}

	@Override
	public IMatch copy() {
		SimpleMatch copy = new SimpleMatch(patternName);
		copy.mappings.putAll(this.mappings);
		copy.edges.addAll(edges);
		return copy;
	}

	@Override
	public Collection<RuntimeEdge> getCreatedEdges() {
		return edges;
	}
}
