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

public class SrcContextPattern extends IbexBasePattern {
	private BlackPatternFactory factory;
	
	public SrcContextPattern(BlackPatternFactory factory) {
		this.factory = factory;
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.SRC_CONTEXT;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					.filter(this::isSignatureNode)
					.collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = rule.getEdges().stream()
					.filter(this::isLocalEdge)
					.collect(Collectors.toList());
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.SRC && n.getBindingType() == BindingType.CONTEXT;
	}

	private boolean isLocalEdge(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.SRC && e.getBindingType() == BindingType.CONTEXT;
	}
	
	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		// Leaf pattern so we have to check injectivity here
		return false;
	}
	
	@Override
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}
}
