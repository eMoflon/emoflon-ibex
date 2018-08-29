package org.emoflon.ibex.tgg.core.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TGGModelUtils {

	public static List<TGGRuleNode> getNodesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getNodes().stream() //
				.filter(n -> type.equals(n.getBindingType()))
				.sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	public static List<TGGRuleEdge> getReferencesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getEdges().stream() //
				.filter(n -> type.equals(n.getBindingType()))
				.sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	/**
	 * Checks whether the rule is an axiom
	 * 
	 * @param rule The rule to check
	 * @return true if the rule only contains green nodes
	 */
	public static boolean ruleIsAxiom(TGGRule rule) {
		return rule.getNodes().stream().allMatch(n -> n.getBindingType() == BindingType.CREATE);
	}
}
