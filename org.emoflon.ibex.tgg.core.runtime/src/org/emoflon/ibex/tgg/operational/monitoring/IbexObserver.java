package org.emoflon.ibex.tgg.operational.monitoring;

public abstract class IbexObserver {

	protected ObservableOperation observableOperation;
	
	public abstract void update();
	
}
