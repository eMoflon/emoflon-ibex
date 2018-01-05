package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TrgRefinementsPattern extends IbexBasePattern {
	private PatternFactory factory;
	
	public TrgRefinementsPattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);
	}

	private void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.create(TrgPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(TrgRefinementsPattern.class));
	}
	
	private void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.TRG_REFINEMENT_INVOCATIONS;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
	
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}

	private boolean isSignatureNode(TGGRuleNode e) {
		return e.getDomainType() == DomainType.TRG;
	}

	@Override
	public PatternFactory getPatternFactory() {
		return factory;
	}
}
