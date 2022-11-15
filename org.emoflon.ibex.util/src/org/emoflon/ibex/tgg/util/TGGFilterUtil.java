package org.emoflon.ibex.tgg.util;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

/**
 * A utility class to filter sets of TGGRuleElements.
 * 
 * @author lfritsche
 *
 */
public class TGGFilterUtil {

	public static Collection<TGGNode> filterNodes(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGNode).map(e -> (TGGNode) e).collect(Collectors.toList());
	}

	public static Collection<TGGEdge> filterEdges(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGEdge).map(e -> (TGGEdge) e).collect(Collectors.toList());
	}

	public static Collection<TGGNode> filterNodes(Collection<TGGNode> elements, BindingType type) {
		return elements.stream().filter(e -> e.getBindingType().equals(type)).collect(Collectors.toList());
	}

	public static Collection<TGGEdge> filterEdges(Collection<TGGEdge> elements, BindingType type) {
		return elements.stream().filter(e -> e.getBindingType().equals(type)).collect(Collectors.toList());
	}

	public static Collection<TGGNode> filterNodes(Collection<TGGNode> elements, DomainType type) {
		return elements.stream().filter(e -> e.getDomainType().equals(type)).collect(Collectors.toList());
	}

	public static Collection<TGGEdge> filterEdges(Collection<TGGEdge> elements, DomainType type) {
		return elements.stream().filter(e -> e.getDomainType().equals(type)).collect(Collectors.toList());
	}

	public static Collection<TGGNode> filterNodes(Collection<TGGNode> elements, DomainType dType, BindingType bType) {
		return elements.stream() //
				.filter(e -> e.getDomainType() == dType) //
				.filter(e -> e.getBindingType() == bType) //
				.collect(Collectors.toList());
	}

	public static Collection<TGGEdge> filterEdges(Collection<TGGEdge> elements, DomainType dType, BindingType bType) {
		return elements.stream() //
				.filter(e -> e.getDomainType() == dType) //
				.filter(e -> e.getBindingType() == bType) //
				.collect(Collectors.toList());
	}

	public static boolean isAxiomatic(TGGRule rule) {
		return rule.getNodes().stream().noneMatch(n -> n.getBindingType() == BindingType.CONTEXT)
				&& rule.getEdges().stream().noneMatch(e -> e.getBindingType() == BindingType.CONTEXT);
	}

}
