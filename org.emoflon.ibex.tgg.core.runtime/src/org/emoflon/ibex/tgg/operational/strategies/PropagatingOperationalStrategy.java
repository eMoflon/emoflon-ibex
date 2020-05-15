package org.emoflon.ibex.tgg.operational.strategies;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.common.collections.CollectionFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.benchmark.EmptyBenchmarkLogger;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.debug.LoggingMatchContainer;
import org.emoflon.ibex.tgg.operational.defaults.IbexGreenInterpreter;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.matches.MarkingMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.PrecedenceMatchContainer;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.AbstractRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy;

import runtime.TGGRuleApplication;

public abstract class PropagatingOperationalStrategy extends OperationalStrategy {

	// Repair
	protected Collection<AbstractRepairStrategy> repairStrategies = new ArrayList<>();

	protected Map<TGGRuleApplication, ITGGMatch> brokenRuleApplications = CollectionFactory.cfactory
			.createObjectToObjectHashMap();
	protected IRedInterpreter redInterpreter;

	protected long repairTime = 0;
	protected long translateTime = 0;
	protected long removeTime = 0;
	protected long matchApplicationTime = 0;

	/***** Constructors *****/

	public PropagatingOperationalStrategy(IbexOptions options) throws IOException {
		super(options);
		redInterpreter = new IbexRedInterpreter(this);
	}

	public void registerRedInterpeter(IRedInterpreter redInterpreter) {
		this.redInterpreter = redInterpreter;
	}

	/***** Algorithm *****/

	protected void initializeRepairStrategy(IbexOptions options) {
		if (!repairStrategies.isEmpty())
			return;

		if (options.repair.useShortcutRules()) {
			repairStrategies.add(new ShortcutRepairStrategy(this));
		}
		if (options.repair.repairAttributes()) {
			repairStrategies.add(new AttributeRepairStrategy(this));
		}
	}

	protected boolean repairBrokenMatches() {
		long tic = System.nanoTime();

		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		for (AbstractRepairStrategy rStrategy : repairStrategies) {
			for (ITGGMatch repairCandidate : rStrategy.chooseMatches(brokenRuleApplications)) {
				if (alreadyProcessed.contains(repairCandidate))
					continue;

				ITGGMatch repairedMatch = rStrategy.repair(repairCandidate);
				if (repairedMatch != null) {
					alreadyProcessed.add(repairCandidate);

					TGGRuleApplication oldRa = getRuleApplicationNode(repairCandidate);
					brokenRuleApplications.remove(oldRa);

					TGGRuleApplication newRa = getRuleApplicationNode(repairedMatch);
					brokenRuleApplications.put(newRa, repairedMatch);
					alreadyProcessed.add(repairedMatch);
				}
			}
		}
		repairTime += System.nanoTime() - tic;
		return !alreadyProcessed.isEmpty();
	}

	protected void translate() {
		long tic = System.nanoTime();
		if (options.propagate.applyConcurrently()) {
			matchDistributor.updateMatches();

			while (true) {
				while (processOneOperationalRuleMatch()) {

				}
				matchDistributor.updateMatches();
				if (!processOneOperationalRuleMatch())
					return;
			}
		} else {
			do {
				matchDistributor.updateMatches();
			} while (processOneOperationalRuleMatch());
		}

		translateTime += System.nanoTime() - tic;
	}

	@Override
	protected boolean processOneOperationalRuleMatch() {
		long tic = System.nanoTime();
		boolean b = super.processOneOperationalRuleMatch();
		matchApplicationTime += System.nanoTime() - tic;
		return b;
	}

	protected void rollBack() {
		long tic = System.nanoTime();
		do
			matchDistributor.updateMatches();
		while (revokeBrokenMatches());
		removeTime += System.nanoTime() - tic;
	}

	protected boolean revokeBrokenMatches() {
		// clear pending elements since every element that has not been repaired until
		// now has to be revoked
		if (operationalMatchContainer instanceof PrecedenceMatchContainer)
			((PrecedenceMatchContainer) operationalMatchContainer).clearPendingElements();

		if (brokenRuleApplications.isEmpty())
			return false;

		revokeAllMatches();

		return true;
	}

