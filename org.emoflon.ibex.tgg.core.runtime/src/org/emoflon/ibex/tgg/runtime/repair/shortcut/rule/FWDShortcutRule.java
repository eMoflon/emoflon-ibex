package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;

public class FWDShortcutRule extends OperationalShortcutRule {

	public FWDShortcutRule(IbexOptions options, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.SOURCE);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.CREATE), BindingType.CONTEXT);
//		transformEdges(filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.RELAXED), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SOURCE, BindingType.CREATE), BindingType.CONTEXT);
//		transformNodes(filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.RELAXED), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.SOURCE, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.SOURCE, BindingType.DELETE));

		// Note: at the moment not required -> maybe use it if there are problems with already created green edges
		// addNACforCreatedInterface(filterEdges(scRule.getEdges(), DomainType.TRG));
	}

	@Override
	public PatternType getType() {
		return PatternType.FWD;
	}

}
