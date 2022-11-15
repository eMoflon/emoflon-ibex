package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.util.config.IbexOptions;

import language.BindingType;
import language.DomainType;

public class SRCShortcutRule extends OperationalShortcutRule {

	public SRCShortcutRule(IbexOptions options, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.SRC);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SRC, BindingType.DELETE));

		// delete nodes and edges in corr and trg domains
		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.CORR));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.CORR));

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TRG));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TRG));
	}

	@Override
	public PatternType getType() {
		return PatternType.SRC;
	}

}
