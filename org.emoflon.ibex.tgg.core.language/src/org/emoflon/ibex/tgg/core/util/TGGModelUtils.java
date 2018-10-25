package org.emoflon.ibex.tgg.core.util;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TGGModelUtils {

	public static List<TGGRuleNode> getNodesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getNodes().stream() //
				.filter(n -> type.equals(n.getBindingType())).sorted((a, b) -> a.getName().compareTo(b.getName())) //
				.collect(Collectors.toList());
	}

	public static List<TGGRuleEdge> getEdgesByOperator(TGGRule rule, BindingType type) {
		Objects.requireNonNull(rule, "The rule must not be null!");
		return rule.getEdges().stream() //
				.filter(n -> type.equals(n.getBindingType())).sorted((a, b) -> a.getName().compareTo(b.getName())) //
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

	public static Collection<TGGRuleNode> getNodesByOperatorAndDomain(TGGRule rule, BindingType type,
			DomainType domain) {
		return getNodesByOperator(rule, type)//
				.stream()//
				.filter(n -> n.getDomainType().equals(domain))//
				.collect(Collectors.toList());
	}

	public static String getMarkerTypeName(String ruleName) {
		return ruleName + "__" + "Marker";
	}

	public static String getMarkerRefName(BindingType type, DomainType domain, String markedOVName) {
		return getMarkerRefNamePrefix(type, domain) + markedOVName;
	}
	
	public static String getMarkerRefNamePrefix(BindingType type, DomainType domain) {
		return type.getLiteral() + "__" + domain.getLiteral() + "__";
	}

	public static Collection<? extends TGGRuleEdge> getEdgesByOperatorAndDomain(TGGRule rule, BindingType type,
			DomainType domain) {
		return getEdgesByOperator(rule, type)//
				.stream()//
				.filter(e -> e.getDomainType().equals(domain))//
				.collect(Collectors.toList());
	}

	public static boolean isOfDomain(NAC nac, DomainType domain) {
		return nac.getNodes().stream().anyMatch(n -> n.getDomainType().equals(domain));
	}
}
