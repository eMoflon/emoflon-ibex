package org.emoflon.ibex.tgg.compiler.patterns.protocol.nacs;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.MarkedPattern;

import language.DomainType;
import language.TGGRule;

public class TrgProtocolNACsPattern extends ProtocolNACsPattern {

	public TrgProtocolNACsPattern(TGGRule rule, List<MarkedPattern> markedPatterns, PatternFactory factory) {
		super(rule, markedPatterns);
		
		// Create pattern network
		addTGGPositiveInvocation(factory.createTrgPattern());
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
