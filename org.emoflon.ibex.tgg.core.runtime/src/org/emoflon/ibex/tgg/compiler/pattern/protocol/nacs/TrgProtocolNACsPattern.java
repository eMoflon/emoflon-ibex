package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class TrgProtocolNACsPattern extends ProtocolNACsPattern {

	public TrgProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRG_PROTOCOL_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}

}
