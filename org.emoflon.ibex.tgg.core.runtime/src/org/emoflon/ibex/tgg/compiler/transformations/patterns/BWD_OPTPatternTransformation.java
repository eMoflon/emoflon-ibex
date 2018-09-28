package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getBWDOptBlackPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXNode;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public BWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return getBWDOptBlackPatternName(rule.getName());
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
		List<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes.
		for (final TGGRuleNode node : nodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule) {
		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}

		// Filter NACs
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.TRG, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate, rule));
		}
	}

}
