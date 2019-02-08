package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;

public class MemoryConsumptionObserver extends IbexObserver{
	
	protected final static Logger logger = Logger.getLogger(MemoryConsumptionObserver.class);
	
	public MemoryConsumptionObserver(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
	
	@Override
	public void update() {
		Runtime runtime = Runtime.getRuntime();
		long memory = runtime.totalMemory() - runtime.freeMemory();
		logger.info("Memory consumed in Bytes: " + memory);		
	}
	

}