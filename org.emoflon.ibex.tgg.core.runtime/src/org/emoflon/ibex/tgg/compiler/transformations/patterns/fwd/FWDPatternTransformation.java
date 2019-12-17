package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.DomainType;
import language.NAC;
import language.TGGRule;

public class FWDPatternTransformation extends FWD_OPTPatternTransformation {
	public FWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
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
}
