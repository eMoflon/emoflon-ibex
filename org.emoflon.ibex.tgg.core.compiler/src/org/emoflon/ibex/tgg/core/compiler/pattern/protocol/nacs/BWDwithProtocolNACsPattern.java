package org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class BWDwithProtocolNACsPattern extends PatternWithProtocolNACs {

	public BWDwithProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.BWD;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}

}
