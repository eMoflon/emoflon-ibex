package org.emoflon.ibex.tgg.compiler.transformations.patterns.bwd;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateBWDOptBlackPatternName;
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

public class BWD_OPTPatternTransformation extends OperationalPatternTransformation {

	public BWD_OPTPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateBWDOptBlackPatternName(rule.getName());
	}
	
	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = getNodesByOperator(rule, BindingType.CONTEXT);
		nodes.addAll(getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes.
		for (final TGGRuleNode node : nodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (TGGRuleEdge edge : edges) 
			parent.transformEdge(edges, edge, ibexPattern);
		
	}

	@Override
	protected boolean patternIsEmpty() {
		return TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG).isEmpty() &&
				TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG).isEmpty();
	}
}
