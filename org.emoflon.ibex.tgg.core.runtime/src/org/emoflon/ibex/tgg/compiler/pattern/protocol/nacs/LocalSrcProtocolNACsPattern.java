package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;

import language.DomainType;
import language.TGGRule;

public class LocalSrcProtocolNACsPattern extends LocalProtocolNACsPattern {

	public LocalSrcProtocolNACsPattern(TGGRule rule, IbexPattern premise, PatternFactory factory) {
		super(factory.createSrcProtocolNACsPattern(), premise);
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.LOCAL_SRC_PROTOCOL_NACS;
	}
	

	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}
}
