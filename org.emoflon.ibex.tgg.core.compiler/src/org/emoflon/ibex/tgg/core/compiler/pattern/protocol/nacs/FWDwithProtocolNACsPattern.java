package org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs;

import language.DomainType;
import language.TGGRule;

public class FWDwithProtocolNACsPattern extends PatternWithProtocolNACs {

	public FWDwithProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}



	@Override
	protected String getPatternNameSuffix() {
		return "_FWD_ProtocolNacs";
	}



	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}

}
