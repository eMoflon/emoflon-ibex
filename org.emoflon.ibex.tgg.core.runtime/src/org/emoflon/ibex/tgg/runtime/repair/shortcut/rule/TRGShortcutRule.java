package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class TRGShortcutRule extends OperationalShortcutRule {

	public TRGShortcutRule(IbexOptions options, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.TARGET);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TARGET, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TARGET, BindingType.DELETE));

		// delete nodes and edges in corr and src domains
		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.CORRESPONDENCE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.CORRESPONDENCE));

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SOURCE));
	}

	@Override
	public PatternType getType() {
		return PatternType.TRG;
	}

}
