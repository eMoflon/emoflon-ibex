package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;

public class SRCShortcutRule extends OperationalShortcutRule {

	public SRCShortcutRule(IbexOptions options, IGreenInterpreter greenInterpreter, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, greenInterpreter, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.SOURCE);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SOURCE, BindingType.CREATE), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SOURCE, BindingType.DELETE));

		// delete nodes and edges in corr and trg domains
		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.CORRESPONDENCE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.CORRESPONDENCE));

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TARGET));
	}

	@Override
	public PatternType getType() {
		return PatternType.SOURCE;
	}

	@Override
	protected OperationalisationMode getOperationalisationMode() {
		return OperationalisationMode.SOURCE;
	}

}
