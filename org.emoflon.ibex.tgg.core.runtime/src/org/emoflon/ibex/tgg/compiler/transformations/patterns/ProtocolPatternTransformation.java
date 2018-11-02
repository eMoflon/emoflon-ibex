package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolPatternName;

import org.emoflon.ibex.tgg.compiler.patterns.IBeXPatternOptimiser;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.TGGRule;

public class ProtocolPatternTransformation extends OperationalPatternTransformation {

	public ProtocolPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return getProtocolPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		// Do nothing
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule) {
		parent.createAndConnectProtocolNode(rule, ibexPattern);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule) {
		// Do nothing
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule) {
		// Do nothing
	}

	@Override
	protected boolean patternIsEmpty(TGGRule rule) {
		// Always has at least one element
		return false;
	}

}
