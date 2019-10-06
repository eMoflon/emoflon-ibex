package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
/**
 * 	This observer class gives the memory consumed while running one of the TGG operation
 *
 */
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
/**
 * @return the current memory consumed
 */
	public long getCurrentMemoryConsumption() {
		return currentMemoryConsumption;
	}
}