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
		createFilterNacs(opScRule.getReplacingRule(), DomainType.TRG);

		transformEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.TRG, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.TRG, BindingType.DELETE));

		// delete nodes and edges in corr and src domains
		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.CORR));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.CORR));

		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.TRG;
	}

}
