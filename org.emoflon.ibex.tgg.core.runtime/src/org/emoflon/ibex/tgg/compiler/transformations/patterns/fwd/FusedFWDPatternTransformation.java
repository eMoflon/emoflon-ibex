package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.FusedPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.DomainType;
import language.TGGRule;

public class FusedFWDPatternTransformation extends FusedPatternTransformation {

	public FusedFWDPatternTransformation(ContextPatternTransformation contextPatternTransformation, IbexOptions options,
			TGGRule rule, OperationalStrategy strategy) {
		super(contextPatternTransformation, options, rule, DomainType.SRC, strategy);
	}

	@Override
	protected String getSuffix() {
		return PatternSuffixes.FWD;
	}

	@Override
	protected OperationalPatternTransformation getKernelTransformer() {
		return new FWDPatternTransformation(parent, options, crule.getKernel());
	}
}
