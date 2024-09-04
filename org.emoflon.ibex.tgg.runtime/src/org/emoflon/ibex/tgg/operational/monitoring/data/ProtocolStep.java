package org.emoflon.ibex.tgg.runtime.monitoring.data;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class ProtocolStep {
	private int index;
	private TGGObjectGraph objectGraph;
	private TGGRule appliedRule;

	public ProtocolStep(int pIndex, TGGObjectGraph pObjectGraph, TGGRule appliedRule) {
		index = pIndex;
		objectGraph = pObjectGraph;
		this.appliedRule = appliedRule;
	}

	public int getIndex() {
		return index;
	}

	public TGGObjectGraph getObjectGraph() {
		return objectGraph;
	}

	public TGGRule getAppliedRule() {
		return appliedRule;
	}
}
