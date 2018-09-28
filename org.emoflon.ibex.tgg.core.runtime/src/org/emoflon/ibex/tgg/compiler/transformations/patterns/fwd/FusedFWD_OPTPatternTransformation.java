package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.FusedPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.DomainType;
import language.TGGRule;

public class FusedFWD_OPTPatternTransformation extends FusedPatternTransformation {

	public FusedFWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, OperationalStrategy strategy) {
		super(parent, options, rule, DomainType.SRC, strategy);
	}
	
	@Override
	protected String getSuffix() {
		return PatternSuffixes.FWD_OPT;
	}
	
	@Override
	protected OperationalPatternTransformation getKernelTransformer() {
		return new FWD_OPTPatternTransformation(parent, options, crule.getKernel());
	}
}
