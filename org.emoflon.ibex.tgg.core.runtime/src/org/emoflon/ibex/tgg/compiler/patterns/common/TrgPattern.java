package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TrgPattern extends IbexBasePattern {
	
	public TrgPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.TRG;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					   .filter(this::isSignatureNode)
					   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = rule.getEdges().stream()
				   .filter(this::isLocalEdge)
				   .collect(Collectors.toList());
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode e) {
		return e.getDomainType() == DomainType.TRG;
	}

	private boolean isLocalEdge(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CREATE;
	}
	
	private void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(TrgContextPattern.class));
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		// If both nodes are context then this has been checked in TrgContext
		return node1.getBindingType() == BindingType.CONTEXT && node2.getBindingType() == BindingType.CONTEXT;
	}
}
