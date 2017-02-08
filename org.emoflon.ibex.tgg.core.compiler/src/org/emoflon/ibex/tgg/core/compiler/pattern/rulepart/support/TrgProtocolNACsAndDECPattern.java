package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;

import language.TGGRule;

public class TrgProtocolNACsAndDECPattern extends TrgProtocolNACsPattern {

	public TrgProtocolNACsAndDECPattern(TGGRule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_PROTOCOL_DEC_TRG";
	}
}
