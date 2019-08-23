package org.emoflon.ibex.tgg.compiler.transformations.patterns.bwd;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.DomainType;
import language.NAC;
import language.TGGRule;

public class BWDPatternTransformation extends BWD_OPTPatternTransformation {

	public BWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return TGGPatternUtil.getBWDBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.TRG, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
		
		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
	}

}
