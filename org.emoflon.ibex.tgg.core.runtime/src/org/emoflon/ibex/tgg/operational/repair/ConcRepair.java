package org.emoflon.ibex.tgg.operational.repair;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.BrokenMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.strategies.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchAnalyzer.ConstrainedAttributeChanges;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;

public class ConcRepair implements TimeMeasurable {

	protected Times times;

	protected final INTEGRATE opStrat;

	protected ShortcutRepairStrategy shortcutRepairStrat;
	protected AttributeRepairStrategy attributeRepairStrat;

	protected boolean strategiesInitialized;

	protected static final PatternType[] shortcutPatternTypes = //
			{ PatternType.FWD, PatternType.BWD, PatternType.CC, PatternType.SRC, PatternType.TRG };

	protected BrokenMatchContainer dependencyContainer;

	public ConcRepair(INTEGRATE opStrat) {
		this.opStrat = opStrat;
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

			this.shortcutRepairStrat = new ShortcutRepairStrategy(opStrat, shortcutPatternTypes);
			this.attributeRepairStrat = new AttributeRepairStrategy(opStrat);

			times.addTo("initializeStrategies", Timer.stop());
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "Repair: init strategies - done\n");
		}
	}

	public boolean repairBrokenMatches() {
		initializeStrategies();

		Timer.start();

		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();
		dependencyContainer.reset();
		opStrat.getMatchHandler().getBrokenMatches().stream() //
				.filter(m -> {
					DeletionPattern pattern = opStrat.matchClassifier().get(m).getDeletionPattern();
					DomainModification srcModType = pattern.getModType(DomainType.SRC, BindingType.CREATE);
					DomainModification trgModType = pattern.getModType(DomainType.TRG, BindingType.CREATE);
					return !(srcModType == DomainModification.COMPL_DEL && trgModType == DomainModification.UNCHANGED || //
					srcModType == DomainModification.UNCHANGED && trgModType == DomainModification.COMPL_DEL);
				}) //
				.forEach(dependencyContainer::addMatch);

		boolean processedOnce = true;
		while (processedOnce) {
			processedOnce = false;
			while (!dependencyContainer.isEmpty()) {
				processedOnce = true;
				ITGGMatch repairCandidate = dependencyContainer.getNext();

				if (alreadyProcessed.contains(repairCandidate))
					continue;

				boolean repairedSth = false;
				ClassifiedMatch classifiedMatch = opStrat.matchClassifier().get(repairCandidate);

				ITGGMatch repairedMatch = repairAttributes(classifiedMatch);
				if (repairedMatch != null) {
					repairedSth = true;
				}

				repairedMatch = repairViaShortcut(classifiedMatch);
				if (repairedMatch != null) {
					repairedSth = true;

					opStrat.getMatchHandler().removeBrokenRuleApplication(repairCandidate.getRuleApplicationNode());
					opStrat.precedenceGraph().removeMatch(repairCandidate);
					opStrat.getMatchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
					opStrat.precedenceGraph().notifyAddedMatch(repairedMatch);
					opStrat.precedenceGraph().notifyRemovedMatch(repairedMatch);
					alreadyProcessed.add(repairedMatch);
				}

				if (repairedSth)
					alreadyProcessed.add(repairCandidate);
				dependencyContainer.matchApplied(repairCandidate);
			}
			alreadyProcessed.addAll(opStrat.getMatchHandler().getBrokenMatches());
			opStrat.getOptions().matchDistributor().updateMatches();

			Timer.start();
			opStrat.matchClassifier().clearAndClassifyAll(opStrat.getMatchHandler().getBrokenMatches());
			times.subtractFrom("repairBrokenMatches", Timer.stop());

			opStrat.getMatchHandler().getBrokenMatches().stream() //
					.filter(m -> !alreadyProcessed.contains(m)) //
					.forEach(dependencyContainer::addMatch);
		}

		times.addTo("repairBrokenMatches", Timer.stop());
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "");

		return !alreadyProcessed.isEmpty();
	}

	private ITGGMatch repairAttributes(ClassifiedMatch classifiedMatch) {
		Set<ConstrainedAttributeChanges> attrChanges = classifiedMatch.getConstrainedAttrChanges();
		if (attrChanges.isEmpty())
			return null;

		boolean repairedSth = false;
		for (ConstrainedAttributeChanges attrCh : attrChanges) {
			boolean srcChange = false;
			boolean trgChange = false;
			for (TGGAttributeExpression param : attrCh.affectedParams.keySet()) {
				switch (param.getObjectVar().getDomainType()) {
					case SRC -> srcChange = true;
					case TRG -> trgChange = true;
					default -> {
					}
				}
			}
			if (srcChange ^ trgChange) {
				PatternType type = srcChange ? PatternType.FWD : PatternType.BWD;
				List<TGGAttributeConstraint> constraints = new LinkedList<>();
				constraints.add(attrCh.constraint);
				ITGGMatch repairedMatch = attributeRepairStrat.repair(constraints, classifiedMatch.getMatch(), type);
				if (repairedMatch != null)
					repairedSth = true;
			}
		}

		return repairedSth ? classifiedMatch.getMatch() : null;
	}

	private ITGGMatch repairViaShortcut(ClassifiedMatch classifiedMatch) {
		DeletionType delType = classifiedMatch.getDeletionType();
		if (DeletionType.getShortcutCCCandidates().contains(delType)) {
			return shortcutRepairStrat.repair(classifiedMatch.getMatch(), PatternType.CC);
		} else if (DeletionType.getShortcutPropCandidates().contains(delType)) {
			PatternType type = delType == DeletionType.SRC_PARTLY_TRG_NOT ? PatternType.FWD : PatternType.BWD;
			ITGGMatch repairedMatch = shortcutRepairStrat.repair(classifiedMatch.getMatch(), type);
			if (repairedMatch == null) {
				repairedMatch = shortcutRepairStrat.repair(classifiedMatch.getMatch(), PatternType.CC);
			}
			return repairedMatch;
		}
		return null;
	}

	public ITGGMatch attributeRepairOneMatch(ITGGMatch repairCandidate, PatternType type) {
		initializeStrategies();

		return repairOneMatch(attributeRepairStrat, repairCandidate, type);
	}

	public ITGGMatch shortcutRepairOneMatch(ITGGMatch repairCandidate, PatternType type) {
		initializeStrategies();

		return repairOneMatch(shortcutRepairStrat, repairCandidate, type);
	}

	private ITGGMatch repairOneMatch(RepairStrategy repairStrat, ITGGMatch repairCandidate, PatternType type) {
		ITGGMatch repairedMatch = null;
		if (type != null)
			repairedMatch = repairStrat.repair(repairCandidate, type);

		if (repairedMatch != null) {
			opStrat.getMatchHandler().removeBrokenRuleApplication(repairCandidate.getRuleApplicationNode());
			opStrat.precedenceGraph().removeMatch(repairCandidate);
			opStrat.getMatchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
			opStrat.precedenceGraph().notifyAddedMatch(repairedMatch);
			opStrat.precedenceGraph().notifyRemovedMatch(repairedMatch);
		}

		return repairedMatch;
	}

	public RepairableMatch isShortcutRepairable(ITGGMatch repairCandidate, ITGGMatch replacingMatch) {
		initializeStrategies();

		return shortcutRepairStrat.isRepairable(repairCandidate, replacingMatch);
	}

	@Override
	public Times getTimes() {
		return times;
	}

}
