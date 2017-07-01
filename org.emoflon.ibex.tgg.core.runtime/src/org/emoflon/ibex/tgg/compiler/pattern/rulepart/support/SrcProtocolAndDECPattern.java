package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class SrcProtocolAndDECPattern extends ProtocolAndDECPattern {

	public SrcProtocolAndDECPattern(TGGRule rule, List<MarkedPattern> markedPatterns) {
		super(rule, markedPatterns);
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
