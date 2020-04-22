package org.emoflon.ibex.tgg.compiler.transformations.patterns.gen;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateGENBlackPatternName;

import java.util.List;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GEN4SYNCPatternTransformation extends OperationalPatternTransformation {

	public GEN4SYNCPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateGENBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		for (final TGGRuleNode node : contextNodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates(rule, DomainType.SRC)) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates(rule, DomainType.TRG)) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
	}
}
