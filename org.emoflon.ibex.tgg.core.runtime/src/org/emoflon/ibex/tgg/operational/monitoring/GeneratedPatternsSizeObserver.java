package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
        this.calculteSize();
    }
    
    public void setEdges(IBeXContextPattern ibexPattern) {
        String key = ibexPattern.getName();
        if (!edges.containsKey(key)) {
            edges.put(key, ibexPattern.getLocalEdges().size());
        }
    }
    
    public void setNodes(IMatch match) {
        String key = match.getPatternName();
        if (!nodes.containsKey(key)) {
            nodes.put(key, match.getParameterNames().size());
        }
    }    
    
    public void calculteSize() {
        Iterator<Entry<String, Integer>> node = nodes.entrySet().iterator();
        while (node.hasNext()) {
            Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)node.next();
            String key = pair.getKey().toString();
            Integer edgeSize = edges.get(key) != null ? edges.get(key).intValue() : 0;
            Integer nodeSize = nodes.get(key) != null ? nodes.get(key).intValue() : 0;
            Integer totalSize = edgeSize + nodeSize;
            System.out.println(key + " = " + totalSize);
            node.remove();
        }
    }
        
}