package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;

import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		if (factory.blackInterpSupportsAttrConstrs())
			throw new NotImplementedException(); // Have to split csp into black and green parts
		else
			return super.getAttributeConstraintContainer(match);
	}
}
