package org.emoflon.ibex.tgg.runtime.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.interpreter.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.TGGMatchParameterOrderProvider;
import org.emoflon.ibex.tgg.runtime.matches.container.DefaultMatchContainer;
import org.emoflon.ibex.tgg.runtime.matches.container.IMatchContainer;
import org.emoflon.ibex.tgg.runtime.matches.container.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.runtime.monitoring.AbstractIbexObservable;
import org.emoflon.ibex.tgg.runtime.strategies.modules.IbexExecutable;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchDistributor;
import org.emoflon.ibex.tgg.runtime.strategies.modules.MatchHandler;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.runtime.updatepolicy.IUpdatePolicy;
import org.emoflon.ibex.tgg.runtime.updatepolicy.NextMatchUpdatePolicy;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;
import org.emoflon.ibex.tgg.util.debug.ConsoleUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public abstract class OperationalStrategy extends AbstractIbexObservable implements IbexExecutable, TimeMeasurable {

	protected final static Logger logger = Logger.getRootLogger();
	protected final Times times = new Times();

	// Match and pattern management
	protected IMatchContainer operationalMatchContainer;
	protected MatchHandler matchHandler;

	protected Map<ITGGMatch, String> blockedMatches = cfactory.createObjectToObjectHashMap();

	// Configuration
	protected final IbexOptions options;

	// Model manipulation
	protected IGreenInterpreter greenInterpreter;

	protected TGGResourceHandler resourceHandler;

	protected MatchDistributor matchDistributor;
	
	protected RuleHandler ruleHandler;

	/***** Constructors *****/

	public OperationalStrategy(IbexOptions options) throws IOException {
		this(options, new NextMatchUpdatePolicy());
	}

	protected OperationalStrategy(IbexOptions options, IUpdatePolicy policy) throws IOException {
		this.options = options;
		initializeBasicModules(options, policy);
		initializeAdditionalModules(options);

		TimeRegistry.register(this);
		this.ruleHandler = options.tgg.ruleHandler();
	}

	private void initializeBasicModules(IbexOptions options, IUpdatePolicy policy) {
		this.notifyStartInit();

		options.executable(this);

		this.setUpdatePolicy(policy);

		resourceHandler = options.resourceHandler();
		matchDistributor = options.matchDistributor();
		matchHandler = options.matchHandler();

		this.notifyStartLoading();
		resourceHandler.initialize();
		this.notifyLoadingFinished();

		this.operationalMatchContainer = createMatchContainer();

		try {
			matchDistributor.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}

		matchHandler.initialize();
		matchHandler.handleOperationalMatches(operationalMatchContainer, getRelevantOperationalPatterns(), this::isPatternRelevantForInterpreter);

		greenInterpreter = new IbexGreenInterpreter(this);

		TGGMatchParameterOrderProvider.init(options.tgg.flattenedTGG());

		options.tgg.ruleHandler().registerRules(options.tgg.flattenedTGG());
		
		this.notifyDoneInit();
	}

	protected abstract void initializeAdditionalModules(IbexOptions options) throws IOException;

	/***** Resource management *****/

	public TGGModel getTGG() {
		return options.tgg.tgg();
	}

	protected IMatchContainer createMatchContainer() {
		return new DefaultMatchContainer(options.tgg.flattenedTGG());
	}

	@Override
	public void saveModels() throws IOException {
		resourceHandler.saveRelevantModels();
	}

	/***** Match and pattern management *****/

	protected boolean isPatternRelevantForInterpreter(PatternType patternType) {
		return true;
	}

	protected abstract Set<PatternType> getRelevantOperationalPatterns();

	public abstract void run() throws IOException;

	protected boolean processOneOperationalRuleMatch() {
		Timer.start();

		this.updateBlockedMatches();
		if (operationalMatchContainer.isEmpty()) {
			times.addTo("translate:ruleApplication", Timer.stop());
			return false;
		}

		Timer.start();
		ITGGMatch match = chooseOneMatch();
		String ruleName = match.getRuleName();
		times.addTo("ruleApplication:chooseMatch", Timer.stop());

		Optional<ITGGMatch> result = processOperationalRuleMatch(ruleName, match);
		matchHandler.removeOperationalMatch(match);

		if (result.isPresent()) {
			options.debug.benchmarkLogger().addToNumOfMatchesApplied(1);
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Matches: removed (as it has just been applied) " + match.getPatternName() + "(" + match.hashCode() + ")\n");
		} else
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Matches: removed (as application failed) " + match.getPatternName() + "(" + match.hashCode() + ")\n");

		times.addTo("translate:ruleApplication", Timer.stop());
		return true;
	}

	protected ITGGMatch chooseOneMatch() {
		ITGGMatch match = this.notifyChooseMatch(new ImmutableMatchContainer(operationalMatchContainer));

		if (match == null)
			throw new IllegalStateException("Update policies should never return null!");

		return match;
	}

	protected Optional<ITGGMatch> processOperationalRuleMatch(String ruleName, ITGGMatch match) {
		// generatedPatternsSizeObserver.setNodes(match);
		Timer.start();
		if (getBlockedMatches().containsKey(match)) {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Rule application: blocked by update policy " + match.getPatternName() + "(" + match.hashCode() + ")");
			times.addTo("ruleApplication:init", Timer.stop());
			return Optional.empty();
		}

		TGGOperationalRule operationRule = ruleHandler.getOperationalRule(ruleName);
		
		LoggerConfig.log(LoggerConfig.log_ruleApplication(),
				() -> "Rule application: attempting to apply " + match.getPatternName() + "(" + match.hashCode() + ") with " //
						+ operationRule.getClass().getSimpleName() + "@" + Integer.toHexString(operationRule.hashCode()));
		times.addTo("ruleApplication:init", Timer.stop());

		Timer.start();
		Optional<ITGGMatch> comatch = greenInterpreter.apply(operationRule, match);
		times.addTo("ruleApplication:createElements", Timer.stop());

		comatch.ifPresent(cm -> {
			LoggerConfig.log(LoggerConfig.log_ruleApplication(),
					() -> "Rule application: successfully applied " + match.getPatternName() + "(" + match.hashCode() + ")\n" //
							+ ConsoleUtil.indent(ConsoleUtil.printMatchParameter(match), 18, true));
			this.notifyMatchApplied(match, ruleName);
			operationalMatchContainer.matchApplied(match);

			Timer.start();
			handleSuccessfulRuleApplication(cm, ruleName, operationRule);
			times.addTo("ruleApplication:finish", Timer.stop());
		});

		return comatch;
	}

	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, TGGOperationalRule operationRule) {
		createMarkers(operationRule, cm, ruleName);
	}

	protected void updateBlockedMatches() {
		if (this.getUpdatePolicy() instanceof NextMatchUpdatePolicy)
			return;

		for (ITGGMatch match : operationalMatchContainer.getMatches()) {
			if (!this.getUpdatePolicy().matchShouldBeApplied(match, match.getRuleName())) {
				if (!blockedMatches.containsKey(match))
					blockedMatches.put(match, "Match is blocked by the update policy");
				this.operationalMatchContainer.removeMatch(match);
			}
		}
	}

	public Map<ITGGMatch, String> getBlockedMatches() {
		return blockedMatches;
	}

	public IGreenInterpreter getGreenInterpreter() {
		return greenInterpreter;
	}

	public void terminate() throws IOException {
		matchDistributor.removeBlackInterpreter();
		operationalMatchContainer.removeAllMatches();
		matchHandler.clearCaches();

		TimeRegistry.logTimes();
	}

	protected void prepareMarkerCreation(TGGOperationalRule operationRule, ITGGMatch comatch, String ruleName) {

	}

	protected void createMarkers(TGGOperationalRule operationRule, ITGGMatch comatch, String ruleName) {
		prepareMarkerCreation(operationRule, comatch, ruleName);
		greenInterpreter.createMarkers(ruleName, comatch);
	}
	
	/***** Configuration *****/

	public IbexOptions getOptions() {
		return options;
	}

	public IMatchContainer getMatchContainer() {
		return operationalMatchContainer;
	}

	public MatchHandler getMatchHandler() {
		return matchHandler;
	}

	protected void collectDataToBeLogged() {
		matchDistributor.collectDataToBeLogged();
	}

	public TGGResourceHandler getResourceHandler() {
		return resourceHandler;
	}

	public void setResourceHandler(TGGResourceHandler resourceHandler) {
		this.resourceHandler = resourceHandler;
	}

	@Override
	public Times getTimes() {
		return times;
	}
	
	public abstract StrategyMode getStrategyMode();
}