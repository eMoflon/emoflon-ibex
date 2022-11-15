package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.util.config.IbexOptions;

import language.BindingType;
import language.DomainType;

public class BWDShortcutRule extends OperationalShortcutRule {

	public BWDShortcutRule(IbexOptions options, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.TRG);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
//		transformEdges(filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
//		transformNodes(filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.DELETE));

		// Note: at the moment not required -> maybe use it if there are problems with already created green edges
		// addNACforCreatedInterface(filterEdges(scRule.getEdges(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.BWD;
	}

}
