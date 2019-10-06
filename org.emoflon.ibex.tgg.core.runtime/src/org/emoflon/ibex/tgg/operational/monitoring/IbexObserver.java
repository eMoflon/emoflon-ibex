package org.emoflon.ibex.tgg.operational.monitoring;

public interface IbexObserver {
	
	public enum ObservableEvent {
		STARTLOADING, LOADINGFINISHED, STARTINIT, DONEINIT, MATCHAPPLIED, CHOOSEMATCH; 
	}
	/**
	 *  Called whenever the observer object is changed. Calculates and sets the currentMemoryConsumption during the runtime
	 * @param eventType type of {@link ObservableEvent}
	 * @param additionalInformation additionalInformation
	 */
	public abstract void update(ObservableEvent eventType, Object... additionalInformation);
	
}
