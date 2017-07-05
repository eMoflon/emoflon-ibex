package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class SrcProtocolNACsPattern extends ProtocolNACsPattern {

	public SrcProtocolNACsPattern(TGGRule rule, List<MarkedPattern> markedPatterns) {
		super(rule, markedPatterns);
		
//		SrcProtocolNACsPattern srcProtocolNACs = new SrcProtocolNACsPattern(rule, markedPatterns);
//		patterns.add(srcProtocolNACs);
//		srcProtocolNACs.addTGGPositiveInvocation(src);
	}
	
	// FIXME:  Why this?
	public SrcProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}


	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SRC_PROTOCOL_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}

}
