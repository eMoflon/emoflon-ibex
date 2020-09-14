package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;


import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.DomainType;
import language.NAC;
import language.TGGRule;

public class FWDPatternTransformation extends FWD_OPTPatternTransformation {
	public FWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return TGGPatternUtil.generateFWDBlackPatternName(rule.getName());
	}
	
	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		super.transformNACs(ibexPattern);

		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.TRG))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
	}
	
	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		if(options.invocation.usePatternInvocation()) {
			
			parent.createInvocation(ibexPattern, parent.getPattern(rule.getName() + PatternSuffixes.GEN));
			for(int i = 1; ; i++) {
				if(!parent.createInvocation(ibexPattern, parent.getPattern(rule.getName() + "_" + i + PatternSuffixes.GEN)))
					break;
			}
			parent.createInvocation(ibexPattern, parent.getPattern(rule.getName() + PatternSuffixes.SRC));
			//At the moment there can be only one SRC/TRG Pattern
//			for(int i = 1; ; i++) {
//				if(!parent.createInvocation(ibexPattern, parent.getPattern(rule.getName() + "_" + i + PatternSuffixes.SRC)))
//					break;
//			}
		}
		else super.transformEdges(ibexPattern);
	}
}
