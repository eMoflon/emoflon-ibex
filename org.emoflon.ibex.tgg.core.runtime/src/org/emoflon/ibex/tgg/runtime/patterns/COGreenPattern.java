package org.emoflon.ibex.tgg.runtime.patterns;

import java.util.Collection;
import java.util.Collections;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class COGreenPattern extends IbexGreenPattern {

	public COGreenPattern(GreenPatternFactory factory) {
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
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return Collections.emptyList();
	}
	
	@Override
	public Collection<TGGRuleEdge> getCorrEdges() {
		return Collections.emptyList();
	}
}
