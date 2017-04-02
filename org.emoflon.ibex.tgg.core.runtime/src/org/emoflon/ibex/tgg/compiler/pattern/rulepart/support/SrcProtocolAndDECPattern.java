package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;

import language.TGGRule;

public class SrcProtocolAndDECPattern extends SrcProtocolNACsPattern {

	public SrcProtocolAndDECPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL_DEC_SRC;
	}
}
