package org.emoflon.ibex.tgg.operational.monitoring;

public interface IbexObserver {
	
	public enum ObservableEvent {
		STARTLOADING, LOADINGFINISHED, STARTINIT, DONEINIT, MATCHAPPLIED, CHOOSEMATCH; 
	}
	public abstract void update(ObservableEvent eventType, Object... additionalInformation);
	
}
