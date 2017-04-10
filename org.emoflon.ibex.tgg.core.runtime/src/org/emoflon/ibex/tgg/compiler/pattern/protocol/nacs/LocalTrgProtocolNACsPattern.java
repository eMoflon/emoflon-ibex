package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.EdgeDirection;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class LocalTrgProtocolNACsPattern extends LocalProtocolNACsPattern {

	public LocalTrgProtocolNACsPattern(IbexPattern globalTrgProtocolPattern, Collection<TGGRuleElement> mappedElements) {
		super(globalTrgProtocolPattern, mappedElements);
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.LOCAL_TRG_PROTOCOL_NACS;
	}
	
	protected DomainType getInputDomainType() {
		return DomainType.TRG;
	}
}
