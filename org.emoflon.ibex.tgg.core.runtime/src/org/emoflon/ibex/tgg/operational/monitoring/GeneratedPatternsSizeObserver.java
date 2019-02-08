package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashMap;

import org.apache.log4j.Logger;
import IBeXLanguage.IBeXContextPattern;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class GeneratedPatternsSizeObserver extends IbexObserver{	

	protected final static Logger logger = Logger.getLogger(GeneratedPatternsSizeObserver.class);
    public HashMap<String, Integer> edges = new HashMap<String, Integer>();
    public HashMap<String, Integer> nodes = new HashMap<String, Integer>();


	public GeneratedPatternsSizeObserver(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
		
	@Override
	public void update() {
		logger.info("Generated Patterns Size: ");
		System.out.println(this.edges);
		System.out.println(this.nodes);
		
	}
	
	public void setEdges(IBeXContextPattern ibexPattern) {
		edges.put(ibexPattern.getName(), ibexPattern.getLocalEdges().size());
		System.out.println(edges);
	}
	
	public void setNodes(IMatch match) {
		nodes.put(match.getPatternName(), match.getParameterNames().size());
	}	
		
}