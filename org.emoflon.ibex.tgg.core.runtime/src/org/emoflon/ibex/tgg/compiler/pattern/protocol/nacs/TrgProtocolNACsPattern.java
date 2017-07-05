package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class TrgProtocolNACsPattern extends ProtocolNACsPattern {

	public TrgProtocolNACsPattern(TGGRule rule, List<MarkedPattern> markedPatterns) {
		super(rule, markedPatterns);
		
//		TrgProtocolNACsPattern trgProtocolNACs = new TrgProtocolNACsPattern(rule, markedPatterns);
//		patterns.add(trgProtocolNACs);
//		trgProtocolNACs.addTGGPositiveInvocation(trg);
	}
	
	public TrgProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.TRG_PROTOCOL_NACS;
	}

	@Override
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}

}
