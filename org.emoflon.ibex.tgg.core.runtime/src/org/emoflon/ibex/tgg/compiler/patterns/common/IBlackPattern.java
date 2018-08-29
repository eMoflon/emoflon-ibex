package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;

import language.TGGAttributeConstraint;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public interface IBlackPattern {
	String getName();
	
	BlackPatternFactory getPatternFactory();
	
	Collection<TGGRuleNode> getSignatureNodes();
	Collection<TGGRuleNode> getLocalNodes();
	Collection<TGGRuleEdge> getLocalEdges();
	Collection<TGGRuleCorr> getAllCorrNodes();
	Collection<TGGRuleNode> getAllNodes();
	
	Collection<PatternInvocation> getPositiveInvocations();
	Collection<PatternInvocation> getNegativeInvocations();

	Collection<Pair<TGGRuleNode, TGGRuleNode>> getInjectivityChecks();
	
	Collection<TGGAttributeConstraint> getAttributeConstraints();

	void addNegativeInvocation(IBlackPattern markedPattern, Map<TGGRuleNode, TGGRuleNode> mapping);
	void addPositiveInvocation(IBlackPattern markedPattern, Map<TGGRuleNode, TGGRuleNode> mapping);
	
	default TGGRuleNode getNode(String nodeName) {
		return Stream.concat(getAllNodes().stream(), getAllCorrNodes().stream())
			  .filter(n -> n.getName().equals(nodeName))
			  .findAny()
			  .orElseThrow(() -> new IllegalStateException("Not able to find " + nodeName + " in " + getName()));
	}
}
