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

public class ComplementBWDPattern extends IbexBasePattern {
	private TGGComplementRule flattenedComplementRule;
	
	public ComplementBWDPattern(PatternFactory factory) {
		assert(factory.getRule() instanceof TGGComplementRule);
		flattenedComplementRule = (TGGComplementRule)factory.getFlattenedVersionOfRule();
		
		initialise();
		createPatternNetwork(factory);
	}
	
	protected void initialise() {		
		String name = flattenedComplementRule.getName() + "_" + flattenedComplementRule.getKernel().getName() + PatternSuffixes.BWD;
		
		Collection<TGGRuleNode> signatureNodes = new ArrayList<>();
		addKernelOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		addComplementOutputAndContextNodes(flattenedComplementRule, signatureNodes, DomainType.TRG);
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();		
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.getFactory(flattenedComplementRule.getKernel()).create(BWDPattern.class));
		createMarkedInvocations(DomainType.TRG, flattenedComplementRule, this);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}
}
