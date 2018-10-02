package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public interface IMatch extends org.emoflon.ibex.common.operational.IMatch {

	IMatch copy();

	default Collection<EMFEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keep track of matched edges!");
	}
	
	default String getRuleName() {
		return PatternSuffixes.removeSuffix(getPatternName());
	}
}
