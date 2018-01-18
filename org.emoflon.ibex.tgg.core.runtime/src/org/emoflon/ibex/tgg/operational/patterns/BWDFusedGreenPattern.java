package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDBlackPattern;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.util.MultiAmalgamationUtil;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BWDFusedGreenPattern extends IbexGreenPattern {

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
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		if (factory.blackInterpSupportsAttrConstrs())
			throw new NotImplementedException(); // Have to split csp into black and green parts
		else
			return super.getAttributeConstraintContainer(match);
	}
	
	@Override
	public void createMarkers(String ruleName, IMatch match) {
		((GreenFusedPatternFactory)factory).getKernelFactory().create(BWDBlackPattern.getName(MultiAmalgamationUtil.getKernelName(match.patternName()))).createMarkers(MultiAmalgamationUtil.getKernelName(match.patternName()), match);
		((GreenFusedPatternFactory)factory).getComplementFactory().create(BWDBlackPattern.getName(MultiAmalgamationUtil.getComplementName(match.patternName()))).createMarkers(MultiAmalgamationUtil.getComplementName(match.patternName()), match);
	}
	
}
