package org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.InvocationHelper;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class ProtocolNACsPattern extends IbexPattern {

	public ProtocolNACsPattern(TGGRule rule, List<MarkedPattern> markedPatterns) {
		super(rule);
		createMarkedInvocations(markedPatterns);
	}
	
	public ProtocolNACsPattern(TGGRule rule) {
		super(rule);
	}

	private void createMarkedInvocations(List<MarkedPattern> markedPatterns) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(BindingType.CREATE) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = InvocationHelper.getMarkedPattern(markedPatterns, node.getDomainType(), true);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				addCustomNegativeInvocation(markedPattern, mapping);
			}
		}
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
