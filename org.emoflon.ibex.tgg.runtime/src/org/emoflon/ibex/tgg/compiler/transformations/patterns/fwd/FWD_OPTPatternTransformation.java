package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateFWDOptBlackPatternName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperatorAndDomain;

import java.util.List;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class FWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public FWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateFWDOptBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = getNodesByOperator(rule, BindingType.CONTEXT);
		nodes.addAll(getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		
		for (final TGGRuleNode node : nodes)
			parent.transformNode(ibexPattern, node);

		// Transform attributes.
		for (final TGGRuleNode node : nodes)
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
	protected boolean patternIsEmpty() {
		return TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC).isEmpty() &&
				TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC).isEmpty();
	}

	@Override
	protected boolean includeDerivedCSPParams() {
		return false;
	}
}
