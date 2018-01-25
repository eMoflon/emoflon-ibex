package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CorrContextPattern extends AbstractCorrPattern {
	
	public CorrContextPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CORR_CONTEXT;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					.filter(this::isSignatureNode)
					.collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = new ArrayList<>();
		rule.getNodes().stream()
			.filter(this::isContextCorr)
			.map(TGGRuleCorr.class::cast)
			.forEach(corr -> extractSourceAndTargetEdges(corr));
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return isContextCorr(n) || isConnectedToAContextCorr(n);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return !(isContextCorr(node1) && isContextCorr(node2));
	}
}
