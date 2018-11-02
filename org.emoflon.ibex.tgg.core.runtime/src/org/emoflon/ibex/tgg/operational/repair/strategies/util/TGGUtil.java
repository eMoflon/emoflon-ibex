package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import java.util.Collection;
import java.util.stream.Collectors;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

/**
 * A utility class to filter sets of TGGRuleElements.
 * 
 * @author lfritsche
 *
 */
public class TGGUtil {
	
	public static Collection<TGGRuleNode> filterNodes(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleNode).map(e -> (TGGRuleNode) e).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleEdge> filterEdges(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleEdge).map(e -> (TGGRuleEdge) e).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleNode> filterNodes(Collection<TGGRuleNode> elements, BindingType type) {
		return elements.stream().filter(e -> e.getBindingType().equals(type)).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleEdge> filterEdges(Collection<TGGRuleEdge> elements, BindingType type) {
		return elements.stream().filter(e -> e.getBindingType().equals(type)).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleNode> filterNodes(Collection<TGGRuleNode> elements, DomainType type) {
		return elements.stream().filter(e -> e.getDomainType().equals(type)).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleEdge> filterEdges(Collection<TGGRuleEdge> elements, DomainType type) {
		return elements.stream().filter(e -> e.getDomainType().equals(type)).collect(Collectors.toList());
	}
	
	public static Collection<TGGRuleNode> filterNodes(Collection<TGGRuleNode> elements, DomainType dType, BindingType bType) {
		return filterNodes(filterNodes(elements, bType), dType);
	}
	
	public static Collection<TGGRuleEdge> filterEdges(Collection<TGGRuleEdge> elements, DomainType dType, BindingType bType) {
		return filterEdges(filterEdges(elements, bType), dType);
	}
	
	public static boolean isAxiomatic(TGGRule rule) {
		return rule.getNodes().stream().noneMatch(n -> n.getBindingType() == BindingType.CONTEXT) && 
				rule.getEdges().stream().noneMatch(e -> e.getBindingType() == BindingType.CONTEXT);
	}

}
