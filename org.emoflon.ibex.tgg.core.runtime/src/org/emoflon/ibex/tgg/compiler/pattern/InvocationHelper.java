package org.emoflon.ibex.tgg.compiler.pattern;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRuleElement;

public class InvocationHelper {
	
	public static Map<TGGRuleElement, TGGRuleElement> getTGGVariableMapping(IbexPattern rootPattern, IbexPattern invocationpattern) {
		Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
		Set<TGGRuleElement> rootElements = Stream.concat(rootPattern.getSignatureElements().stream(), rootPattern.getBodyNodes().stream()).collect(Collectors.toSet());
//		Set<TGGRuleElement> invocationElements = Stream.concat(invocationpattern.getSignatureElements().stream(), invocationpattern.getBodyNodes().stream()).collect(Collectors.toSet());
		Collection<TGGRuleElement> invocationElements = invocationpattern.getSignatureElements();

		// map invocation elements to root elements based on their name
		invocationElements.stream().forEach(iEl -> mapping.put(rootElements.stream().filter(rEl -> rEl.getName().equals(iEl.getName())).findFirst().get(), iEl));
		return mapping;
	}
	
	public static Collection<TGGRuleElement> getUnmappedElements(IbexPattern invocationpattern, Collection<TGGRuleElement> mappedElements) {
		Set<TGGRuleElement> invocationElements = Stream.concat(invocationpattern.getSignatureElements().stream(), invocationpattern.getBodyNodes().stream()).collect(Collectors.toSet());
		return invocationElements.stream().filter(invEl -> !mappedElements.contains(invEl)).collect(Collectors.toList());
	}
	
	public static IbexPattern getMarkedPattern(List<MarkedPattern> markedPatterns, DomainType domain, boolean local) {
		return markedPatterns.stream().map(p -> (MarkedPattern) p).filter(p -> p.getDomain().equals(domain) && !(p.isLocal() ^ local)).findFirst().get();
	}
}
