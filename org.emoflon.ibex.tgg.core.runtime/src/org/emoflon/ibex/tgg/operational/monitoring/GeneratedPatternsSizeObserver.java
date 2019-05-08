package org.emoflon.ibex.tgg.operational.monitoring;



import java.util.Map;
import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.monitoring.IbexObserver.ObservableEvent;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class GeneratedPatternsSizeObserver extends AbstractIbexObserver{    

   
	private final static Logger logger = Logger.getLogger(GeneratedPatternsSizeObserver.class);
    private Map<String, IGreenPatternFactory> factories;
    public GeneratedPatternsSizeObserver(IbexObservable observable) {
		super(observable);
	}
      
    public void calculateSize() {
    	logger.info("Generated Patterns Size: ");
    	
        factories.forEach((k,v)->{
        	int size = 0;
       
        	size += v.getBlackCorrEdgesInRule().size();
        	size += v.getBlackSrcEdgesInRule().size();
        	size += v.getBlackTrgEdgesInRule().size();
        	size += v.getBlackCorrNodesInRule().size();
        	size += v.getBlackSrcNodesInRule().size();
        	size += v.getBlackTrgNodesInRule().size();
        	
        	logger.info(k + ": " + size);       	
        });
    }

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch(eventType) {
			case DONEINIT: 
			case MATCHAPPLIED:
				if(this.getObservable() instanceof OperationalStrategy) {
					OperationalStrategy op = (OperationalStrategy) this.getObservable();
					factories = op.getFactories();
					this.calculateSize();
					break;
				}
		default:
			break;	
		}
	}
        
}