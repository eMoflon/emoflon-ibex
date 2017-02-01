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
	
	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, Map<TGGRule, Collection<Pattern>> ruleToPatterns) {
		super(rule);
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
	}

	protected void createSearchEdgePattern(TGGRule rule, TGGRuleNode n, EReference eType, EdgeDirection eDirection, Map<TGGRule, Collection<Pattern>> ruleToPatterns) {
		SearchEdgePattern pattern = new SearchEdgePattern(rule, n, eType, eDirection);
		getPositiveInvocations().add(pattern);
		ruleToPatterns.get(rule).add(pattern);
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return false;
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
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return "_" + entryPoint.getName() +"_" + edgeType.getName() +  "_DEC_" + entryPoint.getDomainType().getName();
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
