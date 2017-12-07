package org.emoflon.ibex.tgg.compiler.patterns.cc;

import static org.emoflon.ibex.tgg.compiler.patterns.MultiAmalgamationUtil.embedKernelConsistencyPatternNodes;
import static org.emoflon.ibex.tgg.compiler.patterns.MultiAmalgamationUtil.isComplementRule;
import static org.emoflon.ibex.tgg.compiler.patterns.RuleRefinementUtil.checkInjectivityInSubRule;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.BindingType;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CCRefinementPattern extends IbexBasePattern{
	private PatternFactory factory;
	
	public CCRefinementPattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);			
	}
	
	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.CC_REFINEMENT_INVOCATIONS;
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
		
		if (isComplementRule(rule)) {
			embedKernelConsistencyPatternNodes((TGGComplementRule)rule, this);
		}
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getBindingType() != BindingType.CREATE || n.getDomainType() == DomainType.SRC || n.getDomainType() == DomainType.TRG;
	}
	
	protected void createPatternNetwork(PatternFactory factory) {
		addPositiveInvocation(factory.create(SrcPattern.class));
		addPositiveInvocation(factory.create(TrgPattern.class));
		addPositiveInvocation(factory.create(CorrContextPattern.class));
		
		if (isComplementRule(factory.getRule())) {
			TGGComplementRule compRule = (TGGComplementRule) factory.getRule();
			addPositiveInvocation(factory.getFactory(compRule.getKernel()).create(ConsistencyPattern.class));
		}

		for (TGGRule superRule : factory.getRule().getRefines())
			addPositiveInvocation(factory.getFactory(superRule).create(CCRefinementPattern.class));
	}

	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return checkInjectivityInSubRule(factory.getRule(), node1, node2);
	}

}
