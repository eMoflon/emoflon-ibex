package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class IbexGreenPattern implements IGreenPattern {
	protected GreenPatternFactory factory;
	
	public IbexGreenPattern(GreenPatternFactory factory) {
		this.factory = factory;
	}
	
	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		return new RuntimeTGGAttributeConstraintContainer(
				factory.getRuleCSPConstraintLibrary(), 
				match, 
				factory.getStrategy(), 
				factory.getOptions().constraintProvider());
	}
	
	@Override
	public Collection<TGGRuleEdge> getEdgesToBeMarked() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public Collection<TGGRuleNode> getNodesToBeMarked() {
		return Collections.emptyList();
	}
	
	@Override
	public boolean isToBeIgnored(IMatch match) {
		return false;
	}
}
