package org.emoflon.ibex.tgg.runtime.repair;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.BrokenMatchContainer;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.BasicShortcutPatternProvider;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.ShortcutPatternProvider;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.runtime.repair.strategies.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairStrategy;
import org.emoflon.ibex.tgg.runtime.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.PropagatingOperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.PropagationDirectionHolder;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class SeqRepair implements TimeMeasurable {

	protected Times times;

	protected static final PatternType[] shortcutPatternTypes = { PatternType.FWD, PatternType.BWD };

	protected final PropagatingOperationalStrategy opStrat;
	protected final PropagationDirectionHolder propDirHolder;

	protected Collection<RepairStrategy> repairStrategies = new ArrayList<>();

	protected BrokenMatchContainer dependencyContainer;

	protected boolean strategiesInitialized;

	public SeqRepair(PropagatingOperationalStrategy opStrat, PropagationDirectionHolder propDirHolder) {
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
				ShortcutPatternProvider shortcutPatternProvider = initShortcutPatternProvider(opStrat.getOptions());
				repairStrategies.add(new ShortcutRepairStrategy(opStrat.getOptions(), //
						opStrat.getGreenInterpreter(), opStrat.getRedInterpreter(), shortcutPatternProvider));
			}
			if (opStrat.getOptions().repair.repairAttributes()) {
				repairStrategies.add(new AttributeRepairStrategy(opStrat));
			}

			times.addTo("initializeStrategies", Timer.stop());
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "Repair: init strategies - done\n");
		}
	}

	private ShortcutPatternProvider initShortcutPatternProvider(IbexOptions options) {
		return new BasicShortcutPatternProvider(options, shortcutPatternTypes, true);
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

				for (RepairStrategy rStrategy : repairStrategies) {
					if (alreadyProcessed.contains(repairCandidate)) {
						continue;
					}

					RepairApplicationPoint applPoint = new RepairApplicationPoint(repairCandidate, propDirHolder.get().getPatternType());
					Collection<ITGGMatch> repairedMatch = rStrategy.repair(applPoint);
					if (repairedMatch != null) {
						processRepairedMatches(applPoint, repairedMatch);

						alreadyProcessed.add(repairCandidate);
						alreadyProcessed.addAll(repairedMatch);
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

	private void processRepairedMatches(RepairApplicationPoint applPoint, Collection<ITGGMatch> repairedMatches) {
		if (applPoint instanceof ShortcutApplicationPoint scApplPoint) {
			for (PrecedenceNode originalNode : scApplPoint.getOriginalNodes()) {
				opStrat.getMatchHandler().removeBrokenRuleApplication(originalNode.getMatch().getRuleApplicationNode());
			}
		} else {
			opStrat.getMatchHandler().removeBrokenRuleApplication(applPoint.getApplicationMatch().getRuleApplicationNode());
		}

		for (ITGGMatch repairedMatch : repairedMatches) {
			opStrat.getMatchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
		}
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
