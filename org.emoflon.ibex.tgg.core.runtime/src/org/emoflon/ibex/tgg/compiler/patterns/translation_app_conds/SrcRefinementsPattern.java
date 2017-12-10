package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class SrcRefinementsPattern extends IbexBasePattern {
	private PatternFactory factory;
	
	public SrcRefinementsPattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);
 	}

	private void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.SRC_REFINEMENT_INVOCATIONS;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
	
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private void createPatternNetwork(PatternFactory factory){
		addPositiveInvocation(factory.create(SrcPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(SrcRefinementsPattern.class));
	}

	private boolean isSignatureNode(TGGRuleNode node) {
		return node.getDomainType() == DomainType.SRC;
	}	
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	@Override
	public PatternFactory getPatternFactory() {
		return factory;
	}
}
