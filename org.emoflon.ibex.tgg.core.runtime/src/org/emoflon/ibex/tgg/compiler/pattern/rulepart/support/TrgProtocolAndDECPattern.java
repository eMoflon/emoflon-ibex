package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class TrgProtocolAndDECPattern extends ProtocolAndDECPattern {

	public TrgProtocolAndDECPattern(TGGRule rule, PatternFactory factory, List<MarkedPattern> markedPatterns) {
		super(rule, markedPatterns);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.createTrgProtocolNACsPattern());
		//addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.TRG));
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL_DEC_TRG;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}
}
