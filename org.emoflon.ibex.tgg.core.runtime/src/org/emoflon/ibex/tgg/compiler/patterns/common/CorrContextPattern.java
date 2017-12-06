package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CorrContextPattern extends IbexBasePattern {

	public CorrContextPattern(PatternFactory factory) {
		initialise(factory.getRule());
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(PatternFactory factory) {
		// Leaf pattern
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CORR_CONTEXT;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					.filter(this::isSignatureNode)
					.collect(Collectors.toList());
		
		// There are currently no edges between corrs, and the src/trg connections 
		// between corrs and  src/trg objects are modelled as part of the corr itself
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return isContextCorr(n) || isConnectedToAContextCorr(n);
	}

	private boolean isConnectedToAContextCorr(TGGRuleNode n) {
		return Stream.concat(n.getIncomingCorrsSource().stream(), n.getIncomingCorrsTarget().stream())
			         .anyMatch(this::isContextCorr);
	}

	private boolean isContextCorr(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CONTEXT && n instanceof TGGRuleCorr;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return !(isContextCorr(node1) && isContextCorr(node2));
	}
}
