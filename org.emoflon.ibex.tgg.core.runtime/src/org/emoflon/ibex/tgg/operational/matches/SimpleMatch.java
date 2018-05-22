package org.emoflon.ibex.tgg.operational.matches;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

public class SimpleMatch extends org.emoflon.ibex.common.operational.SimpleMatch implements IMatch{
	private final Collection<RuntimeEdge> edges;
	
	public SimpleMatch(String patternName) {
		super(patternName);
		edges = new ArrayList<>();
	}
	
	public SimpleMatch(IMatch match) {
		super(match);
		edges = new ArrayList<>(match.getCreatedEdges());
	}

	@Override
	public IMatch copy() {
		SimpleMatch copy = new SimpleMatch(this);
		return copy;
	}

	@Override
	public Collection<RuntimeEdge> getCreatedEdges() {
		return edges;
	}
}
