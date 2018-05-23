package org.emoflon.ibex.tgg.operational.edge;

import org.emoflon.ibex.common.utils.EMFEdge;

import it.unimi.dsi.fastutil.Hash.Strategy;

public class RuntimeEdgeHashingStrategy implements Strategy<EMFEdge> {

	@Override
	public boolean equals(EMFEdge e1, EMFEdge e2) {
		if (e1 == e2) {
			return true;
		}
		if (e1 == null || e2 == null) {
			return false;
		}
		return e1.getSource() == e2.getSource() && e1.getTarget() == e2.getTarget() & e1.getType() == e2.getType();
	}

	@Override
	public int hashCode(EMFEdge e) {
		return 31 * e.getSource().hashCode() + 17 * e.getTarget().hashCode() + e.getType().hashCode();
	}

}
