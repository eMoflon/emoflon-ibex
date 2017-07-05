package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class SrcProtocolAndDECPattern extends ProtocolAndDECPattern {

	public SrcProtocolAndDECPattern(TGGRule rule, PatternFactory factory, List<MarkedPattern> markedPatterns) {
		super(rule, markedPatterns);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.createSrcProtocolNACsPattern());
		//addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.SRC));
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