	protected void revokeAllMatches() {
		while (!brokenRuleApplications.isEmpty()) {
			Set<TGGRuleApplication> revoked = cfactory.createObjectSet();

			for (TGGRuleApplication ra : brokenRuleApplications.keySet()) {
				ITGGMatch match = brokenRuleApplications.get(ra);
				redInterpreter.revokeOperationalRule(match);
				revoked.add(ra);
				LoggerConfig.log(LoggerConfig.log_matchApplication(), () -> "Rollback match: " + match);
			}
			for (TGGRuleApplication revokedRA : revoked)
				brokenRuleApplications.remove(revokedRA);

			options.debug.benchmarkLogger().addToNumOfMatchesRevoked(revoked.size());
		}
	}

	/***** Marker Handling *******/

	/**
	 * Override in subclass if markers for protocol are not required (this can speed
	 * up the translation process).
	 */
	@Override
	protected void handleSuccessfulRuleApplication(ITGGMatch cm, String ruleName, IGreenPattern greenPattern) {
		createMarkers(greenPattern, cm, ruleName);
	}

	/***** Match and pattern management *****/

	@Override
	protected IMatchContainer createMatchContainer() {
		if (options.propagate.usePrecedenceGraph())
			return new PrecedenceMatchContainer(this);
		else
			return new MarkingMatchContainer(this);
	}

	@Override
	protected void addConsistencyMatch(ITGGMatch match) {
		super.addConsistencyMatch(match);

		TGGRuleApplication ruleAppNode = getRuleApplicationNode(match);
		if (brokenRuleApplications.containsKey(ruleAppNode)) {
			LoggerConfig.log(LoggerConfig.log_matchApplication(),
					() -> match.getPatternName() + " (" + match.hashCode() + ") appears to be fixed.");
			brokenRuleApplications.remove(ruleAppNode);
			options.debug.benchmarkLogger().addToNumOfMatchesRepaired(1);
		}

		operationalMatchContainer.matchApplied(match);
	}

	@Override
	public boolean removeOperationalRuleMatch(ITGGMatch match) {
		if (match.getType() == PatternType.CONSISTENCY)
			addConsistencyBrokenMatch((ITGGMatch) match);

		return super.removeOperationalRuleMatch(match);
	}

	protected void addConsistencyBrokenMatch(ITGGMatch match) {
		TGGRuleApplication ra = getRuleApplicationNode(match);
		brokenRuleApplications.put(ra, match);

		consistencyMatches.remove(ra);
		operationalMatchContainer.removeMatch(match);
	}
	
	public IRedInterpreter getRedInterpreter() {
		return redInterpreter;
	}

	@Override
	protected void collectDataToBeLogged() {
		if (options.debug.benchmarkLogger() instanceof EmptyBenchmarkLogger)
			return;

		super.collectDataToBeLogged();

		options.debug.benchmarkLogger().setNumOfElementsCreated(greenInterpreter.getNumOfCreatedNodes());
		options.debug.benchmarkLogger().setNumOfElementsDeleted(redInterpreter.getNumOfDeletedNodes());
	}

	@Override
	public void terminate() throws IOException {
		DecimalFormat df = new DecimalFormat("0.#####");
		df.setMaximumFractionDigits(5);
		LoggerConfig.log(LoggerConfig.log_translationTime(), 		() -> "Translation time: " + df.format((double) translateTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_repairTime(), 			() -> "Repair time: " + df.format((double) repairTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_removalTime(), 			() -> "Remove time: " + df.format((double) removeTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "Match application time: " + df.format((double) matchApplicationTime / (double) (1000 * 1000 * 1000)) + " -> {");
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "     Init time:            " + df.format((double) initMatchApplicationTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "     Choose match time:    " + df.format((double) chooseMatchTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "     Create elements time: " + df.format((double) ((IbexGreenInterpreter) greenInterpreter).getCreationTime() / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "     Finalize time:        " + df.format((double) finishRuleApplicationTime / (double) (1000 * 1000 * 1000)));
		LoggerConfig.log(LoggerConfig.log_matchApplicationTime(), 	() -> "}");
		LoggerConfig.log(LoggerConfig.log_collectMatchTime(), 		() -> "Match collection time: " + df.format((double) matchDistributor.getTime() / (double) (1000 * 1000 * 1000)));

		if (operationalMatchContainer instanceof LoggingMatchContainer)
			((LoggingMatchContainer) operationalMatchContainer).log();
		super.terminate();
	}

}
