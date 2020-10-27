package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateSRCBlackPatternName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByDomain;

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

public class SRCPatternTransformation extends OperationalPatternTransformation {

	public SRCPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateSRCBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = getNodesByDomain(rule, DomainType.SRC);

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes.
		for (final TGGRuleNode node : nodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = getEdgesByDomain(rule, DomainType.SRC);
		
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected boolean patternIsEmpty() {
		return TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.SRC).isEmpty() &&
				TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC).isEmpty();
	}

}
