package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;

import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class LocalProtocolNACsPattern extends IbexPattern {

	private IbexPattern globalProtocolPattern;
	private Collection<TGGRuleElement> mappedElements = null;
	
	public LocalProtocolNACsPattern(IbexPattern globalProtocolPattern, IbexPattern premise) {
		super(globalProtocolPattern.getRule());
		this.globalProtocolPattern = globalProtocolPattern;
		this.mappedElements = createMapping(premise);
		initialize();
		
		// Create pattern network
		addTGGPositiveInvocation(globalProtocolPattern);
	}
	
	private Collection<TGGRuleElement> createMapping(IbexPattern premise) {
		// FIXME [Anjorin]
		return Collections.EMPTY_LIST;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return mappedElements != null && !mappedElements.contains(n) && globalProtocolPattern.getSignatureElements().stream().filter(e -> e.getName().equals(n.getName())).count() != 0;
	}
	
	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}
	
	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return mappedElements != null && mappedElements.contains(e);
	}
}
