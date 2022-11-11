package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.csp.EmptyRuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class EmptyGreenPattern implements IGreenPattern {
	
	public EmptyGreenPattern(GreenPatternFactory factory) {
		
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
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(ITGGMatch match) {
		return new EmptyRuntimeTGGAttributeConstraintContainer();
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
	public boolean isToBeIgnored(ITGGMatch match) {
		return true;
	}

	@Override
	public void createMarkers(String ruleName, ITGGMatch match) {
		
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
