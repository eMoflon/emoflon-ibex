package org.emoflon.ibex.tgg.compiler.patterns.co;

import static org.emoflon.ibex.tgg.util.MAUtil.embedKernelRuleAppAndConsistencyPatternNodes;
import static org.emoflon.ibex.tgg.util.MAUtil.isComplementRule;
import static org.emoflon.ibex.tgg.util.RuleRefinementUtil.checkInjectivityInSubRule;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CORefinementPattern extends IbexBasePattern{
	
	public CORefinementPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);			
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CO_REFINEMENT_INVOCATIONS;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
		
		if (isComplementRule(rule)) {
			embedKernelRuleAppAndConsistencyPatternNodes((TGGComplementRule)rule, this);
		}
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return true;
	}
	
	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(SrcPattern.class));
		addPositiveInvocation(factory.createBlackPattern(TrgPattern.class));
		addPositiveInvocation(factory.createBlackPattern(CorrPattern.class));
		
		if (isComplementRule(factory.getRule())) {
			TGGComplementRule compRule = (TGGComplementRule) factory.getRule();
			addPositiveInvocation(factory.getFactory(compRule.getKernel()).createBlackPattern(ConsistencyPattern.class));
		}

		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).createBlackPattern(CORefinementPattern.class));
	}

	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return checkInjectivityInSubRule(factory.getRule(), node1, node2);
	}
}
