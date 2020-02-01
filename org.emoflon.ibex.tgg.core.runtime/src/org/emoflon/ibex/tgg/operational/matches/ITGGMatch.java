package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.compiler.patterns.PatternUtil;

public interface ITGGMatch extends IMatch {

	ITGGMatch copy();

	default Collection<EMFEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keeping track of matched edges!");
	}
	
	default String getRuleName() {
		return PatternUtil.getRuleName(getPatternName());
	}

	PatternType getType();
}
