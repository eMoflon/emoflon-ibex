package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CCGreenPattern extends IbexGreenPattern {

	public CCGreenPattern(GreenPatternFactory factory) {
		super(factory);
	}

	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getTrgNodes() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return factory.getGreenCorrNodesInRule();
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		Collection<TGGRuleNode> greenNodesInRule = factory.getGreenSrcNodesInRule().stream().collect(Collectors.toList());
		greenNodesInRule.addAll(factory.getGreenTrgNodesInRule());
		return greenNodesInRule;
	}
	
	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		Collection<TGGRuleNode> blackNodesInRule = factory.getBlackSrcNodesInRule().stream().collect(Collectors.toList());
		blackNodesInRule.addAll(factory.getBlackTrgNodesInRule());
		return blackNodesInRule;
	}
	
	@Override
	public Collection<TGGRuleEdge> getCorrEdges() {
		return factory.getGreenCorrEdgesInRule();
	}
}
