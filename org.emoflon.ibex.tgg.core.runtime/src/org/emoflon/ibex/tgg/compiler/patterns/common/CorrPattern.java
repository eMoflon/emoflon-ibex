package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CorrPattern extends IbexBasePattern {

	public CorrPattern(PatternFactory factory) {
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}
	
	public void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CORR;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					   .filter(this::isSignatureNode)
					   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return isCorr(n) || isConnectedToACorr(n);
	}

	private void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.create(CorrContextPattern.class));
	}

	private boolean isConnectedToACorr(TGGRuleNode n) {
		return Stream.concat(n.getIncomingCorrsSource().stream(), n.getIncomingCorrsTarget().stream())
			         .anyMatch(this::isCorr);
	}

	private boolean isCorr(TGGRuleNode n) {
		return n instanceof TGGRuleCorr;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getBindingType() == BindingType.CONTEXT && node2.getBindingType() == BindingType.CONTEXT;
	}
}
