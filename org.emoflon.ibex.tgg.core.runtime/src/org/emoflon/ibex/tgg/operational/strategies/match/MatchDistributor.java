package org.emoflon.ibex.tgg.operational.strategies.match;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class MatchDistributor implements IMatchObserver {

	private final long INTERVAL_LENGTH = 5000;
	private long currentIntervalStart = -1;
	
	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	protected Map<IMatch, String> blockedMatches = cfactory.createObjectToObjectHashMap();

	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2addMatch;
	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2removeMatch;
	
	private IbexOptions options;
	
	private long matchCounter = 0;
	
	public MatchDistributor(IbexOptions options) {
		this.options = options;
		type2addMatch = new HashMap<>();
		type2removeMatch = new HashMap<>();
	}
	
	@Override
	public void notifySubscriptions() {
	
	}

	@Override
	public void addMatch(org.emoflon.ibex.common.operational.IMatch match) {
		ITGGMatch tggMatch = (ITGGMatch) match;
		
		matchCounter++;
		if (currentIntervalStart == -1) {
			logger.info("Now collecting matches...");
			currentIntervalStart = System.currentTimeMillis();
		} else if (System.currentTimeMillis() - currentIntervalStart > INTERVAL_LENGTH) {
			logger.info("Collected " + matchCounter + " matches...");
			currentIntervalStart = System.currentTimeMillis();
		}

		type2addMatch.get(tggMatch.getType()).
	}

	@Override
	public void removeMatch(org.emoflon.ibex.common.operational.IMatch match) {
		if (removeOperationalRuleMatch((IMatch) match)) {
			logger.debug("Removed due to delete event from pattern matcher: ");
			logger.debug(match.getPatternName());
		}
	}

	/***** Benchmark Logging *****/
	
	protected void collectDataToBeLogged() {
		options.getBenchmarkLogger().setNumOfMatchesFound(matchCounter);
	}
}
