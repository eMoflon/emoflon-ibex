package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;

public class NumberOfMatchesObserver extends IbexObserver{

    protected final static Logger logger = Logger.getLogger(GeneratedPatternsSizeObserver.class);
    int matchesSize; 
    String patternName;
    
	public NumberOfMatchesObserver(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		logger.info("Pattern: " + patternName + " hasMatches: " + matchesSize);
	}
	
	public void getMatch(IMatch match, IMatchContainer operationalMatchContainer) {
		matchesSize = operationalMatchContainer.getMatches().size();
		patternName = PatternSuffixes.removeSuffix(match.getPatternName());
    }  
}
