package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDOptGreenPattern extends IbexGreenPattern {

	public BWDOptGreenPattern(GreenPatternFactory factory) {
		super(factory);
	}

	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return factory.getGreenSrcNodesInRule();
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return factory.getGreenSrcEdgesInRule();
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
	public boolean isToBeIgnored(IMatch match) {
		return !factory.isComplementRule() && factory.getGreenTrgNodesInRule().isEmpty() && factory.getGreenTrgEdgesInRule().isEmpty();
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return factory.getBlackTrgNodesInRule();
	}
	
	@Override
	public Collection<TGGRuleEdge> getCorrEdges() {
		return factory.getGreenCorrEdgesInRule();
	}
}
