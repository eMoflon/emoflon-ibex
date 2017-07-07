package org.emoflon.ibex.tgg.compiler.patterns.rulepart.refinement;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.rulepart.CCPattern;

import language.TGGRule;
import language.TGGRuleNode;

public class CCNoNACsPattern extends CCPattern {

	public CCNoNACsPattern(TGGRule rule, TGGRule flattenedRule, PatternFactory factory) {
		super(flattenedRule, factory);		
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.createSrcPattern());
		addTGGPositiveInvocation(factory.createTrgPattern());
		addTGGPositiveInvocation(factory.createCorrContextPattern());

		for (TGGRule superRule : rule.getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).createCCNoNACsPattern());
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CC_NO_NACS;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		if (!rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)) {
			// if both nodes are from super-rule, then super-rule takes care of injectivity
			return true;
		} else if (rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)
				|| rule.getNodes().contains(node2) && !rule.getNodes().contains(node1)) {
			// if one node is from super-rule while the other is not, injectivity has probably (depending on invocations) not been checked yet
			return false;
		} else if (node1.getDomainType() != node2.getDomainType()) {
			// if nodes are from different domains, injectivity cannot have been checked yet
			return false;
		} else {
			// if both are from this rule and from the same domain, they have been checked in context-patterns
			return true;
		}
	}

}
