package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class UserNACGreenPattern extends IbexGreenPattern {

	public UserNACGreenPattern(GreenPatternFactory factory) {
		super(factory);
	}

	@Override
	public boolean isToBeIgnored(IMatch match) {
		return false;
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
	public Collection<TGGRuleEdge> getCorrEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getMarkedContextNodes() {
		return Collections.emptyList();
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		
	}
}
