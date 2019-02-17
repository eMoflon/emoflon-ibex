package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;

public class NumberOfMatchesObserver extends AbstractIbexObserver{

	private final static Logger logger = Logger.getLogger(GeneratedPatternsSizeObserver.class);
    private int matchesSize;
	
    public NumberOfMatchesObserver(IbexObservable observable) {
		super(observable);
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		
		if(eventType.equals(ObservableEvent.CHOOSEMATCH) && additionalInformation.length >= 1 && (additionalInformation[0] instanceof IMatchContainer)) {
			IMatchContainer operationalMatchContainer = (IMatchContainer) additionalInformation[0];
			matchesSize = operationalMatchContainer.getMatches().size();
			logger.info("Found matches: " + matchesSize);
		}
	}  
}
