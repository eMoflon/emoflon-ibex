package org.emoflon.ibex.tgg.operational.repair.shortcut.higherorder;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtilProvider;

import language.TGGRule;

public class HigherOrderTGGRuleFactory {

	private final PrecedenceGraph pg;
	private final TGGMatchUtilProvider mu;

	public HigherOrderTGGRuleFactory(PrecedenceGraph pg, TGGMatchUtilProvider mu) {
		this.pg = pg;
		this.mu = mu;
	}

	public HigherOrderTGGRule createHigherOrderTGGRule(List<PrecedenceNode> nodes) {
		if (nodes.isEmpty())
			throw new RuntimeException("There must be at least one simple rule to create a higher-order TGG rule!");

		HigherOrderTGGRule higherOrderRule = new HigherOrderTGGRule();
		for (PrecedenceNode node : nodes) {
			TGGMatchUtil matchUtil = mu.get(node.getMatch());
			TGGRule rule = matchUtil.getRule();

			// TODO create mapping
		}

		return higherOrderRule;
	}

}
