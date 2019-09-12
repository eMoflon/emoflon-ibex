package org.emoflon.ibex.tgg.operational.repair.strategies.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.NAC;
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
	
	public static boolean isNac(String ruleName, IbexOptions options) {
		
		for (TGGRule r : options.flattenedTGG().getRules())
			if (r.getName().equals(ruleName))
				return false;
		
		for (TGGRule r : options.flattenedTGG().getRules())
			for (NAC n : r.getNacs())
				if (n.getName().contentEquals(ruleName))
					return true;
		
		throw new IllegalStateException("Could not find " + ruleName + " in the TGG.");
	}
	
	public static EList<TGGRule> getRulesForNac(String nacName, IbexOptions options) {
		
		EList<TGGRule> result = new BasicEList<TGGRule>();
		for (TGGRule r : options.flattenedTGG().getRules()) {
			if (r.getNacs().stream().anyMatch(n -> n.getName().contentEquals(nacName))) {
				result.add(r);
			}
		}
		return result;
	}
	
	public static <T> Set<T> intersect(Set<T> a, Set<T> b) {
		Set<T> result = new HashSet<>();
		
		if (a.size() < b.size())
			for (T elem : a) {
				if (b.contains(elem))
					result.add(elem);
			}
		else {
			for (T elem : b) {
				if (a.contains(elem))
					result.add(elem);
			}
		}
		
		return result;
	}
}
