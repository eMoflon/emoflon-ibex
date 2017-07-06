package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;

import language.DomainType;
import language.TGGRule;

public class LocalTrgProtocolNACsPattern extends LocalProtocolNACsPattern {
	
	public LocalTrgProtocolNACsPattern(TGGRule rule, IbexPattern premise, PatternFactory factory) {
		super(factory.createTrgProtocolNACsPattern(), premise);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.LOCAL_TRG_PROTOCOL_NACS;
	}
	
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}
}
