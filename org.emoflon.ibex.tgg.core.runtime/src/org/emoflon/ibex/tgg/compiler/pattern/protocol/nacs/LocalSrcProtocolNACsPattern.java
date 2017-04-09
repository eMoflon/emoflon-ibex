package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class LocalSrcProtocolNACsPattern extends LocalProtocolNACsPattern {

	public LocalSrcProtocolNACsPattern(IbexPattern globalSrcProtocolPattern, Collection<TGGRuleElement> mappedElements) {
		super(globalSrcProtocolPattern, mappedElements);
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.LOCAL_SRC_PROTOCOL_NACS;
	}
	

	protected DomainType getInputDomainType() {
		return DomainType.SRC;
	}
}
