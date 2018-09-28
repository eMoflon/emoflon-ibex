package org.emoflon.ibex.tgg.compiler.transformations.patterns.bwd;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.FusedPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRule;

public class FusedBWDPatternTransformation extends FusedPatternTransformation {

	public FusedBWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected void createTransformers() {
		kernelTransformer = new BWDPatternTransformation(parent, options, crule.getKernel());
		complementTransformer = new BWDPatternTransformation(parent, options, crule);
	}
}
