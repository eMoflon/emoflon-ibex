package org.emoflon.ibex.tgg.operational.matches;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;

public class SimpleMatch extends org.emoflon.ibex.common.operational.SimpleMatch implements ITGGMatch {
	private final Collection<EMFEdge> edges;
	private PatternType type;

	public SimpleMatch(String patternName) {
		super(patternName);
		this.type = PatternUtil.resolve(patternName);
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
	
	@Override
	public PatternType getType() {
		return type;
	}
}
