package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.DomainType;

public class BWDShortcutRule extends OperationalShortcutRule {

	public BWDShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, FilterNACAnalysis filterNACAnalysis) {
		super(strategy, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(scRule.getReplacingRule(), DomainType.TRG);

		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
//		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
//		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);

		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.DELETE));

		// Note: at the moment not required -> maybe use it if there are problems with already created green edges
		// addNACforCreatedInterface(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.BWD;
	}

}
