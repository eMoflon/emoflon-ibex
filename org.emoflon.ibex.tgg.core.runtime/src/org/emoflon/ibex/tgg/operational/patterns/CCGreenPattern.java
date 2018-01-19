package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

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
}
