package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.DomainType;

public class TRGShortcutRule extends OperationalShortcutRule {

	public TRGShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, FilterNACAnalysis filterNACAnalysis) {
		super(strategy, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(scRule.getReplacingRule(), DomainType.TRG);

		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.DELETE));

		// delete nodes and edges in corr and src domains
		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.CORR));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.CORR));

		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.TRG;
	}

}
