package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.InvocationHelper;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class LocalProtocolNACsPattern extends IbexPattern {

	private IbexPattern globalProtocolPattern;
	private Collection<TGGRuleElement> mappedElements = null;
	
	public LocalProtocolNACsPattern(IbexPattern globalProtocolPattern, Collection<TGGRuleElement> mappedElements) {
		super(globalProtocolPattern.getRule());
		this.globalProtocolPattern = globalProtocolPattern;
		this.mappedElements = mappedElements;
		initialize();
		addTGGPositiveInvocation(globalProtocolPattern);
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

	@Override
	public String getName() {
		return super.getName();
	}
}
