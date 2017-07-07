package org.emoflon.ibex.tgg.compiler.patterns.rulepart.support;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.DomainType;
import language.TGGRule;

public class SrcProtocolAndDECPattern extends ProtocolAndDECPattern {

	public SrcProtocolAndDECPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.createSrcProtocolNACsPattern());
		addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.SRC));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL_DEC_SRC;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}
}
