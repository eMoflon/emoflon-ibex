package org.emoflon.ibex.tgg.operational.repair;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.BrokenMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.strategies.PropDirectedAttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.PropDirectedShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.PropagationDirectedRepairStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.PropagationDirectionHolder;

import runtime.TGGRuleApplication;

public class SyncRepair implements TimeMeasurable {

	protected Times times;

	protected static final PatternType[] shortcutPatternTypes = { PatternType.FWD, PatternType.BWD };

	protected final PropagatingOperationalStrategy opStrat;
	protected final PropagationDirectionHolder propDirHolder;

	protected Collection<PropagationDirectedRepairStrategy> repairStrategies = new ArrayList<>();

	protected BrokenMatchContainer dependencyContainer;

	protected boolean strategiesInitialized;

	public SyncRepair(PropagatingOperationalStrategy opStrat, PropagationDirectionHolder propDirHolder) {
		this.opStrat = opStrat;
		this.propDirHolder = propDirHolder;
		this.dependencyContainer = new BrokenMatchContainer(opStrat);
		this.strategiesInitialized = false;

		this.times = new Times();
		TimeRegistry.register(this);
	}

	protected void initializeStrategies() {
		if (!strategiesInitialized) {
			strategiesInitialized = true;

			LoggerConfig.log(LoggerConfig.log_repair(), () -> "Repair: init strategies");
			Timer.start();

			if (opStrat.getOptions().repair.useShortcutRules()) {
				repairStrategies.add(new PropDirectedShortcutRepairStrategy(opStrat, shortcutPatternTypes, propDirHolder));
			}
			if (opStrat.getOptions().repair.repairAttributes()) {
				repairStrategies.add(new PropDirectedAttributeRepairStrategy(opStrat, propDirHolder));
			}

			times.addTo("initializeStrategies", Timer.stop());
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "Repair: init strategies - done\n");
		}
	}

	public boolean repairBrokenMatches() {
		initializeStrategies();

		Timer.start();

		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		dependencyContainer.reset();
		opStrat.getMatchHandler().getBrokenMatches().forEach(dependencyContainer::addMatch);

		boolean processedOnce = true;
		while (processedOnce) {
			processedOnce = false;
			// TODO lfritsche, amoeller: refactor this -> applying repairs can occasionally invalidate
			// other consistency matches
			boolean finished = !dependencyContainer.isEmpty();
			while (finished) {
				ITGGMatch repairCandidate = dependencyContainer.getNext();

				processedOnce = true;

				for (PropagationDirectedRepairStrategy rStrategy : repairStrategies) {
					if (alreadyProcessed.contains(repairCandidate)) {
						continue;
					}

					ITGGMatch repairedMatch = rStrategy.repair(repairCandidate);
					if (repairedMatch != null) {

						TGGRuleApplication oldRa = repairCandidate.getRuleApplicationNode();
						opStrat.getMatchHandler().removeBrokenRuleApplication(oldRa);

						TGGRuleApplication newRa = repairedMatch.getRuleApplicationNode();
						opStrat.getMatchHandler().addBrokenRuleApplication(newRa, repairedMatch);
						alreadyProcessed.add(repairCandidate);
						alreadyProcessed.add(repairedMatch);
					}
				}
				dependencyContainer.matchApplied(repairCandidate);

				finished = !dependencyContainer.isEmpty();
			}
			alreadyProcessed.addAll(opStrat.getMatchHandler().getBrokenMatches());
			opStrat.getOptions().matchDistributor().updateMatches();

			opStrat.getMatchHandler().getBrokenMatches().stream() //
					.filter(m -> !alreadyProcessed.contains(m)) //
					.forEach(dependencyContainer::addMatch);
		}

		times.addTo("repairBrokenMatches", Timer.stop());
		return !alreadyProcessed.isEmpty();
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
