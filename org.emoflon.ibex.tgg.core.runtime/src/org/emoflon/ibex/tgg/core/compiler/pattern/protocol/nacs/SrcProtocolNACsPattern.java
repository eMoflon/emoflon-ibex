package org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class SrcProtocolNACsPattern extends ProtocolNACsPattern {

	public SrcProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SRC_PROTOCOL_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}

}
