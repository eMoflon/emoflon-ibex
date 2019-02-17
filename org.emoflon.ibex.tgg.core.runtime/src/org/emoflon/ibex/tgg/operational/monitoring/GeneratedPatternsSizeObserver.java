package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import IBeXLanguage.IBeXContextPattern;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GeneratedPatternsSizeObserver extends AbstractIbexObserver{    

   
	private final static Logger logger = Logger.getLogger(GeneratedPatternsSizeObserver.class);
    private HashMap<String, Integer> edges = new HashMap<String, Integer>();
    private HashMap<String, Integer> nodes = new HashMap<String, Integer>();
    
    public GeneratedPatternsSizeObserver(IbexObservable observable) {
		super(observable);
	}
    
    public void setEdges(IBeXContextPattern ibexPattern) {
        String patternName = PatternSuffixes.removeSuffix(ibexPattern.getName());
        if (!edges.containsKey(patternName)) {
            edges.put(patternName, ibexPattern.getLocalEdges().size());
        }
    }
    
    public void setNodes(IMatch match) {
        String patternName = PatternSuffixes.removeSuffix(match.getPatternName());
        if (!nodes.containsKey(patternName)) {
            nodes.put(patternName, match.getParameterNames().size());
        }
    }    
    
    public void calculateSize() {
        Iterator<Entry<String, Integer>> node = nodes.entrySet().iterator();
        while (node.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)node.next();
            String patternName = pair.getKey().toString();
            Integer edgeSize = edges.get(patternName) != null ? edges.get(patternName).intValue() : 0;
            Integer nodeSize = nodes.get(patternName) != null ? nodes.get(patternName).intValue() : 0;
            Integer totalSize = edgeSize + nodeSize;
            logger.info(patternName + ": " + totalSize);
            node.remove();
        }
    }

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		logger.info("Generated Patterns Size: ");
        this.calculateSize();
	}
        
}