package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;
import org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.RulePartPattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DECPattern extends RulePartPattern {

	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;
	
	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, DECTrackingContainer decTC) {
		super(rule);
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
		initialize();
	}

	protected void createSearchEdgePattern(TGGRule rule, TGGRuleNode n, EReference eType, EdgeDirection eDirection, DECTrackingContainer decTC) {
		SearchEdgePattern pattern = new SearchEdgePattern(rule, n, eType, eDirection);
		getPositiveInvocations().add(pattern);
		decTC.getRuleToPatternsMap().get(rule).add(pattern);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
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
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return entryPoint != null && entryPoint.getDomainType() == e.getDomainType();
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_" + entryPoint.getName() +"_" + edgeType.getName() + "_" + eDirection.toString().toLowerCase() + "_DEC_" + entryPoint.getDomainType().getName();
	}

	/**
	 * we only check here for negative invocations because the positive invocation is generated on start we drop this pattern in case that there is no negative
	 * invocation which means that we can apply the rule anyways
	 * 
	 * @return
	 */
	protected boolean isEmpty() {
		return getNegativeInvocations().isEmpty();
	}
}
