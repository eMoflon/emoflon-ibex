package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class ProtocolNACsPattern extends IbexPattern {

	public ProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	abstract protected String getPatternNameSuffix();

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == getInputDomainType();
	}
	
	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}
	
	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return isRelevantForSignature(n);
	}
	
	protected abstract DomainType getInputDomainType();

}
