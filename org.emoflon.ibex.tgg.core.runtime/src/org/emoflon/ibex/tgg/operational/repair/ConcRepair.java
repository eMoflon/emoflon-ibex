package org.emoflon.ibex.tgg.operational.repair;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.operational.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.operational.benchmark.Timer;
import org.emoflon.ibex.tgg.operational.benchmark.Times;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.BrokenMatchContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.BasicShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.HigherOrderShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternProvider;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.ShortcutApplicationPointFinder;
import org.emoflon.ibex.tgg.operational.repair.strategies.AttributeRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy;
import org.emoflon.ibex.tgg.operational.repair.strategies.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionPattern;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DomainModification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchAnalyzer.ConstrainedAttributeChanges;

import language.BindingType;
import language.DomainType;
import language.TGGAttributeConstraint;
import language.TGGAttributeExpression;

public class ConcRepair implements TimeMeasurable {

	protected Times times;

	protected final INTEGRATE opStrat;

	protected ShortcutApplicationPointFinder scApplPointFinder;
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

			this.scApplPointFinder = new ShortcutApplicationPointFinder(opStrat.precedenceGraph(), opStrat.matchClassifier());
			ShortcutPatternProvider shortcutPatternProvider = initShortcutPatternProvider(opStrat.getOptions());
			this.shortcutRepairStrat = new ShortcutRepairStrategy(opStrat.getOptions(), //
					opStrat.getGreenInterpreter(), opStrat.getRedInterpreter(), shortcutPatternProvider);
			this.attributeRepairStrat = new AttributeRepairStrategy(opStrat);

