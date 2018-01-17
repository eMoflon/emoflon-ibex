package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		if (factory.blackInterpSupportsAttrConstrs())
			throw new NotImplementedException(); // Have to split csp into black and green parts
		else
			return super.getAttributeConstraintContainer(match);
	}
}
