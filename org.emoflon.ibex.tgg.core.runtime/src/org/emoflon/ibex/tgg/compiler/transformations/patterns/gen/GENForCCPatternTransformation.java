package org.emoflon.ibex.tgg.compiler.transformations.patterns.gen;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGenForCCBlackPatternName;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class GENForCCPatternTransformation extends GENPatternTransformation {

	public GENForCCPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getGenForCCBlackPatternName(rule.getName());
	}
}
