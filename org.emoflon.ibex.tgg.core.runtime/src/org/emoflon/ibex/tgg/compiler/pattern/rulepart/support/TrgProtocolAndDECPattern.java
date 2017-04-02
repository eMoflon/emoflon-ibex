package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;

import language.TGGRule;

public class TrgProtocolAndDECPattern extends TrgProtocolNACsPattern {

	public TrgProtocolAndDECPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL_DEC_TRG;
	}
}
