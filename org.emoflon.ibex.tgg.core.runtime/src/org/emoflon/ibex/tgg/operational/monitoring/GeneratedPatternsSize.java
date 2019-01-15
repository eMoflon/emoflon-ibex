package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;

public class GeneratedPatternsSize extends IbexObserver{	

	protected final static Logger logger = Logger.getLogger(MemoryConsumption.class);
	
	public GeneratedPatternsSize(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
		
	@Override
	public void update() {
		logger.info("Generated Patterns Size is here: ");		
	}
		
}