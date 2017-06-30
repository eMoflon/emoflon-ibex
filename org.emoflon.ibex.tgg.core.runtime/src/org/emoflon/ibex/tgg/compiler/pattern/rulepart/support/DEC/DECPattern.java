package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DECPattern extends RulePartPattern {

	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;
	private DECTrackingHelper decTC;
	private SearchEdgePattern sep;

	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, DECTrackingHelper decTC) {
		super(rule);
		this.entryPoint = entryPoint;
		this.decTC = decTC;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
		
		createSearchEdgePattern();
		addDECAsBodyNode();
		createInvocation();
	}

	private void createSearchEdgePattern() {
		if (sep != null)
			throw new RuntimeException("SearchEdgePattern found. Generating this pattern twice is a bad idea!");
		
		sep = new SearchEdgePattern(rule, entryPoint, edgeType, eDirection);
		decTC.getRuleToPatternsMap().get(rule).add(sep);
		decTC.addEntryAndDec(this, entryPoint.getName(), DECHelper.getDECNode(sep.getRule()).getName());
	}
	
	private void addDECAsBodyNode() {
		getBodyNodes().add(EcoreUtil.copy(DECHelper.getDECNode(sep.getRule())));
	}

	private void createInvocation() {
		addTGGPositiveInvocation(sep);
	}
	
	protected SearchEdgePattern getSearchEdgePattern() {
		return sep;
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
		return DECHelper.isDECNode(n);
	}
	
	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		if (sep == null)
			throw new RuntimeException("No SearchEdgePattern found. Please generate one first before calculating the signature!");
		
		return sep.getSignatureElements().stream().filter(element -> element.getName().equals(e.getName())).count() != 0;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.SEP + entryPoint.getName() + "_" + edgeType.getName() + "_" + eDirection.toString().toLowerCase() + "_DEC_" + entryPoint.getDomainType().getName();
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