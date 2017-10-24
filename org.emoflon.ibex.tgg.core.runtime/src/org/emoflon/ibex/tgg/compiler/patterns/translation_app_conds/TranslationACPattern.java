package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class TranslationACPattern extends IbexPattern {

	public TranslationACPattern(TGGRule rule, boolean positive) {
		super(rule);
		createMarkedInvocations(positive);
	}

	protected void createMarkedInvocations(boolean positive) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (positive)
					addCustomPositiveInvocation(markedPattern, mapping);
				else
					addCustomNegativeInvocation(markedPattern, mapping);
			}
		}
	}

	@Override
	abstract protected String getPatternNameSuffix();

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
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
