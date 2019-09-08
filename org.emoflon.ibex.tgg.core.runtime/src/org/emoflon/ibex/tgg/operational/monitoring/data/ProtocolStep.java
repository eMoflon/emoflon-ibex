package org.emoflon.ibex.tgg.operational.monitoring.data;

public class ProtocolStep {
	private int index;
	private Graph objectGraph;
	private String ruleName;

	public ProtocolStep(int pIndex, Graph pObjectGraph, String pRuleName) {
		index = pIndex;
		objectGraph = pObjectGraph;
	}

	public int getIndex() {
		return index;
	}

	public Graph getObjectGraph() {
		return objectGraph;
	}
	
	public String getRuleName() {
		return ruleName;
	}
}
