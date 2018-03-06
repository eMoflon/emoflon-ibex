package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraint;

public class CheckLocalTranslationStatePattern extends IbexBasePattern {
	private static final String PROTOCOL_NAME = "protocol";	
	private DomainType domain;
	
	public CheckLocalTranslationStatePattern(CheckTranslationStatePattern localPattern, DomainType domain) {
		super(null);
		this.domain = domain;
		
		initialise(localPattern);
	}
	
	private void initialise(CheckTranslationStatePattern localPattern) {
		Collection<TGGRuleNode> signatureNodes = localPattern.getSignatureNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = localPattern.getLocalEdges();
	
		Collection<TGGRuleNode> localNodes = localPattern.getSignatureNodes().stream()
				   .filter(node -> !isSignatureNode(node))
				   .collect(Collectors.toList());
	
		super.initialise(determineName(), signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode node) {
		return !(node.getName().equals(PROTOCOL_NAME));
	}

	public String determineName() {
		return "MarkedPattern" + PatternSuffixes.LOCAL_MARKED + PatternSuffixes.SEP + domain.getName();
	}

	public DomainType getDomain() {
		return domain;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	@Override
	public Collection<TGGAttributeConstraint> getAttributeConstraints() {
		return Collections.emptyList();
	}
}
