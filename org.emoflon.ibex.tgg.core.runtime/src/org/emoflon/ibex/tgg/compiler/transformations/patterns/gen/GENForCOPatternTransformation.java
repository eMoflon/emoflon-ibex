package org.emoflon.ibex.tgg.compiler.transformations.patterns.gen;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGenForCOBlackPatternName;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class GENForCOPatternTransformation extends GENPatternTransformation {

	public GENForCOPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getGenForCOBlackPatternName(rule.getName());
	}
}
