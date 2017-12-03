package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public interface IPattern {
	String getName();
	
	Collection<TGGRuleNode> getSignatureNodes();
	Collection<TGGRuleNode> getLocalNodes();
	Collection<TGGRuleEdge> getLocalEdges();
	Collection<TGGRuleCorr> getLocalCorrNodes();
	
	Collection<PatternInvocation> getPositiveInvocations();
	Collection<PatternInvocation> getNegativeInvocations();
}
