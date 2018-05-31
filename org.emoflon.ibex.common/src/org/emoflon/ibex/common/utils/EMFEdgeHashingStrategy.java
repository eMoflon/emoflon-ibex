package org.emoflon.ibex.common.utils;

import it.unimi.dsi.fastutil.Hash.Strategy;

public class EMFEdgeHashingStrategy implements Strategy<EMFEdge> {

	@Override
	public boolean equals(EMFEdge e1, EMFEdge e2) {
		return e1.equals(e2);
	}

	@Override
	public int hashCode(EMFEdge e) {
		return e.hashCode();
	}
}
