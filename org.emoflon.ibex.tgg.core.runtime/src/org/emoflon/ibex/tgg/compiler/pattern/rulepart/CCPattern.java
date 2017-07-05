package org.emoflon.ibex.tgg.compiler.pattern.rulepart;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class CCPattern extends RulePartPattern {

	protected PatternFactory factory;
	private Collection<TGGRuleElement> signatureElements = new HashSet<TGGRuleElement>();


	public CCPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		signatureElements = getSignatureElements(getRule());
		
		createPatternNetwork();
	}

	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.createSrcPattern());
		addTGGPositiveInvocation(factory.createTrgPattern());
		addTGGPositiveInvocation(factory.createCorrContextPattern());
		
		//addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.SRC));
		//addTGGPositiveInvocation(factory.createNoDECsPatterns(DomainType.TRG));
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getBindingType() != BindingType.CREATE || e.getDomainType() == DomainType.SRC || e.getDomainType() == DomainType.TRG;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CC;
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
