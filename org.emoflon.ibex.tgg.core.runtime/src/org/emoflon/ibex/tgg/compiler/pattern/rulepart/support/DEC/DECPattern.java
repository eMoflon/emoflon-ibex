package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.LocalProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.LocalSrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.LocalTrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DECPattern extends RulePartPattern {

	private TGGRuleNode entryPoint;
	private EReference edgeType;
	private EdgeDirection eDirection;
	private IbexPattern premise;

	public DECPattern(TGGRule rule, TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection, Collection<TGGRule> savingRules, PatternFactory factory) {
		super(rule);
		this.entryPoint = entryPoint;
		this.edgeType = edgeType;
		this.eDirection = eDirection;

		premise = factory.createSearchEdgePattern(entryPoint, edgeType, eDirection);
		addDECAsBodyNode();
		
		// Create pattern network
		addTGGPositiveInvocation(premise);		
		
		for (TGGRule savingRule : savingRules) {
			switch (entryPoint.getDomainType()) {
			case SRC:
			{
				LocalSrcProtocolNACsPattern conclusion = factory.getFactory(savingRule).createLocalSrcProtocolNACsPattern(premise);
				addCustomNegativeInvocation(conclusion, createMappingToLocalPattern(conclusion));
				break;
			}
			case TRG:
			{
				LocalTrgProtocolNACsPattern conclusion = factory.getFactory(savingRule).createLocalTrgProtocolNACsPattern(premise);
				addCustomNegativeInvocation(conclusion, createMappingToLocalPattern(conclusion));
				break;
			}
			default:
				throw new IllegalStateException("DECPatterns: Not defined for anything else than SRC or TRG patterns!");
			}
		}
	}
	
	private Map<TGGRuleElement, TGGRuleElement> createMappingToLocalPattern(LocalProtocolNACsPattern conclusion) {
		// FIXME[Anjorin] How should conclusion be invoked from this?
		return Collections.EMPTY_MAP;
	}

	private void addDECAsBodyNode() {
		getBodyNodes().add(EcoreUtil.copy(DECHelper.getDECNode(premise.getRule())));
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
		return premise.getSignatureElements().stream().filter(element -> element.getName().equals(e.getName())).count() != 0;
	}

	@Override
	protected String getPatternNameSuffix() {
		return getPatternNameSuffix(entryPoint, edgeType, eDirection);
	}
	
	public static String getPatternNameSuffix(TGGRuleNode entryPoint, EReference edgeType, EdgeDirection eDirection){
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
