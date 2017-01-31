package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;

import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DECPattern extends RulePartPattern {
	
	private TGGRuleNode entryPoint;
	
	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		super(DECHelper.createCheckEdgeRule(rule, entryPoint, edgeType, eDirection));
	}

	@Override
	protected boolean injectivityIsAlreadyCheckedBySubpattern(TGGRuleNode node1, TGGRuleNode node2) {
		if(DECHelper.isDECNodE(node1) || DECHelper.isDECNodE(node2)) 
			return false;
		
		return true;	
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return true;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return true;
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		if(e instanceof TGGRuleNode && DECHelper.isDECNodE((TGGRuleNode) e))
			return false;
		
		return true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_DEC";
	}
	
	/**
	 * we only check here for negative invocations because the positive invocation is generated on start
	 * we drop this pattern in case that there is no negative invocation which means that we can apply the rule anyways
	 * @return
	 */
	protected boolean isEmpty() {
		return getNegativeInvocations().isEmpty();
	}
}
