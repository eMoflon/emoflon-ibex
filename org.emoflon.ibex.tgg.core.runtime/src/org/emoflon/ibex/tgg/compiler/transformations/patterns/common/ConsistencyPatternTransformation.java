package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ConsistencyPatternTransformation extends OperationalPatternTransformation {

	public ConsistencyPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getConsistencyPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
		// Nothing to do
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		rule.getNodes().forEach(n -> {
			parent.transformNode(ibexPattern, n);
		});

		// Transform attributes
		for (final TGGRuleNode node : rule.getNodes()) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		for (TGGRuleEdge edge : rule.getEdges())
			parent.transformEdge(rule.getEdges(), edge, ibexPattern);

		// Create protocol node and connections to nodes in pattern
		parent.createAndConnectProtocolNode(rule, ibexPattern);

	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.SRC, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}

		filterNACAnalysis = new FilterNACAnalysis(DomainType.TRG, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
	}
}
