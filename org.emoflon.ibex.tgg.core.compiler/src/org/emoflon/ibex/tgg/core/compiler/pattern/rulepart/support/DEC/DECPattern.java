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
	private EReference edgeType;

	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection) {
		super(rule);
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
	}

	@Override
	protected boolean injectivityIsAlreadyCheckedBySubpattern(TGGRuleNode node1, TGGRuleNode node2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * we only check here for negative invocations because the positive invocation is generated on start
	 * we drop this pattern in case that there is no negative invocation which means that we can apply the rule anyways
	 * @return
	 */
	protected boolean isEmpty() {
		return getNegativeInvocations().isEmpty();
	}
	
	private int countIncomingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType) {
		return (int) entryPoint.getIncomingEdges().stream().filter(e -> e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType)).count();
	}
	
	private int countOutgoingEdgeInRule(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType) {
		return (int) entryPoint.getOutgoingEdges().stream().filter(e -> e.getBindingType().equals(BindingType.CREATE) && e.getType().equals(edgeType)).count();
	}

}
