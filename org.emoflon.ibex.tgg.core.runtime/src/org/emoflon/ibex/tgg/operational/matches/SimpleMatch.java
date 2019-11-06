package org.emoflon.ibex.tgg.operational.matches;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;

public class SimpleMatch extends org.emoflon.ibex.common.operational.SimpleMatch implements ITGGMatch {
	private final Collection<EMFEdge> edges;

	public SimpleMatch(String patternName) {
		super(patternName);
		edges = new ArrayList<>();
	}

	public SimpleMatch(ITGGMatch match) {
		super(match);
		edges = new ArrayList<>(match.getCreatedEdges());
	}

	@Override
	public ITGGMatch copy() {
		SimpleMatch copy = new SimpleMatch(this);
		return copy;
	}

	@Override
	public Collection<EMFEdge> getCreatedEdges() {
		return edges;
	}
}
