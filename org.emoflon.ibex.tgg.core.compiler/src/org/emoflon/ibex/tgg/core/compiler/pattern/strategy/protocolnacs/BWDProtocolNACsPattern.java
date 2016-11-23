package org.emoflon.ibex.tgg.core.compiler.pattern.strategy.protocolnacs;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class BWDProtocolNACsPattern extends ProtocolNACsPattern {

	public BWDProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.BWD_PROTOCOLNACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}

}
