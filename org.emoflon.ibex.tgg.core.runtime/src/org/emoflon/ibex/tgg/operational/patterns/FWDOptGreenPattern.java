package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWDOptGreenPattern extends IbexGreenPattern {

	public FWDOptGreenPattern(GreenPatternFactory factory) {
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
		return factory.getGreenTrgNodesInRule();
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return factory.getGreenTrgEdgesInRule();
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return factory.getGreenCorrNodesInRule();
	}
	
	@Override
	public boolean isToBeIgnored(IMatch match) {
		return !factory.isComplementRule() && factory.getGreenSrcNodesInRule().isEmpty() && factory.getGreenSrcEdgesInRule().isEmpty();
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return factory.getBlackSrcNodesInRule();
	}
}
