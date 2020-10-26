package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.generateSRCBlackPatternName;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByDomain;
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

public class TRGPatternTransformation extends OperationalPatternTransformation {

	public TRGPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return generateSRCBlackPatternName(rule.getName());
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = getNodesByDomain(rule, DomainType.TRG);

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes.
		for (final TGGRuleNode node : nodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = getEdgesByDomain(rule, DomainType.TRG);
		
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected boolean patternIsEmpty() {
		return TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG).isEmpty() &&
				TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG).isEmpty();
	}

}
