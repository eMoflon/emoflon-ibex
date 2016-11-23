package org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class FWDProtocolNACsPattern extends ProtocolNACsPattern {

	public FWDProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}



	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.FWD_PROTOCOLNACS;
	}



	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}

}
