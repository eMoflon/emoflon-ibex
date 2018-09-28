package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFWDOptBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static  org.emoflon.ibex.tgg.core.util.TGGModelUtils.getNodesByOperator;
import static  org.emoflon.ibex.tgg.core.util.TGGModelUtils.getNodesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getEdgesByOperatorAndDomain;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXNode;
import language.BindingType;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public FWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return getFWDOptBlackPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		if(rule instanceof TGGComplementRule) {
			TGGComplementRule compRule = (TGGComplementRule) rule;
			IBeXContextPattern kernelConsistencyPattern = parent.createConsistencyPattern(compRule.getKernel());
			
			createInvocation(ibexPattern, kernelConsistencyPattern, true);
			
			Optional<IBeXNode> markerNode = findIBeXNodeWithName(ibexPattern, getProtocolNodeName(compRule.getKernel().getName()));
			markerNode.ifPresent(ibexPattern.getSignatureNodes()::add);
		}
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule) {
		List<TGGRuleNode> contextNodes = getNodesByOperator(rule, BindingType.CONTEXT);
		contextNodes.addAll(getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		for (final TGGRuleNode node : contextNodes)
			parent.transformNode(ibexPattern, node);

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule) {
		List<TGGRuleEdge> edges = getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule) {
		// Filter NACs
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.SRC, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate, rule));
		}
	}
}
