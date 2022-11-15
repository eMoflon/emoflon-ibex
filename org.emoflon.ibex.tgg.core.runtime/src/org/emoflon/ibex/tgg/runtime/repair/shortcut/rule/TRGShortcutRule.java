package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.util.config.IbexOptions;

import language.BindingType;
import language.DomainType;

public class TRGShortcutRule extends OperationalShortcutRule {

	public TRGShortcutRule(IbexOptions options, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.TRG);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG, BindingType.DELETE));

		// delete nodes and edges in corr and src domains
		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.CORR));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.CORR));

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.TRG;
	}

}
