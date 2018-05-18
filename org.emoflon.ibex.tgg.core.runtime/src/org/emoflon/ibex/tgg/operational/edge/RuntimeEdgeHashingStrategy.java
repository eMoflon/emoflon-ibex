package org.emoflon.ibex.tgg.operational.edge;

import it.unimi.dsi.fastutil.Hash.Strategy;

public class RuntimeEdgeHashingStrategy implements Strategy<RuntimeEdge>{

	@Override
	public boolean equals(RuntimeEdge e1, RuntimeEdge e2) {
		if(e1 == e2) {
			return true;
		}
		if(e1 == null || e2 == null) {
			return false;
		}
		return e1.src == e2.src && e1.trg == e2.trg & e1.ref == e2.ref;
	}

	@Override
	public int hashCode(RuntimeEdge e) {
		return 31*e.src.hashCode() + 17*e.trg.hashCode() + e.ref.hashCode();
	}

}
