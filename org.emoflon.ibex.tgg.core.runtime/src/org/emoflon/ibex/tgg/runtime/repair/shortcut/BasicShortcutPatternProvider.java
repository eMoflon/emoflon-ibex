package org.emoflon.ibex.tgg.runtime.repair.shortcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalSCFactory;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.SCPersistence;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.runtime.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class BasicShortcutPatternProvider implements ShortcutPatternProvider {

	protected final IbexOptions options;
	protected final OverlapUtil overlapUtil;
	protected final OperationalSCFactory opSCFactory;

	protected final Set<PatternType> types;

	protected Collection<RuntimeShortcutRule> basicShortcutRules;
	protected final Map<PatternType, Map<String, Collection<OperationalShortcutRule>>> basicShortcutPatterns;

	protected final Map<OperationalShortcutRule, LocalPatternSearch> opShortcutRule2patternMatcher;

	public BasicShortcutPatternProvider(IbexOptions options, PatternType[] types, boolean initiallyPersistShortcutRules) {
		this.options = options;
		this.overlapUtil = new OverlapUtil(options);
		this.opSCFactory = new OperationalSCFactory(options);

		this.types = new HashSet<>(Arrays.asList(types));

		this.basicShortcutPatterns = new HashMap<>();
		this.opShortcutRule2patternMatcher = new HashMap<>();

		createBasicShortcutPatterns();

		if (initiallyPersistShortcutRules)
			persistShortcutRules();
	}

	private void createBasicShortcutPatterns() {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Generate basic Short-cut Rules:");

		Collection<TGGOverlap> basicOverlaps = overlapUtil.calculateOverlaps(options.tgg.flattenedTGG().getRuleSet());

		basicShortcutRules = basicOverlaps.stream() //
				.map(overlap -> new RuntimeShortcutRule(overlap, options)) //
				.collect(Collectors.toList());

		for (PatternType type : types) {
			Map<String, Collection<OperationalShortcutRule>> opShortcutRules = opSCFactory.createOperationalRules(basicShortcutRules, type);
			basicShortcutPatterns.put(type, opShortcutRules);

			LoggerConfig.log(LoggerConfig.log_repair(), //
					() -> "  Generated " + opShortcutRules.values().stream().mapToInt(s -> s.size()).sum() + " " + type.name() + " Short-cut Rules");
		}

		basicShortcutPatterns.values().stream() //
				.flatMap(m -> m.values().stream()) //
				.flatMap(c -> c.stream()) //
				.forEach(r -> opShortcutRule2patternMatcher.put(r, new LocalPatternSearch(r, options)));
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
		SCPersistence persistence = new SCPersistence(options);

		persistence.saveSCRules(basicShortcutRules);
		Map<String, Collection<OperationalShortcutRule>> fwdOpSCRs = basicShortcutPatterns.get(PatternType.FWD);
		if (fwdOpSCRs != null) {
			persistence.saveOperationalFWDSCRules(fwdOpSCRs.values().stream() //
					.flatMap(c -> c.stream()).collect(Collectors.toList()));
		}
		Map<String, Collection<OperationalShortcutRule>> bwdOpSCRs = basicShortcutPatterns.get(PatternType.BWD);
		if (bwdOpSCRs != null) {
			persistence.saveOperationalBWDSCRules(bwdOpSCRs.values().stream() //
					.flatMap(c -> c.stream()).collect(Collectors.toList()));
		}
	}

}
