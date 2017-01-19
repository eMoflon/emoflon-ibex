package org.emoflon.ibex.tgg.core.compiler.pattern.protocol.nacs;

import java.util.Collection;

import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class ProtocolNACsPattern extends Pattern {

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
		return isRelevantForSignature(n) && n.getBindingType() == BindingType.CREATE;
	}
	
	protected abstract DomainType getInputDomainType();

}
