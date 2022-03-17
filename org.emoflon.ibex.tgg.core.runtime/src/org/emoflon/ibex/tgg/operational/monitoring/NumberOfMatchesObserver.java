package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class NumberOfMatchesObserver extends AbstractIbexObserver {

	private final static Logger logger = Logger.getLogger(NumberOfMatchesObserver.class);
	private IMatchContainer operationalMatchContainer;
	private int matchesSize;

	public NumberOfMatchesObserver(IbexObservable observable) {
		super(observable);
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch (eventType) {
			case DONEINIT, MATCHAPPLIED -> {
				if (this.getObservable() instanceof OperationalStrategy op) {
					operationalMatchContainer = op.getMatchContainer();
					matchesSize = operationalMatchContainer.getMatches().size();
					String patternName = operationalMatchContainer.getNext().getRuleName();
					logger.info("Pattern: " + patternName + " hasMatches: " + matchesSize);
				}
			}
			default -> {
			}
		}
	}
}
