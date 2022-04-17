package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.IGreenInterpreter;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.ShortcutPatternTool;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.ShortcutApplicationPointFinder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapCategory;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil.FixedMappings;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

import language.DomainType;
import language.TGGRuleElement;

public class HigherOrderShortcutRepairStrategy {

	private final IbexOptions options;

	private final PrecedenceGraph pg;
	private final TGGMatchUtilProvider mup;
	private final IGreenInterpreter greenInterpreter;
	private final IRedInterpreter redInterpreter;

	private final ShortcutApplicationPointFinder scApplPointFinder;
	private final HigherOrderTGGRuleFactory ruleFactory;
	private final OverlapUtil overlapUtil;

	public HigherOrderShortcutRepairStrategy(IbexOptions options, PrecedenceGraph pg, MatchClassifier mc, TGGMatchUtilProvider mup, //
			IGreenInterpreter greenInterpreter, IRedInterpreter redInterpreter) {
		this.options = options;

		this.pg = pg;
		this.mup = mup;
		this.greenInterpreter = greenInterpreter;
		this.redInterpreter = redInterpreter;

		this.scApplPointFinder = new ShortcutApplicationPointFinder(pg, mc);
		this.ruleFactory = new HigherOrderTGGRuleFactory(options, pg, mup);
		this.overlapUtil = new OverlapUtil(options);
	}

	public void repair() {
		Set<ShortcutApplicationPoint> shortcutApplPoints = scApplPointFinder.searchForShortcutApplications();
		for (ShortcutApplicationPoint applPoint : shortcutApplPoints) {
			Collection<ITGGMatch> repairedMatches = repairAtApplicationPoint(applPoint);
			if (repairedMatches != null) {
				for (PrecedenceNode originalNode : applPoint.originalNodes) {
					options.matchHandler().removeBrokenRuleApplication(originalNode.getMatch().getRuleApplicationNode());
					pg.removeMatch(originalNode.getMatch());
				}
				for (ITGGMatch repairedMatch : repairedMatches) {
					options.matchHandler().addBrokenRuleApplication(repairedMatch.getRuleApplicationNode(), repairedMatch);
					pg.notifyAddedMatch(repairedMatch);
					pg.notifyRemovedMatch(repairedMatch);
				}
			}
		}
	}

	private Collection<ITGGMatch> repairAtApplicationPoint(ShortcutApplicationPoint applPoint) {
		DomainType propagationDomain = switch (applPoint.propagationType) {
			case SRC -> DomainType.SRC;
			case TRG -> DomainType.TRG;
			default -> throw new RuntimeException("Unexpected propagation type: " + applPoint.propagationType);
		};

		HigherOrderTGGRule originalHigherOrderRule = ruleFactory.createHigherOrderTGGRuleFromConsMatches(applPoint.originalNodes);
		Set<HigherOrderTGGRule> replacingHigherOrderRules = applPoint.setOfReplacingNodes.stream() //
				.map(n -> ruleFactory.createHigherOrderTGGRuleFromSrcTrgNodes(n, propagationDomain)) //
				.collect(Collectors.toSet());

		Set<TGGOverlap> overlaps = new HashSet<>();
		replacingHigherOrderRules.forEach( //
				replacingHigherOrderRule -> createHigherOrderTGGOverlaps(originalHigherOrderRule, replacingHigherOrderRule, applPoint, overlaps));

		Collection<ShortcutRule> shortcutRules = overlaps.stream() //
				.map(overlap -> new ShortcutRule(overlap, options)) //
				.collect(Collectors.toList());

		PatternType shortcutPatternType = switch (applPoint.propagationType) {
			case SRC -> PatternType.FWD;
			case TRG -> PatternType.BWD;
			default -> throw new IllegalArgumentException("Unexpected value: " + applPoint.propagationType);
		};
		ShortcutPatternTool shortcutPatternTool = new ShortcutPatternTool( //
				options, greenInterpreter, redInterpreter, shortcutRules, Collections.singleton(shortcutPatternType), true);

		Collection<ITGGMatch> repairedMatches = shortcutPatternTool.processBrokenMatch(shortcutPatternType, applPoint.applNode.getMatch());
		if (repairedMatches != null)
			logSuccessfulRepair(applPoint.applNode.getMatch(), repairedMatches);
		return repairedMatches;
	}

	private void createHigherOrderTGGOverlaps(HigherOrderTGGRule originalHigherOrderRule, HigherOrderTGGRule replacingHigherOrderRule,
			ShortcutApplicationPoint applPoint, Set<TGGOverlap> overlaps) {
		Set<TGGRuleElement> originalElts = new HashSet<>();
		Set<TGGRuleElement> replacingElts = new HashSet<>();
		Map<TGGRuleElement, TGGRuleElement> mappings = new HashMap<>();

		Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> pgNodeOverlaps = applPoint.original2replacingNodesOverlaps;
		for (PrecedenceNode originalPGNode : pgNodeOverlaps.keySet()) {
			HigherOrderRuleComponent originalComponent = originalHigherOrderRule.getComponent(originalPGNode.getMatch());
			if (originalComponent == null)
				throw new RuntimeException("Internal error: there must exist a component for this PG-node!");
			Map<PrecedenceNode, Set<Object>> replacingPGNodes = pgNodeOverlaps.get(originalPGNode);
			for (PrecedenceNode replacingPGNode : replacingPGNodes.keySet()) {
				HigherOrderRuleComponent replacingComponent = replacingHigherOrderRule.getComponent(replacingPGNode.getMatch());
				if (replacingComponent == null)
					continue;
				Set<Object> sharingObjects = replacingPGNodes.get(replacingPGNode);
				for (Object sharingObject : sharingObjects) {
					TGGRuleElement higherOrderOriginalElt = extractHigherOrderElement(originalComponent, originalPGNode, sharingObject);
					TGGRuleElement higherOrderReplacingElt = extractHigherOrderElement(replacingComponent, replacingPGNode, sharingObject);

					originalElts.add(higherOrderOriginalElt);
					replacingElts.add(higherOrderReplacingElt);
					mappings.put(higherOrderOriginalElt, higherOrderReplacingElt);
				}
			}
		}

		FixedMappings fixedMappings = new FixedMappings(originalElts, replacingElts, mappings);
		overlapUtil.calculateOverlaps(originalHigherOrderRule, replacingHigherOrderRule, fixedMappings, OverlapCategory.OTHER, overlaps);
	}

	private TGGRuleElement extractHigherOrderElement(HigherOrderRuleComponent component, PrecedenceNode pgNode, Object sharingObject) {
		TGGMatchUtil mu = mup.get(pgNode.getMatch());
		TGGRuleElement element = mu.getElement(sharingObject);
		ComponentSpecificRuleElement componentSpecRuleElt = component.getComponentSpecificRuleElement(element);
		return componentSpecRuleElt.getRespectiveHigherOrderElement();
	}

	private void logSuccessfulRepair(ITGGMatch repairCandidate, Collection<ITGGMatch> repairedMatches) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> {
			StringBuilder b = new StringBuilder();
			b.append("  '-> repaired: '" + repairCandidate.getPatternName() + "' -> '");
			b.append(String.join("--", repairedMatches.stream().map(m -> m.getPatternName()).toList()));
			b.append("'");
			return b.toString();
		});
	}

}
