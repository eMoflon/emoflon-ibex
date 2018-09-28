package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.FusedPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class FusedFWDPatternTransformation extends FusedPatternTransformation {

	public FusedFWDPatternTransformation(ContextPatternTransformation contextPatternTransformation, IbexOptions options,
			TGGRule rule) {
		super(contextPatternTransformation, options, rule);
	}

	@Override
	protected void createTransformers() {
		kernelTransformer = new FWDPatternTransformation(parent, options, crule.getKernel());
		complementTransformer = new FWDPatternTransformation(parent, options, crule);
	}
}
