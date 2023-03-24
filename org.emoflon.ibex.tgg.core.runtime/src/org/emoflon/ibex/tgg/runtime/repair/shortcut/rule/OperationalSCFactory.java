package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

public class OperationalSCFactory {

	private final IbexOptions options;
	private final ACAnalysis filterNACAnalysis;

	public OperationalSCFactory(IbexOptions options) {
		this.options = options;
		this.filterNACAnalysis = new ACAnalysis(options.tgg.tgg(), options.patterns.acStrategy());
	}

	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(IGreenInterpreter greenInterpreter,
			Collection<RuntimeShortcutRule> scRules, PatternType type) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();

		for (RuntimeShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			if (scRule.getOverlap().nonOperationalizablePatterns.contains(type))
				continue;

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that contain no interface edges
			if (TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE).size()
					+ TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;

			OperationalShortcutRule opSCR = createOpShortcutRule(greenInterpreter, scRule, filterNACAnalysis, type);
			String keyRuleName = originalRule instanceof HigherOrderTGGRule hoRule //
					? HigherOrderSupport.getKeyRuleName(hoRule)
					: originalRule.getName();
			operationalRules.computeIfAbsent(keyRuleName, k -> new LinkedList<>()).add(opSCR);

			greenInterpreter.registerOperationalRule(opSCR.getOperationalizedSCR().getShortcutRule());
		}

		return operationalRules;
	}

	private OperationalShortcutRule createOpShortcutRule(IGreenInterpreter greenInterpreter, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis,
			PatternType type) {
		return switch (type) {
			case FWD -> new FWDShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis);
			case BWD -> new BWDShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis);
			case CC -> new CCShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis);
			case SOURCE -> new SRCShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis);
			case TARGET -> new TRGShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis);
			default -> throw new RuntimeException("Shortcut Rules cannot be operationalized for " + type.toString() + " operations");
		};
	}
}
