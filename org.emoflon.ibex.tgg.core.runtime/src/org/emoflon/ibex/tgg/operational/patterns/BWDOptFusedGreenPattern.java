package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDOptFusedGreenPattern extends FusedGreenPattern {

	public BWDOptFusedGreenPattern(GreenFusedPatternFactory factory) {
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
		return factory.getGreenTrgNodesInRule().isEmpty() && factory.getGreenTrgEdgesInRule().isEmpty();
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		createMarkers(ruleName, match, TGGPatternUtil::getBWDOptBlackPatternName);
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return factory.getBlackTrgNodesInRule();
	}
}
