package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.*;

import java.util.List;

import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import language.BindingType;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CCPatternTransformation extends OperationalPatternTransformation {

	public CCPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		super(parent, options);
	}
	
	@Override
	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		if (rule instanceof TGGComplementRule)
			handleComplementRuleForCC((TGGComplementRule) rule, ibexPattern);
	}
	
	private void handleComplementRuleForCC(TGGComplementRule rule, IBeXContextPattern ibexPattern) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getPatternName(TGGRule rule) {
		return getCCBlackPatternName(rule.getName());
	}
	
	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule) {
		//TODO All source, all target, but only context corr
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		contextNodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		contextNodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));
		
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
		//TODO All source, all target, but only context corr
		List<TGGRuleEdge> edges = TGGModelUtils.getReferencesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getReferencesByOperator(rule, BindingType.CREATE));
		
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);
	}
}
