package org.emoflon.ibex.tgg.runtime.monitoring;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.runtime.matches.container.IMatchContainer;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;

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
