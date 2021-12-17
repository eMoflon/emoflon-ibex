package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.ComponentSpecificRuleElement;
import org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder.HigherOrderTGGRule.HigherOrderRuleComponent;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

import language.TGGRule;
import language.TGGRuleElement;

public class HigherOrderTGGRuleFactory {

	private final PrecedenceGraph pg;
	private final TGGMatchUtilProvider mu;

	public HigherOrderTGGRuleFactory(PrecedenceGraph pg, TGGMatchUtilProvider mu) {
		this.pg = pg;
		this.mu = mu;
	}

	public HigherOrderTGGRule createHigherOrderTGGRuleFromConsNodes(List<PrecedenceNode> nodes) {
		validateInputNodes(nodes);

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode node : nodes) {
			TGGMatchUtil matchUtil = mu.get(node.getMatch());
			TGGRule rule = matchUtil.getRule();
			Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = nodes.indexOf(node) == 0 //
					? new HashMap<>()
					: createMappingForCons(higherOrderRule, node, matchUtil);
			higherOrderRule.addComponentLast(rule, node.getMatch(), contextMapping);
		}

		return higherOrderRule;
	}

	private Map<TGGRuleElement, ComponentSpecificRuleElement> createMappingForCons(HigherOrderTGGRule higherOrderRule, PrecedenceNode node,
			TGGMatchUtil matchUtil) {
		Map<TGGRuleElement, ComponentSpecificRuleElement> contextMapping = new HashMap<>();

		Set<Object> objects = matchUtil.getObjects(new EltFilter().srcAndTrg().context());
		for (Object obj : objects) {
			TGGRuleElement ruleElement = matchUtil.getElement(obj);
			Set<PrecedenceNode> mappedNodes = pg.getNodesTranslating(obj);
			for (PrecedenceNode mappedNode : mappedNodes) {
				if (mappedNode.getMatch().getType() != PatternType.CONSISTENCY)
					continue;

				HigherOrderRuleComponent mappedComponent = higherOrderRule.getComponent(mappedNode.getMatch());
				if (mappedComponent == null)
					continue;

				TGGMatchUtil mappedMatchUtil = mu.get(mappedNode.getMatch());
				TGGRuleElement mappedRuleElement = mappedMatchUtil.getElement(obj);
				contextMapping.put(ruleElement, new ComponentSpecificRuleElement(mappedRuleElement, mappedComponent));
				break;
			}
		}

		return contextMapping;
	}

	public HigherOrderTGGRule createHigherOrderTGGRuleFromSrcTrgNodes(List<PrecedenceNode> nodes) {
		validateInputNodes(nodes);

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		// TODO continue

		return higherOrderRule;
	}

	private void validateInputNodes(List<PrecedenceNode> nodes) {
		if (nodes.isEmpty())
			throw new RuntimeException("There must be at least one simple rule to create a higher-order TGG rule!");
	}

}
