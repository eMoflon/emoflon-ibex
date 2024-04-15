package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.analysis.ACAnalysis;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.ShortcutResourceHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;

public class OperationalSCFactory implements TimeMeasurable {

	private final IbexOptions options;
	private final ACAnalysis filterNACAnalysis;
	private final ShortcutResourceHandler scResourceHandler;
	private Times times;

	public OperationalSCFactory(IbexOptions options, ShortcutResourceHandler scResourceHandler) {
		this.options = options;
		this.filterNACAnalysis = new ACAnalysis(options.tgg.tgg(), options.patterns.acStrategy());
		this.scResourceHandler = scResourceHandler;
		times = new Times();
		TimeRegistry.register(this);
	}

	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(IGreenInterpreter greenInterpreter, Collection<RuntimeShortcutRule> scRules, PatternType type) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();

		Collection<Supplier<OperationalShortcutRule>> operationalRuleCreator = new LinkedList<>();
		Collection<OperationalShortcutRule> opSCRs = new LinkedList<>();
		for (RuntimeShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			if (scRule.getOverlap().nonOperationalizablePatterns.contains(type))
				continue;

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that contain no interface edges
			if (TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE).size() + TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;

			operationalRuleCreator.add(() -> createOpShortcutRule(greenInterpreter, scRule, filterNACAnalysis, type));
		}

		opSCRs = operationalRuleCreator.stream() //
				.map(Supplier<OperationalShortcutRule>::get) //
				.collect(Collectors.toList());

		for (var opSCR : opSCRs) {
			RuntimeShortcutRule scRule = opSCR.getRawShortcutRule();
			TGGRule originalRule = scRule.getOriginalRule();

			String keyRuleName = originalRule instanceof HigherOrderTGGRule hoRule //
					? HigherOrderSupport.getKeyRuleName(hoRule)
					: originalRule.getName();
			operationalRules.computeIfAbsent(keyRuleName, k -> new LinkedList<>()).add(opSCR);

			options.tgg.ruleHandler().registerOperationalRule(opSCR.getOperationalizedSCR().getShortcutRule());
			greenInterpreter.registerOperationalRule(opSCR.getOperationalizedSCR().getShortcutRule());
		}

		return operationalRules;
	}

	private OperationalShortcutRule createOpShortcutRule(IGreenInterpreter greenInterpreter, RuntimeShortcutRule scRule, ACAnalysis filterNACAnalysis, PatternType type) {
		Timer.start();
		var oscRule = switch (type) {
		case FWD -> new FWDShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis, scResourceHandler);
		case BWD -> new BWDShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis, scResourceHandler);
		case CC -> new CCShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis, scResourceHandler);
		case SOURCE -> new SRCShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis, scResourceHandler);
		case TARGET -> new TRGShortcutRule(options, greenInterpreter, scRule, filterNACAnalysis, scResourceHandler);
		default -> throw new RuntimeException("Shortcut Rules cannot be operationalized for " + type.toString() + " operations");
		};
		times.addTo("OperationalSCFactory", Timer.stop());
		return oscRule;
	}

	@Override
	public Times getTimes() {
		return times;
	}
}
