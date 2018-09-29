package org.emoflon.ibex.tgg.compiler.transformations.patterns.opt;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getCOBlackPatternName;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class COPatternTransformation extends CCPatternTransformation {

	public COPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getCOBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		nodes.addAll(TGGModelUtils.getNodesByOperator(rule, BindingType.CREATE));

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform attributes.
		for (final TGGRuleNode node : nodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getEdgesByOperator(rule, BindingType.CREATE));

		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}
}
