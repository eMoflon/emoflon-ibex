package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;

public class MemoryConsumptionObserver extends AbstractIbexObserver{
	
	private final static Logger logger = Logger.getLogger(MemoryConsumptionObserver.class);
	private long currentMemoryConsumption = 0;
	
	public MemoryConsumptionObserver(IbexObservable observable) {
		super(observable);
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		Runtime runtime = Runtime.getRuntime();
		this.currentMemoryConsumption = runtime.totalMemory() - runtime.freeMemory();
		logger.info("Memory consumed in Bytes: " + currentMemoryConsumption);
	}

	public long getCurrentMemoryConsumption() {
		return currentMemoryConsumption;
	}
}