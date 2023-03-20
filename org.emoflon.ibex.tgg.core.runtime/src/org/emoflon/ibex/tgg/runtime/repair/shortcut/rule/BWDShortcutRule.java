package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterEdges;
import static org.emoflon.ibex.tgg.util.TGGFilterUtil.filterNodes;

import org.emoflon.ibex.tgg.compiler.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;

public class BWDShortcutRule extends OperationalShortcutRule {

	public BWDShortcutRule(IbexOptions options, IGreenInterpreter greenInterpreter, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, greenInterpreter, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(operationalizedSCR.getReplacingRule(), DomainType.TARGET);

		transformEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.CREATE), BindingType.CONTEXT);
//		transformEdges(filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);
		transformInterfaceEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.DELETE), BindingType.NEGATIVE);

		transformNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TARGET, BindingType.CREATE), BindingType.CONTEXT);
//		transformNodes(filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.RELAXED), BindingType.CONTEXT);

		removeEdges(filterEdges(operationalizedSCR.getEdges(), DomainType.TARGET, BindingType.DELETE));
		removeNodes(filterNodes(operationalizedSCR.getNodes(), DomainType.TARGET, BindingType.DELETE));

		// Note: at the moment not required -> maybe use it if there are problems with already created green edges
		// addNACforCreatedInterface(filterEdges(scRule.getEdges(), DomainType.SRC));
	}

	@Override
	public PatternType getType() {
		return PatternType.BWD;
	}

	@Override
	protected OperationalisationMode getOperationalisationMode() {
		return OperationalisationMode.BACKWARD;
	}

}
