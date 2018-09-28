package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.FusedPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class FusedFWD_OPTPatternTransformation extends FusedPatternTransformation {

	public FusedFWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected void createTransformers() {
		kernelTransformer = new FWD_OPTPatternTransformation(parent, options, crule.getKernel());
		complementTransformer = new FWD_OPTPatternTransformation(parent, options, crule);
	}
}
