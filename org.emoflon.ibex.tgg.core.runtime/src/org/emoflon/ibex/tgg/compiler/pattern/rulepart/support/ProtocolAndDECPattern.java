package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.InvocationHelper;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.ProtocolNACsPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class ProtocolAndDECPattern extends ProtocolNACsPattern {

	public ProtocolAndDECPattern(TGGRule rule, List<MarkedPattern> markedPatterns) {
		super(rule);
		createMarkedInvocations(markedPatterns);
	}

	private void createMarkedInvocations(List<MarkedPattern> markedPatterns) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(BindingType.CONTEXT) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = InvocationHelper.getMarkedPattern(markedPatterns, node.getDomainType(), true);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				addCustomPositiveInvocation(markedPattern, mapping);
			}
		}
	}
}
