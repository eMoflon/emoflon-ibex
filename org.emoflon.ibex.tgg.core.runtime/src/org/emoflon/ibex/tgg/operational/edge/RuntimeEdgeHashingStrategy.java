package org.emoflon.ibex.tgg.operational.edge;

import gnu.trove.strategy.HashingStrategy;

public class RuntimeEdgeHashingStrategy implements HashingStrategy<RuntimeEdge>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int computeHashCode(RuntimeEdge e) {
		return 31*e.src.hashCode() + 17*e.trg.hashCode() + e.ref.hashCode();
	}

	@Override
	public boolean equals(RuntimeEdge e1, RuntimeEdge e2) {
		return e1.src == e2.src && e1.trg == e2.trg & e1.ref == e2.ref;
	}

}
