package org.emoflon.ibex.tgg.operational.monitoring;

public abstract class IbexObserver {

	protected ObservableOperation observableOperation;

	//public abstract void getMemoryConsumed();
	
	public abstract void update();

	public void helper(Object parameter) {
	}

}
