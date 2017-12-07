package org.emoflon.ibex.tgg.compiler.patterns.sync;

import static org.emoflon.ibex.tgg.compiler.patterns.MultiAmalgamationUtil.addComplementOutputAndContextNodes;
import static org.emoflon.ibex.tgg.compiler.patterns.MultiAmalgamationUtil.addKernelOutputAndContextNodes;
import static org.emoflon.ibex.tgg.compiler.patterns.MultiAmalgamationUtil.createMarkedInvocations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.DomainType;
import language.TGGComplementRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ComplementFWDPattern extends IbexBasePattern {
	private TGGComplementRule flattenedComplementRule;
	
	public ComplementFWDPattern(PatternFactory factory) {
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = flattenedComplementRule.getName() + "_" + flattenedComplementRule.getKernel().getName() + PatternSuffixes.FWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		addComplementOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.SRC);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();		
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	protected void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).create(FWDPattern.class));		
		createMarkedInvocations(DomainType.SRC, flattenedComplementRule, this);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}
}
