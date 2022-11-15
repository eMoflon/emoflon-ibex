package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderSupport;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.higherorder.HigherOrderTGGRule;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.util.config.IbexOptions;

import language.BindingType;
import language.TGGRule;

public class OperationalSCFactory {

	private final IbexOptions options;
	private final ACAnalysis filterNACAnalysis;

	public OperationalSCFactory(IbexOptions options) {
		this.options = options;
		this.filterNACAnalysis = new ACAnalysis(options.tgg.tgg(), options);
	}

	public Map<String, Collection<OperationalShortcutRule>> createOperationalRules(Collection<ShortcutRule> scRules, PatternType type) {
		Map<String, Collection<OperationalShortcutRule>> operationalRules = new HashMap<>();

		for (ShortcutRule scRule : scRules) {
			TGGRule originalRule = scRule.getOriginalRule();
			TGGRule replacingRule = scRule.getReplacingRule();

			if (scRule.getOverlap().nonOperationalizablePatterns.contains(type))
				continue;

			// TODO larsF, adrianM: does this make sense?
			// we do not want rules that contain no interface edges
			if (TGGFilterUtil.filterEdges(originalRule.getEdges(), BindingType.CREATE).size()
					+ TGGFilterUtil.filterEdges(replacingRule.getEdges(), BindingType.CREATE).size() == 0)
				continue;

			OperationalShortcutRule opSCR = createOpShortcutRule(scRule, filterNACAnalysis, type);
			String keyRuleName = originalRule instanceof HigherOrderTGGRule hoRule //
					? HigherOrderSupport.getKeyRuleName(hoRule)
					: originalRule.getName();
			operationalRules.computeIfAbsent(keyRuleName, k -> new LinkedList<>()).add(opSCR);
		}

		return operationalRules;
	}

	private OperationalShortcutRule createOpShortcutRule(ShortcutRule scRule, ACAnalysis filterNACAnalysis, PatternType type) {
		return switch (type) {
			case FWD -> new FWDShortcutRule(options, scRule, filterNACAnalysis);
			case BWD -> new BWDShortcutRule(options, scRule, filterNACAnalysis);
			case CC -> new CCShortcutRule(options, scRule, filterNACAnalysis);
			case SRC -> new SRCShortcutRule(options, scRule, filterNACAnalysis);
			case TRG -> new TRGShortcutRule(options, scRule, filterNACAnalysis);
			default -> throw new RuntimeException("Shortcut Rules cannot be operationalized for " + type.toString() + " operations");
		};
	}
}
