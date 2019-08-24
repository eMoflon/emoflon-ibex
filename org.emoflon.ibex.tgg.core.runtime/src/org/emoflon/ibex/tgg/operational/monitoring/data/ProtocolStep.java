package org.emoflon.ibex.tgg.operational.monitoring.data;

public class ProtocolStep {
	private int index;
	private Graph objectGraph;

	public ProtocolStep(int pIndex, Graph pObjectGraph) {
		index = pIndex;
		objectGraph = pObjectGraph;
	}

	public int getIndex() {
		return index;
	}

	public Graph getObjectGraph() {
		return objectGraph;
	}
}
