package org.emoflon.ibex.tgg.core.compiler.pattern.protocol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.core.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class ConsistencyPattern extends Pattern {

	public ConsistencyPattern(TGGRule rule) {
		super(rule);
	}

	@Override
	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule) {
		Collection<TGGRuleElement> result = super.getSignatureElements(rule);

		TGGRuleNode protocolNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		protocolNode.setName(getProtocolNodeName());
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		result.add(protocolNode);

		return result;
	}
	
	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.PROTOCOL;
	}

	public String getProtocolNodeName() {
		return "eMoflon_ProtocolNode";
	}

	public String getRuleName() {
		return rule.getName();
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return n.getDomainType() != DomainType.CORR;
	}

	

}
