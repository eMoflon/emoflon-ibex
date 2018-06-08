package org.emoflon.ibex.tgg.compiler.patterns.co;

import static org.emoflon.ibex.tgg.util.RuleRefinementUtil.checkInjectivityInSubRule;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterNACStrategy;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class COBlackPattern extends IbexBasePattern {
	
	public COBlackPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork(factory);
	}
	
	protected void initialise(TGGRule rule) {
		String name = getName(rule.getName());
		
		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
					   .filter(this::isSignatureNode)
					   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.CO;
	}

	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(CORefinementPattern.class));
		
		if (factory.getOptions().getFilterNACStrategy() != FilterNACStrategy.NONE) {
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		}
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return true;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return checkInjectivityInSubRule(factory.getRule(), node1, node2);
	}
}
