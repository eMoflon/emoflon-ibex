package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.DomainType;
import language.NAC;
import language.TGGRule;

public class FWDPatternTransformation extends FWD_OPTPatternTransformation {
	public FWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return TGGPatternUtil.getFWDBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule) {
		super.transformNACs(ibexPattern, rule);

		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.TRG))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
		
	}
}
