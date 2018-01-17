package org.emoflon.ibex.tgg.compiler.patterns.cc;

import static org.emoflon.ibex.tgg.util.RuleRefinementUtil.checkInjectivityInSubRule;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CCBlackPattern extends IbexBasePattern {
	
	private BlackPatternFactory factory;

	public CCBlackPattern(BlackPatternFactory factory) {
		this.factory = factory;
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
		return ruleName + PatternSuffixes.CC;
	}

	protected void createPatternNetwork(BlackPatternFactory factory) {
		addPositiveInvocation(factory.createBlackPattern(CCRefinementPattern.class));
		
		if (BlackPatternFactory.strategy != FilterACStrategy.NONE) {
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		}
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getBindingType() != BindingType.CREATE || n.getDomainType() == DomainType.SRC || n.getDomainType() == DomainType.TRG;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return checkInjectivityInSubRule(factory.getRule(), node1, node2);
	}

	@Override
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}

}
