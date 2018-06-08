package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.common.emf.EMFEdge;

public interface IMatch extends org.emoflon.ibex.common.operational.IMatch {

	IMatch copy();

	default Collection<EMFEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keep track of matched edges!");
	}
}
