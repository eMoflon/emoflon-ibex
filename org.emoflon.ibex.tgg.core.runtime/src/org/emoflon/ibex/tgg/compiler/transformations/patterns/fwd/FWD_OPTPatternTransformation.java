package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateFWDOptBlackPatternName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperatorAndDomain;

import java.util.List;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public FWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateFWDOptBlackPatternName(rule.getName());
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
		
		if(options.patterns.lookAheadStrategy().equals(FilterNACStrategy.PACS)) {
			for (PACCandidate candidate : ((PACAnalysis) filterNACAnalysis).computePACCandidates(rule,  DomainType.SRC)) {
				parent.addContextPattern(createPAC(ibexPattern,  DomainType.SRC, candidate));
			}
		}
		else for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates(rule, DomainType.SRC)) {
				parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
			}
	}
}
