package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class GeneratedPatternsSize extends IbexObserver{	

	protected final static Logger logger = Logger.getLogger(MemoryConsumption.class);
    private ArrayList<String> patternsName = new ArrayList<String>(); 


	public GeneratedPatternsSize(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
		
	@Override
	public void update() {
		logger.info("Generated Patterns Size: " + this.patternsName.size());		
	}
	
	public void helper(String patternName) {
		if (this.patternsName.indexOf(patternName) == -1) {
			this.patternsName.add(patternName);
		}
	}
		
}