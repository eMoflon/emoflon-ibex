package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.DomainType;

public class FWDShortcutRule extends OperationalShortcutRule {

	public FWDShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(strategy, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(opScRule.getReplacingRule(), DomainType.SRC);

		transformEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
//		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.RELAXED), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
//		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.RELAXED), BindingType.CONTEXT);

		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.SRC, BindingType.DELETE));

		// Note: at the moment not required -> maybe use it if there are problems with already created green edges
		// addNACforCreatedInterface(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG));
	}

	@Override
	public PatternType getType() {
		return PatternType.FWD;
	}

}
