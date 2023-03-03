package org.emoflon.ibex.tgg.runtime.strategies.modules;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.engine.IMatch;
import org.emoflon.ibex.common.operational.IMatchObserver;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.IBlackInterpreter;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class MatchDistributor implements IMatchObserver, TimeMeasurable {

	protected final Times times = new Times();

	private final long INTERVAL_LENGTH = 5000;
	private long currentIntervalStart = -1;
	private boolean noOutput = true;

	protected final static Logger logger = Logger.getLogger(OperationalStrategy.class);

	protected Map<IMatch, String> blockedMatches = cfactory.createObjectToObjectHashMap();

	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2addMatch = new HashMap<>();
	protected Map<PatternType, Collection<Consumer<ITGGMatch>>> type2removeMatch = new HashMap<>();
	
	protected Map<Consumer<Collection<ITGGMatch>>, Collection<PatternType>> addMatches2types = new HashMap<>();
	protected Map<Consumer<Collection<ITGGMatch>>, Collection<PatternType>> removeMatches2types = new HashMap<>();

	private IbexOptions options;

	private long matchCounter = 0;
	private IBlackInterpreter blackInterpreter;
	private ResourceSet rs;

	private boolean initialized = false;
	private boolean useTrashResource;

	public MatchDistributor(IbexOptions options) {
		this.options = options;
		TimeRegistry.register(this);
	}

	public void initialize() throws IOException {
		if (initialized)
			return;

		rs = options.resourceHandler().getModelResourceSet();
		if (options.blackInterpreter() == null)
			logger.warn("No pattern matcher is registered!");
		else
			initialiseBlackInterpreter(options.executable());

		initialized = true;
	}

	public void updateMatches() {
		noOutput = true;
		Timer.start();

		blackInterpreter.updateMatches();

		times.addTo("updateMatches", Timer.stop());
		if (!noOutput)
			LoggerConfig.log(LoggerConfig.log_matches(), () -> "Pattern matcher: update matches - done\n");
	}

	protected void initialiseBlackInterpreter(IbexExecutable executable) throws IOException {
		blackInterpreter = options.blackInterpreter();
		useTrashResource = options.blackInterpreter().getClass().getName().contains("Democles");

		Optional<RuntimeException> initExcep = Optional.empty();
		try {
			blackInterpreter.initialise(executable, options, rs.getPackageRegistry(), this);
		} catch (RuntimeException e) {
			initExcep = Optional.of(e);
		}

		Collection<Resource> resources = new LinkedList<>();
		resources.add(options.resourceHandler().source);
		resources.add(options.resourceHandler().corr);
		resources.add(options.resourceHandler().target);
		resources.add(options.resourceHandler().protocol);
		if (useTrashResource)
			resources.add(options.resourceHandler().getTrashResource());

		try {
			blackInterpreter.monitor(resources);
		} finally {
			if (initExcep.isPresent())
				throw initExcep.get();
		}
	}

	/**
	 * Replaces the black interpreter and initialises the new black interpreter
	 * 
	 * @param newBlackInterpreter The black interpreter to replace the existing black
	 *                            interpreter
	 */
	protected void reinitializeBlackInterpreter(IbexExecutable executable, IBlackInterpreter newBlackInterpreter) {
		this.removeBlackInterpreter();
		this.blackInterpreter = newBlackInterpreter;
		this.blackInterpreter.initialise(executable, options, rs.getPackageRegistry(), this);

		Collection<Resource> resources = new LinkedList<>();
		resources.add(options.resourceHandler().source);
		resources.add(options.resourceHandler().corr);
		resources.add(options.resourceHandler().target);
		resources.add(options.resourceHandler().protocol);
		if (useTrashResource)
			resources.add(options.resourceHandler().getTrashResource());

		this.blackInterpreter.monitor(resources);
	}

	/**
	 * Removes the black interpreter and all references to the black interpreter from the
	 * strategy and its resources
	 */
	public void removeBlackInterpreter() {
		if (blackInterpreter == null)
			return;

		blackInterpreter.terminate();
		blackInterpreter = null;
		rs.getAllContents().forEachRemaining(c -> c.eAdapters().clear());
		rs.eAdapters().clear();
	}

	@Override
	public void addMatch(IMatch match) {
		handleLogging();

		ITGGMatch tggMatch = (ITGGMatch) match;
		Collection<Consumer<ITGGMatch>> consumers = type2addMatch.get(tggMatch.getType());
		if (consumers != null) {
			matchCounter++;
			if (currentIntervalStart == -1) {
				currentIntervalStart = System.currentTimeMillis();
			} else if (System.currentTimeMillis() - currentIntervalStart > INTERVAL_LENGTH) {
				LoggerConfig.log(LoggerConfig.log_matches(), () -> "Pattern matcher: collected " + matchCounter + " matches...");
				currentIntervalStart = System.currentTimeMillis();
			}

			consumers.forEach(c -> c.accept(tggMatch));
		}
	}

	@Override
	public void addMatches(Collection<IMatch> matches) {
		handleLogging();
		
		for(Consumer<Collection<ITGGMatch>> consumer : addMatches2types.keySet()) {
			Collection<PatternType> types = addMatches2types.get(consumer);
			Collection<ITGGMatch> tggMatches = matches.parallelStream().map(m -> (ITGGMatch) m).filter(m -> types.contains(m.getType())).collect(Collectors.toList());
			matchCounter+=tggMatches.size();
			consumer.accept(tggMatches);
		}
	}

	@Override
	public void removeMatch(IMatch match) {
		handleLogging();
		
		ITGGMatch tggMatch = (ITGGMatch) match;
		Collection<Consumer<ITGGMatch>> consumers = type2removeMatch.get(tggMatch.getType());
		if (consumers != null) {
			consumers.forEach(c -> c.accept(tggMatch));
			LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: removed " + match.getPatternName() + "(" + match.hashCode() + ")");
		}
	}

	@Override
	public void removeMatches(Collection<IMatch> matches) {
		handleLogging();
		
		for(Consumer<Collection<ITGGMatch>> consumer : removeMatches2types.keySet()) {
			Collection<PatternType> types = removeMatches2types.get(consumer);
			Collection<ITGGMatch> tggMatches = matches.parallelStream().map(m -> (ITGGMatch) m).filter(m -> types.contains(m.getType())).collect(Collectors.toList());
			consumer.accept(tggMatches);
		}
		matches.forEach(match -> LoggerConfig.log(LoggerConfig.log_matches(), () -> "Matches: removed " + match.getPatternName() + "(" + match.hashCode() + ")"));
	}

	/***** Benchmark Logging *****/

	public void collectDataToBeLogged() {
		options.debug.benchmarkLogger().setNumOfMatchesFound(matchCounter);
	}

	public void registerSingle(Collection<PatternType> types, Consumer<ITGGMatch> addMatch, Consumer<ITGGMatch> removeMatch) {
		for (PatternType type : types) {
			Collection<Consumer<ITGGMatch>> addConsumers = type2addMatch.get(type);
			if (addConsumers == null) {
				addConsumers = new LinkedList<>();
				type2addMatch.put(type, addConsumers);
			}
			addConsumers.add(addMatch);

			Collection<Consumer<ITGGMatch>> removeConsumers = type2removeMatch.get(type);
			if (removeConsumers == null) {
				removeConsumers = new LinkedList<>();
				type2removeMatch.put(type, removeConsumers);
			}
			removeConsumers.add(removeMatch);
		}
	}
	
	public void registerMultiple(Collection<PatternType> types, Consumer<Collection<ITGGMatch>> addMatches, Consumer<Collection<ITGGMatch>> removeMatches) {
		addMatches2types.put(addMatches, types.stream().collect(Collectors.toSet()));
		removeMatches2types.put(removeMatches, types.stream().collect(Collectors.toSet()));
	}

	public Collection<PatternType> getPatternRelevantForCompiler() {
		return type2addMatch.keySet();
	}

	@Override
	public Times getTimes() {
		return times;
	}

	private void handleLogging() {
		if (noOutput) {
			noOutput = false;
			LoggerConfig.log(LoggerConfig.log_matches(), () -> "Pattern matcher: update matches");
		}
	}

}
