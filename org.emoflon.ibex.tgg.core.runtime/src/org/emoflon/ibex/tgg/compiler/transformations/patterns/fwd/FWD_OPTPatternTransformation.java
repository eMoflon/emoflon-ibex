package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFWDOptBlackPatternName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static  org.emoflon.ibex.tgg.core.util.TGGModelUtils.getNodesByOperator;
import static  org.emoflon.ibex.tgg.core.util.TGGModelUtils.getNodesByOperatorAndDomain;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public FWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getFWDOptBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> contextNodes = getNodesByOperator(rule, BindingType.CONTEXT);
		contextNodes.addAll(getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		
		for (final TGGRuleNode node : contextNodes)
			parent.transformNode(ibexPattern, node);

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.SRC, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
	}
}
