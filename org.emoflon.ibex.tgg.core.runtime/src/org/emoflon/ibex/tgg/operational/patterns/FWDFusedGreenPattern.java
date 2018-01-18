package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDBlackPattern;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FWDFusedGreenPattern extends IbexGreenPattern {

	public FWDFusedGreenPattern(GreenFusedPatternFactory factory) {
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
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return factory.getGreenSrcEdgesInRule();
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return factory.getBlackSrcEdgesInRule();
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return factory.getGreenSrcNodesInRule();
	}
	
	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		if (factory.blackInterpSupportsAttrConstrs())
			throw new NotImplementedException(); // Have to split csp into black and green parts
		else
			return super.getAttributeConstraintContainer(match);
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		((GreenFusedPatternFactory)factory).getKernelFactory().create(FWDBlackPattern.getName(MultiAmalgamationUtil.getKernelName(match.patternName()))).createMarkers(MultiAmalgamationUtil.getKernelName(match.patternName()), match);
		((GreenFusedPatternFactory)factory).getComplementFactory().create(FWDBlackPattern.getName(MultiAmalgamationUtil.getComplementName(match.patternName()))).createMarkers(MultiAmalgamationUtil.getComplementName(match.patternName()), match);
	}
}
