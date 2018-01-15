package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CorrPattern extends AbstractCorrPattern {
	private BlackPatternFactory factory;
	
	public CorrPattern(BlackPatternFactory factory) {
		this.factory = factory;
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}
	
	public void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CORR;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					   .filter(this::isSignatureNode)
					   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = new ArrayList<>();
		rule.getNodes().stream()
			.filter(this::isCorr)
			.map(TGGRuleCorr.class::cast)
			.forEach(corr -> extractSourceAndTargetEdges(corr));
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return isCorr(n) || isConnectedToACorr(n);
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(CorrContextPattern.class));
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getBindingType() == BindingType.CONTEXT && node2.getBindingType() == BindingType.CONTEXT;
	}
	
	@Override
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}
}
