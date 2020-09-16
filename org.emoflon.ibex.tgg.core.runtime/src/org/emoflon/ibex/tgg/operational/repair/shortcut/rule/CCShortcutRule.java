package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.DomainType;

public class CCShortcutRule extends OperationalShortcutRule {

	public CCShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, FilterNACAnalysis filterNACAnalysis) {
		super(strategy, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(scRule.getReplacingRule(), DomainType.SRC);
		createFilterNacs(scRule.getReplacingRule(), DomainType.TRG);

		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		transformInterfaceEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE));

		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.DELETE));
	}

	@Override
	public PatternType getType() {
		return PatternType.CC;
	}

}
