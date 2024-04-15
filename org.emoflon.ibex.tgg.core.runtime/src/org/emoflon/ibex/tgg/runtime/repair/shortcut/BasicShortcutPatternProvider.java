package org.emoflon.ibex.tgg.runtime.repair.shortcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.interpreter.IGreenInterpreter;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalSCFactory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.ShortcutResourceHandler;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.util.benchmark.TimeMeasurable;
import org.emoflon.ibex.tgg.util.benchmark.TimeRegistry;
import org.emoflon.ibex.tgg.util.benchmark.Timer;
import org.emoflon.ibex.tgg.util.benchmark.Times;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class BasicShortcutPatternProvider implements ShortcutPatternProvider, TimeMeasurable {

	protected final IbexOptions options;
	protected final OverlapUtil overlapUtil;
	protected final ShortcutResourceHandler scResourceHandler;
	protected final OperationalSCFactory opSCFactory;

	protected final Set<PatternType> types;

	protected Collection<RuntimeShortcutRule> basicShortcutRules;
	protected final Map<PatternType, Map<String, Collection<OperationalShortcutRule>>> basicShortcutPatterns;

	protected final Map<OperationalShortcutRule, LocalPatternSearch> opShortcutRule2patternMatcher;

	private Times times;

	public BasicShortcutPatternProvider(IbexOptions options, IGreenInterpreter greenInterpreter, PatternType[] types, boolean initiallyPersistShortcutRules) {
		this.options = options;
		this.overlapUtil = new OverlapUtil(options);
		this.scResourceHandler = new ShortcutResourceHandler(options);
		this.opSCFactory = new OperationalSCFactory(options, scResourceHandler);
		this.times = new Times();
		TimeRegistry.register(this);

		this.types = new HashSet<>(Arrays.asList(types));

		this.basicShortcutPatterns = Collections.synchronizedMap(new HashMap<>());
		this.opShortcutRule2patternMatcher = new HashMap<>();

		createBasicShortcutPatterns(greenInterpreter);

		if (initiallyPersistShortcutRules)
			persistShortcutRules();
	}

	private void createBasicShortcutPatterns(IGreenInterpreter greenInterpreter) {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Generate basic Short-cut Rules:");
		System.out.println("BLABLA");
		Timer.start();
		Collection<TGGOverlap> basicOverlaps = overlapUtil.calculateOverlaps(options.tgg.tgg().getRuleSet());
		times.addTo("BasicShortcutPatternProvider:calculcateOverlaps", Timer.stop());

		Timer.start();
		basicShortcutRules = basicOverlaps.stream() //
				.map(overlap -> {
					RuntimeShortcutRule rtShortcutRule = new RuntimeShortcutRule(overlap, options);
					scResourceHandler.add(rtShortcutRule.getShortcutRule());
					return rtShortcutRule;
				}) //
				.collect(Collectors.toList());

		times.addTo("BasicShortcutPatternProvider:createRuntimeSCRules", Timer.stop());

		Timer.start();
		types.stream().forEach(type -> {
			Map<String, Collection<OperationalShortcutRule>> opShortcutRules = opSCFactory.createOperationalRules(greenInterpreter, basicShortcutRules, type);
			basicShortcutPatterns.put(type, opShortcutRules);

			LoggerConfig.log(LoggerConfig.log_repair(), //
					() -> "  Generated " + opShortcutRules.values().stream().mapToInt(s -> s.size()).sum() + " " + type.name() + " Short-cut Rules");
		});
		times.addTo("BasicShortcutPatternProvider:createOperationalRules", Timer.stop());

		Timer.start();
		basicShortcutPatterns.values().stream() //
				.flatMap(m -> m.values().stream()) //
				.flatMap(c -> c.stream()) //
				.forEach(r -> opShortcutRule2patternMatcher.put(r, new LocalPatternSearch(r, options)));
		times.addTo("BasicShortcutPatternProvider:createSearchPlans", Timer.stop());
	}

	@Override
	public Collection<OperationalShortcutRule> getOperationalShortcutRules(RepairApplicationPoint applPoint) {
		PatternType repairType = applPoint.getRepairType();
		ITGGMatch applMatch = applPoint.getApplicationMatch();

		if (basicShortcutPatterns.containsKey(repairType)) {
			Map<String, Collection<OperationalShortcutRule>> tmp = basicShortcutPatterns.get(repairType);
			if (tmp.containsKey(applMatch.getRuleName()))
				return tmp.get(applMatch.getRuleName());
		}
		return Collections.emptyList();
	}

	@Override
	public LocalPatternSearch getPatternSearch(OperationalShortcutRule opShortcutRule) {
		return opShortcutRule2patternMatcher.get(opShortcutRule);
	}

	public void persistShortcutRules() {
//		scResourceHandler.save();
	}

	@Override
	public Times getTimes() {
		return times;
	}
}
