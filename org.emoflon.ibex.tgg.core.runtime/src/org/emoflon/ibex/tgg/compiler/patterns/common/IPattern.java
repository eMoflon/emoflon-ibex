package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public interface IPattern {
	String getName();
	
	Collection<TGGRuleNode> getSignatureNodes();
	Collection<TGGRuleNode> getLocalNodes();
	Collection<TGGRuleEdge> getLocalEdges();
	Collection<TGGRuleCorr> getAllCorrNodes();
	Collection<TGGRuleNode> getAllNodes();
	
	Collection<PatternInvocation> getPositiveInvocations();
	Collection<PatternInvocation> getNegativeInvocations();

	Collection<Pair<TGGRuleNode, TGGRuleNode>> getInjectivityChecks();

	void addNegativeInvocation(IPattern markedPattern, Map<TGGRuleNode, TGGRuleNode> mapping);
	void addPositiveInvocation(IPattern markedPattern, Map<TGGRuleNode, TGGRuleNode> mapping);
}
