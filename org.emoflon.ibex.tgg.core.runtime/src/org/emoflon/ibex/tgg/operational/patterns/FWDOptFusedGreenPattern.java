package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWDOptFusedGreenPattern extends FusedGreenPattern {
	
	public FWDOptFusedGreenPattern(GreenFusedPatternFactory factory) {
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
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return Collections.emptyList();
	}
	
	@Override
	public boolean isToBeIgnored(IMatch match) {
		return factory.getGreenSrcNodesInRule().isEmpty() && factory.getGreenSrcEdgesInRule().isEmpty();
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		createMarkers(ruleName, match, TGGPatternUtil::getFWDOptBlackPatternName);
	}
}
