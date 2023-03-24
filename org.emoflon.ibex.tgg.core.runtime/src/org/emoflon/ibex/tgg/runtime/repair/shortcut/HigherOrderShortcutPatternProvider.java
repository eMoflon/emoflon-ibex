package org.emoflon.ibex.tgg.runtime.repair.shortcut;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRuleFactory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.ShortcutApplicationPoint;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.OverlapCategory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.OverlapUtil.FixedMappings;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.SCPersistence;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchUtilProvider;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class HigherOrderShortcutPatternProvider extends BasicShortcutPatternProvider {

	private final IGreenInterpreter greenInterpreter;
	private final TGGMatchUtilProvider mup;
	private final HigherOrderTGGRuleFactory ruleFactory;

	// to reuse generated Short-cut Rules, we utilize a two stage hashing of the application point, here
	// the original-nodes list is hashed first, and the replacing-nodes lists second:
	private final Map<String, Map<String, Collection<RuntimeShortcutRule>>> higherOrderShortcutRules;
	private final Map<String, Map<String, Map<PatternType, Collection<OperationalShortcutRule>>>> higherOrderShortcutPatterns;

	public HigherOrderShortcutPatternProvider(IbexOptions options, IGreenInterpreter greenInterpreter, PrecedenceGraph pg, TGGMatchUtilProvider mup, //
			PatternType[] types, boolean initiallyPersistShortcutRules) {
		super(options, greenInterpreter, types, initiallyPersistShortcutRules);

		this.greenInterpreter = greenInterpreter;
		this.mup = mup;
		this.ruleFactory = new HigherOrderTGGRuleFactory(options, pg, mup);

		this.higherOrderShortcutRules = new HashMap<>();
		this.higherOrderShortcutPatterns = new HashMap<>();
	}

	@Override
	public Collection<OperationalShortcutRule> getOperationalShortcutRules(RepairApplicationPoint applPoint) {
		Collection<OperationalShortcutRule> operationalShortcutRules = new LinkedList<>();

		if (applPoint instanceof ShortcutApplicationPoint scApplPoint)
			operationalShortcutRules.addAll(getHigherOrderOpShortcutRules(scApplPoint));

		operationalShortcutRules.addAll(super.getOperationalShortcutRules(applPoint));

		return operationalShortcutRules;
	}

	private Collection<OperationalShortcutRule> getHigherOrderOpShortcutRules(ShortcutApplicationPoint applPoint) {
		String originalNodesID = applPoint.getOriginalNodesID();
		Set<String> replacingNodesIDs = applPoint.getID2replacingNodes().keySet();

		// first, if not present, we generate the short-cut rules:
		Map<String, Collection<RuntimeShortcutRule>> replacingID2shortcutRules = higherOrderShortcutRules //
				.computeIfAbsent(originalNodesID, k -> new HashMap<>());

		SetView<String> missingReplacingNodeIDs = Sets.difference(replacingNodesIDs, replacingID2shortcutRules.keySet());
		Map<String, Set<RuntimeShortcutRule>> missingReplacingID2shortcutRules = generateHigherOrderShortcutRules(applPoint, missingReplacingNodeIDs);
		replacingID2shortcutRules.putAll(missingReplacingID2shortcutRules);

		// second, we operationalize the short-cut rules:
		Map<String, Map<PatternType, Collection<OperationalShortcutRule>>> replacingID2opRules = higherOrderShortcutPatterns //
				.computeIfAbsent(originalNodesID, k -> new HashMap<>());

		Collection<OperationalShortcutRule> generatedOpRules = new LinkedList<>();
		for (String replacingNodeID : replacingNodesIDs) {
			Map<PatternType, Collection<OperationalShortcutRule>> opRulesByPattern = replacingID2opRules //
					.computeIfAbsent(replacingNodeID, k -> new HashMap<>());

			Collection<RuntimeShortcutRule> shortcutRules = replacingID2shortcutRules.get(replacingNodeID);
			Collection<OperationalShortcutRule> opRules = opRulesByPattern //
					.computeIfAbsent(applPoint.getRepairType(), k -> generateHigherOrderShortcutPatterns(shortcutRules, k));
			generatedOpRules.addAll(opRules);
		}

		generatedOpRules.forEach(r -> opShortcutRule2patternMatcher.put(r, new LocalPatternSearch(r, options)));

		// FIXME start
		higherOrderShortcutRules.clear();
		higherOrderShortcutPatterns.clear();
		// FIXME end: deactivated caching HO-SC-rules, reason: see FIXME comment at
		// ShortcutApplicationTool#getEntryNodeElts

		return generatedOpRules;
	}

	private Collection<OperationalShortcutRule> generateHigherOrderShortcutPatterns(Collection<RuntimeShortcutRule> shortcutRules, PatternType repairType) {
		return opSCFactory.createOperationalRules(greenInterpreter, shortcutRules, repairType) //
				.values().stream() //
				.flatMap(c -> c.stream()) //
				.collect(Collectors.toList());
	}

	private Map<String, Set<RuntimeShortcutRule>> generateHigherOrderShortcutRules(ShortcutApplicationPoint applPoint, Set<String> replacingNodesIDs) {
		DomainType propagationDomain = switch (applPoint.getPropagationMatchType()) {
			case SOURCE -> DomainType.SOURCE;
			case TARGET -> DomainType.TARGET;
			default -> throw new RuntimeException("Unexpected propagation type: " + applPoint.getRepairType());
		};

		HigherOrderTGGRule originalHigherOrderRule = ruleFactory.createHigherOrderTGGRuleFromConsMatches(applPoint.getOriginalNodes());
		Map<String, HigherOrderTGGRule> replacingHigherOrderRules = applPoint.getID2replacingNodes().entrySet().stream() //
				.collect(Collectors.toMap(e -> e.getKey(), //
						e -> ruleFactory.createHigherOrderTGGRuleFromSrcTrgNodes(e.getValue(), propagationDomain)));

		Map<String, Set<TGGOverlap>> id2overlaps = new HashMap<>();
		replacingHigherOrderRules.forEach((id, replacingHigherOrderRule) -> {
			Set<TGGOverlap> currentOverlaps = id2overlaps.computeIfAbsent(id, k -> new HashSet<>());
			createHigherOrderTGGOverlaps(originalHigherOrderRule, replacingHigherOrderRule, applPoint, currentOverlaps);
		});

		Map<String, Set<RuntimeShortcutRule>> shortcutRules = new HashMap<>();
		id2overlaps.forEach((id, overlaps) -> shortcutRules.put(id, //
				overlaps.stream() //
						.map(overlap -> new RuntimeShortcutRule(overlap, options)) //
						.collect(Collectors.toSet()) //
		));

		return shortcutRules;
	}

	private void createHigherOrderTGGOverlaps(HigherOrderTGGRule originalHigherOrderRule, HigherOrderTGGRule replacingHigherOrderRule,
			ShortcutApplicationPoint applPoint, Set<TGGOverlap> overlaps) {
		Set<TGGRuleElement> originalElts = new HashSet<>();
		Set<TGGRuleElement> replacingElts = new HashSet<>();
		Map<TGGRuleElement, TGGRuleElement> mappings = new HashMap<>();

		Map<PrecedenceNode, Map<PrecedenceNode, Set<Object>>> pgNodeOverlaps = applPoint.getOriginal2replacingNodesOverlaps();
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

	@Override
	public void persistShortcutRules() {
		SCPersistence persistence = new SCPersistence(options);

		LinkedList<RuntimeShortcutRule> allShortcutRules = new LinkedList<>(basicShortcutRules);
		allShortcutRules.addAll( //
				higherOrderShortcutRules.values().stream() //
						.flatMap(m -> m.values().stream()) //
						.flatMap(c -> c.stream()) //
						.toList() //
		);

		persistence.saveSCRules(allShortcutRules);
		Map<String, Collection<OperationalShortcutRule>> fwdOpSCRs = basicShortcutPatterns.get(PatternType.FWD);
		if (fwdOpSCRs != null) {
			persistence.saveOperationalFWDSCRules(fwdOpSCRs.values().stream() //
					.flatMap(c -> c.stream()).collect(Collectors.toList()));
		}
		Map<String, Collection<OperationalShortcutRule>> bwdOpSCRs = basicShortcutPatterns.get(PatternType.BWD);
		if (bwdOpSCRs != null) {
			persistence.saveOperationalBWDSCRules(bwdOpSCRs.values().stream() //
					.flatMap(c -> c.stream()).collect(Collectors.toList()));
		}
	}

}
