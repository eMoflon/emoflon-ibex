package org.emoflon.ibex.tgg.runtime.matches;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.engine.SimpleMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.PatternUtil;

public class SimpleTGGMatch extends SimpleMatch implements ITGGMatch {
	private final Collection<EMFEdge> edges;
	private PatternType type;

	public SimpleTGGMatch(String patternName) {
		super(patternName);
		this.type = PatternUtil.resolve(patternName);
		edges = new ArrayList<>();
	}

	public SimpleTGGMatch(ITGGMatch match) {
		super(match);
		edges = new ArrayList<>(match.getCreatedEdges());
	}

	@Override
	public ITGGMatch copy() {
		SimpleTGGMatch copy = new SimpleTGGMatch(this);
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
