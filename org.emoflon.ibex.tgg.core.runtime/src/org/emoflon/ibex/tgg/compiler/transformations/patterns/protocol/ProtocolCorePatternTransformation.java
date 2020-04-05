package org.emoflon.ibex.tgg.compiler.transformations.patterns.protocol;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolCorePatternName;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.TGGRule;

public class ProtocolCorePatternTransformation extends OperationalPatternTransformation {

	public ProtocolCorePatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return getProtocolCorePatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		parent.createProtocolNode(rule, ibexPattern);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		// Do nothing
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		// Do nothing
	}

	@Override
	protected boolean patternIsEmpty() {
		// Always has at least one element
		return false;
	}

}
