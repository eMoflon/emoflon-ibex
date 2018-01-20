package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.tgg.operational.csp.sorting.SearchPlanAction;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;

public class GenGreenPattern extends IbexGreenPattern {
	
	public GenGreenPattern(GreenPatternFactory factory) {
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
	protected List<TGGAttributeConstraint> sortConstraints(List<TGGParamValue> variables, List<TGGAttributeConstraint> constraints) {
		SearchPlanAction spa = new SearchPlanAction(variables, constraints, true, getSrcTrgNodesCreatedByPattern());
		return spa.sortConstraints();
	}
}
