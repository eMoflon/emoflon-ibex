package org.emoflon.ibex.tgg.compiler.transformations.patterns.gen;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGenForCCBlackPatternName;

import java.util.List;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.BindingType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class GENForCCPatternTransformation extends OperationalPatternTransformation {

	public GENForCCPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return getGenForCCBlackPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		// No handling needed
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule) {
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
	protected void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule) {
		
	}
	
}
