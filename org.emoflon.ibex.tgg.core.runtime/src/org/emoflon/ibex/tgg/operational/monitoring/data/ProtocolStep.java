package org.emoflon.ibex.tgg.operational.monitoring.data;

public class ProtocolStep {
	private int index;
	private TGGObjectGraph objectGraph;

	public ProtocolStep(int pIndex, TGGObjectGraph pObjectGraph) {
		index = pIndex;
		objectGraph = pObjectGraph;
	}

	public int getIndex() {
		return index;
	}

	public TGGObjectGraph getObjectGraph() {
		return objectGraph;
	}
}
