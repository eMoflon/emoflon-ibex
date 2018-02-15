package org.emoflon.ibex.tgg.operational.matches;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.edge.RuntimeEdge;

public interface IMatch extends org.emoflon.ibex.common.operational.IMatch {

	IMatch copy();

	default Collection<RuntimeEdge> getCreatedEdges() {
		throw new UnsupportedOperationException("This match does not support keep track of matched edges!");
	}
}
