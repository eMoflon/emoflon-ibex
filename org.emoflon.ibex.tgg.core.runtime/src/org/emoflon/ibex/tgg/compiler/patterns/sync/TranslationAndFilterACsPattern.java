package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.CheckTranslationStatePattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.TranslationACPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class TranslationAndFilterACsPattern extends TranslationACPattern {

	public TranslationAndFilterACsPattern(TGGRule rule) {
		super(rule);
		createMarkedInvocations(PatternFactory.getMarkedPatterns());
	}

	private void createMarkedInvocations(Collection<CheckTranslationStatePattern> markedPatterns) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(BindingType.CONTEXT) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				addCustomPositiveInvocation(markedPattern, mapping);
			}
		}
	}
}