			times.addTo("initializeStrategies", Timer.stop());
			LoggerConfig.log(LoggerConfig.log_repair(), () -> "Repair: init strategies - done\n");
		}
	}

	private ShortcutPatternProvider initShortcutPatternProvider(IbexOptions options) {
		if (options.repair.usePGbasedSCruleCreation()) {
			return new HigherOrderShortcutPatternProvider(options, //
					opStrat.precedenceGraph(), opStrat.matchUtils(), shortcutPatternTypes, true);
		} else {
			return new BasicShortcutPatternProvider(options, shortcutPatternTypes, true);
		}
	}

	public boolean repairBrokenMatches() {
		initializeStrategies();

		Timer.start();

		boolean usePGbasedSCruleCreation = opStrat.getOptions().repair.usePGbasedSCruleCreation();
		Collection<ITGGMatch> alreadyProcessed = cfactory.createObjectSet();

		repairBrokenMatches(usePGbasedSCruleCreation, alreadyProcessed);

		times.addTo("repairBrokenMatches", Timer.stop());
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "");

		return !alreadyProcessed.isEmpty();
	}

	private void repairBrokenMatches(boolean usePGbasedSCruleCreation, Collection<ITGGMatch> alreadyProcessed) {
		dependencyContainer.reset();
		opStrat.getMatchHandler().getBrokenMatches().stream() //
				.filter(this::filterRepairCandidates) //
				.forEach(dependencyContainer::addMatch);

		Map<ITGGMatch, ShortcutApplicationPoint> applPoints = null;
		if (usePGbasedSCruleCreation) {
			applPoints = scApplPointFinder.searchForShortcutApplications().stream() //
					.collect(Collectors.toMap(p -> p.getApplicationMatch(), p -> p));
		}

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

				Collection<ITGGMatch> repairedMatches = repairAttributes(classifiedMatch);
				if (repairedMatches != null) {
					repairedSth = true;
				}

				if (usePGbasedSCruleCreation) {
					if (applPoints.containsKey(repairCandidate))
						repairedMatches = repairViaShortcut(applPoints.get(repairCandidate));
					else
						repairedMatches = null;
				} else {
					repairedMatches = repairViaShortcut(classifiedMatch);
				}
				if (repairedMatches != null) {
					repairedSth = true;
					alreadyProcessed.addAll(repairedMatches);
				}

				if (repairedSth)
					alreadyProcessed.add(repairCandidate);
				dependencyContainer.matchApplied(repairCandidate);
			}
			alreadyProcessed.addAll(opStrat.getMatchHandler().getBrokenMatches());
			opStrat.getOptions().matchDistributor().updateMatches();

			if (usePGbasedSCruleCreation) {
				applPoints = scApplPointFinder.searchForShortcutApplications().stream() //
						.collect(Collectors.toMap(p -> p.getApplicationMatch(), p -> p));
			} else {
				opStrat.matchClassifier().clearAndClassifyAll(opStrat.getMatchHandler().getBrokenMatches());
			}

			opStrat.getMatchHandler().getBrokenMatches().stream() //
					.filter(m -> !alreadyProcessed.contains(m)) //
					// TODO shouldn't be added a pattern filter here?
					.forEach(dependencyContainer::addMatch);
		}
	}

	private boolean filterRepairCandidates(ITGGMatch match) {
		DeletionPattern pattern = opStrat.matchClassifier().get(match).getDeletionPattern();
		DomainModification srcModType = pattern.getModType(DomainType.SRC, BindingType.CREATE);
		DomainModification trgModType = pattern.getModType(DomainType.TRG, BindingType.CREATE);
		return !(srcModType == DomainModification.COMPL_DEL && trgModType == DomainModification.UNCHANGED || //
				srcModType == DomainModification.UNCHANGED && trgModType == DomainModification.COMPL_DEL);
	}

	private Collection<ITGGMatch> repairAttributes(ClassifiedMatch classifiedMatch) {
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
				RepairApplicationPoint applPoint = new RepairApplicationPoint(classifiedMatch.getMatch(), type);
				Collection<ITGGMatch> repairedMatches = attributeRepairStrat.repair(constraints, applPoint);
				if (repairedMatches != null)
					repairedSth = true;
			}
		}

		return repairedSth ? Collections.singletonList(classifiedMatch.getMatch()) : null;
	}

	private Collection<ITGGMatch> repairViaShortcut(ClassifiedMatch classifiedMatch) {
		DeletionType delType = classifiedMatch.getDeletionType();
		RepairApplicationPoint applPoint = null;
		Collection<ITGGMatch> repairedMatches = null;

		if (DeletionType.shortcutCCCandidates.contains(delType)) {
			applPoint = new RepairApplicationPoint(classifiedMatch.getMatch(), PatternType.CC);
			repairedMatches = shortcutRepairStrat.repair(applPoint);
		} else if (DeletionType.shortcutPropCandidates.contains(delType)) {
			PatternType type = delType == DeletionType.SRC_PARTLY_TRG_NOT ? PatternType.FWD : PatternType.BWD;
			applPoint = new RepairApplicationPoint(classifiedMatch.getMatch(), type);
			repairedMatches = shortcutRepairStrat.repair(applPoint);
			if (repairedMatches == null) {
				applPoint = new RepairApplicationPoint(classifiedMatch.getMatch(), PatternType.CC);
				repairedMatches = shortcutRepairStrat.repair(applPoint);
			}
		}

		if (repairedMatches != null)
			processRepairedMatches(applPoint, repairedMatches);
		return repairedMatches;
	}

	private Collection<ITGGMatch> repairViaShortcut(ShortcutApplicationPoint applPoint) {
		Collection<ITGGMatch> repairedMatches = shortcutRepairStrat.repair(applPoint);
		if (repairedMatches != null)
			processRepairedMatches(applPoint, repairedMatches);
		return repairedMatches;
	}

	public Collection<ITGGMatch> attributeRepairOneMatch(ITGGMatch repairCandidate, PatternType type) {
		initializeStrategies();

		return repairOneMatch(attributeRepairStrat, repairCandidate, type);
	}

	public Collection<ITGGMatch> shortcutRepairOneMatch(ITGGMatch repairCandidate, PatternType type) {
		initializeStrategies();

		return repairOneMatch(shortcutRepairStrat, repairCandidate, type);
	}

	private Collection<ITGGMatch> repairOneMatch(RepairStrategy repairStrat, ITGGMatch repairCandidate, PatternType type) {
		RepairApplicationPoint applPoint = new RepairApplicationPoint(repairCandidate, type);
		Collection<ITGGMatch> repairedMatches = repairStrat.repair(applPoint);

		if (repairedMatches != null)
			processRepairedMatches(applPoint, repairedMatches);

		return repairedMatches;
	}

	public RepairableMatch isShortcutRepairable(ITGGMatch repairCandidate, ITGGMatch replacingMatch) {
		initializeStrategies();

		return shortcutRepairStrat.isRepairable(repairCandidate, replacingMatch);
	}

	private void processRepairedMatches(RepairApplicationPoint applPoint, Collection<ITGGMatch> repairedMatches) {
		if (applPoint instanceof ShortcutApplicationPoint scApplPoint) {
			for (PrecedenceNode originalNode : scApplPoint.getOriginalNodes()) {
				opStrat.getMatchHandler().removeBrokenRuleApplication(originalNode.getMatch().getRuleApplicationNode());
				opStrat.precedenceGraph().removeMatch(originalNode.getMatch());
			}
		} else {
			opStrat.getMatchHandler().removeBrokenRuleApplication(applPoint.getApplicationMatch().getRuleApplicationNode());
			opStrat.precedenceGraph().removeMatch(applPoint.getApplicationMatch());
		}

		for (ITGGMatch repairedMatch : repairedMatches) {
			opStrat.getMatchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
			opStrat.precedenceGraph().notifyAddedMatch(repairedMatch);
			opStrat.precedenceGraph().notifyRemovedMatch(repairedMatch);
		}
	}

	@Override
	public Times getTimes() {
		return times;
	}

}