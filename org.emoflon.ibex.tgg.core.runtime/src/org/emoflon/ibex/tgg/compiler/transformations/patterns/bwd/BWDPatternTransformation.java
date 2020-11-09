package org.emoflon.ibex.tgg.compiler.transformations.patterns.bwd;


import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class BWDPatternTransformation extends BWD_OPTPatternTransformation {

	public BWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return TGGPatternUtil.generateBWDBlackPatternName(rule.getName());
	}
}
