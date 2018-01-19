package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDBlackPattern;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWDFusedGreenPattern extends FusedGreenPattern {

	public BWDFusedGreenPattern(GreenFusedPatternFactory factory) {
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
		return factory.getGreenTrgEdgesInRule();
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return factory.getBlackTrgEdgesInRule();
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return factory.getGreenTrgNodesInRule();
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		createMarkers(ruleName, match, BWDBlackPattern::getName);
	}
}
