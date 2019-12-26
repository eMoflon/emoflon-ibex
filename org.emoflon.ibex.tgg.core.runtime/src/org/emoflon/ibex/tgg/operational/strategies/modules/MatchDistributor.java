package org.emoflon.ibex.tgg.operational.strategies.modules;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IBlackInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import IBeXLanguage.IBeXContext;

public class MatchDistributor implements IMatchObserver {

	private final long INTERVAL_LENGTH = 5000;
	private long currentIntervalStart = -1;
	
	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	protected Map<IMatch, String> blockedMatches = cfactory.createObjectToObjectHashMap();

	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2addMatch = new HashMap<>();;
	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2removeMatch = new HashMap<>();
	
	private IbexOptions options;
	
	private long matchCounter = 0;
	private IBlackInterpreter blackInterpreter;
	private ResourceSet rs;
	
	private boolean initialized = false;
	
	public MatchDistributor(IbexOptions options) {
		this.options = options;
	}
	
	public void initialize() throws IOException {
		if(initialized)
			return;
		
		rs = options.getResourceHandler().getResourceSet();
		if(options.getBlackInterpreter() == null)
			logger.warn("No pattern matcher is registered!");
		else	
			initialiseBlackInterpreter(options.getExecutable());
		
		initialized = true;
	}
	
	public void updateMatches() {
		blackInterpreter.updateMatches();
	}
	
	@Override
	public void notifySubscriptions() {
	
	}
	
	protected void initialiseBlackInterpreter(IbexExecutable executable) throws IOException {		
		blackInterpreter = options.getBlackInterpreter();
		Optional<RuntimeException> initExcep = Optional.empty();
		try {
			blackInterpreter.initialise(executable, options, rs.getPackageRegistry(), this);
		} catch (RuntimeException e) {
			initExcep = Optional.of(e);
		}

		try {
			blackInterpreter.monitor(rs);
		} finally {
			if (initExcep.isPresent())
				throw initExcep.get();
		}
	}
	
	/**
	 * Replaces the black interpreter and initialises the new black interpreter
	 * 
	 * @param newBlackInterpreter The black interpreter to replace the existing
	 *                            black interpreter
	 */
	protected void reinitializeBlackInterpreter(IbexExecutable executable, IBlackInterpreter newBlackInterpreter) {
		this.removeBlackInterpreter();
		this.blackInterpreter = newBlackInterpreter;
		this.blackInterpreter.initialise(executable, options, rs.getPackageRegistry(), this);
		this.blackInterpreter.monitor(rs);
	}
	
	/**
	 * Removes the black interpreter and all references to the black interpreter
	 * from the strategy and its resources
	 */
	public void removeBlackInterpreter() {
		if (blackInterpreter == null)
			return;

		blackInterpreter.terminate();
		blackInterpreter = null;
		rs.getAllContents().forEachRemaining(c -> c.eAdapters().clear());
		rs.eAdapters().clear();
		logger.debug("Removed black interpreter");
	}

	@Override
	public void addMatch(IMatch match) {
		ITGGMatch tggMatch = (ITGGMatch) match;
		
		Collection<Consumer<ITGGMatch>> consumers = type2addMatch.get(tggMatch.getType());
		if (consumers != null) {
			matchCounter++;
			if (currentIntervalStart == -1) {
				logger.info("Now collecting matches...");
				currentIntervalStart = System.currentTimeMillis();
			} else if (System.currentTimeMillis() - currentIntervalStart > INTERVAL_LENGTH) {
				logger.info("Collected " + matchCounter + " matches...");
				currentIntervalStart = System.currentTimeMillis();
			}

			consumers.forEach(c -> c.accept(tggMatch));
		}
	}

	@Override
	public void removeMatch(IMatch match) {
		ITGGMatch tggMatch = (ITGGMatch) match;
		Collection<Consumer<ITGGMatch>> consumers = type2removeMatch.get(tggMatch.getType());
		if (consumers != null) {
			consumers.forEach(c -> c.accept(tggMatch));
			logger.debug("Removed due to delete event from pattern matcher: ");
			logger.debug(match.getPatternName());
		}
	}

	/***** Benchmark Logging *****/
	
	public void collectDataToBeLogged() {
		options.getBenchmarkLogger().setNumOfMatchesFound(matchCounter);
	}
	
	public void register(Collection<PatternType> types, Consumer<ITGGMatch> addMatch, Consumer<ITGGMatch> removeMatch) {
		for(PatternType type : types) {
			Collection<Consumer<ITGGMatch>> addConsumers = type2addMatch.get(type);
			if(addConsumers == null) {
				addConsumers = new LinkedList<>();
				type2addMatch.put(type, addConsumers);
			}
			addConsumers.add(addMatch);
			
			Collection<Consumer<ITGGMatch>> removeConsumers = type2removeMatch.get(type);
			if(removeConsumers == null) {
				removeConsumers = new LinkedList<>();
				type2removeMatch.put(type, removeConsumers);
			}
			removeConsumers.add(removeMatch);
		}
	}

	public Collection<PatternType> getPatternRelevantForCompiler() {
		return type2addMatch.keySet();
	}
}
