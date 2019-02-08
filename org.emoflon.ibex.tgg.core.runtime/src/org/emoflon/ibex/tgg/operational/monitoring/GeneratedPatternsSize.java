package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import IBeXLanguage.IBeXContextPattern;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class GeneratedPatternsSize extends IbexObserver{	

	protected final static Logger logger = Logger.getLogger(MemoryConsumption.class);
    public HashMap<String, Integer> edges = new HashMap<String, Integer>();
    public HashMap<String, Integer> nodes = new HashMap<String, Integer>();


	public GeneratedPatternsSize(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
		
	@Override
	public void update() {
		logger.info("Generated Patterns Size: ");
		System.out.println(this.edges);
		System.out.println(this.nodes);
		
	}
	
	public void helper(Object parameter) {
		if (parameter instanceof IBeXContextPattern) {
			IBeXContextPattern ibexPattern = (IBeXContextPattern) parameter;
			edges.put(ibexPattern.getName(), ibexPattern.getLocalEdges().size());
			System.out.print(this.edges);
		}
		
		System.out.println(parameter.getClass().toString());
		if (parameter instanceof IMatch) {
			IMatch match = (IMatch) parameter;
			nodes.put(match.getPatternName(), match.getParameterNames().size());
		}
	}
		
}