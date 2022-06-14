package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;

public class CCShortcutRule extends OperationalShortcutRule {

	public CCShortcutRule(IbexOptions options, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.SRC);
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.TRG);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE));

		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SRC, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.DELETE));
	}

	@Override
	public PatternType getType() {
		return PatternType.CC;
	}

}
