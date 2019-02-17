package org.emoflon.ibex.tgg.operational.monitoring;

public interface IbexObserver {
	
	public enum ObservableEvent {
		STARTLOADING, LOADINGFINISHED, STARTINIT, DONEINIT; 
	}
	public abstract void update(ObservableEvent eventType, Object... additionalInformation);
	
}
