package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;

import language.TGGRule;

public class SrcProtocolAndDECPattern extends SrcProtocolNACsPattern {

	public SrcProtocolAndDECPattern(TGGRule rule) {
		super(rule);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_PROTOCOL_DEC_SRC";
	}
}
