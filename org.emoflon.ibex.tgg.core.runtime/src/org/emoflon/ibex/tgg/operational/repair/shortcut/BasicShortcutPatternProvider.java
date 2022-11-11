package org.emoflon.ibex.tgg.operational.repair.shortcut;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalSCFactory;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.OperationalShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.shortcut.search.LocalPatternSearch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.OverlapUtil;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.SCPersistence;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.repair.strategies.RepairApplicationPoint;
import org.emoflon.ibex.util.config.IbexOptions;

public class BasicShortcutPatternProvider implements ShortcutPatternProvider {

	protected final IbexOptions options;
	protected final OverlapUtil overlapUtil;
	protected final OperationalSCFactory opSCFactory;

	private final Set<PatternType> types;

	private Collection<ShortcutRule> basicShortcutRules;
	private final Map<PatternType, Map<String, Collection<OperationalShortcutRule>>> basicShortcutPatterns;

	protected final Map<OperationalShortcutRule, LocalPatternSearch> opShortcutRule2patternMatcher;

	public BasicShortcutPatternProvider(IbexOptions options, PatternType[] types, boolean persistShortcutRules) {
		this.options = options;
		this.overlapUtil = new OverlapUtil(options);
		this.opSCFactory = new OperationalSCFactory(options);

		this.types = new HashSet<>(Arrays.asList(types));

		this.basicShortcutPatterns = new HashMap<>();
		this.opShortcutRule2patternMatcher = new HashMap<>();

		createBasicShortcutPatterns();

		if (persistShortcutRules)
			persistShortcutRules();
	}

	private void createBasicShortcutPatterns() {
		LoggerConfig.log(LoggerConfig.log_repair(), () -> "Generate basic Short-cut Rules:");

		Collection<TGGOverlap> basicOverlaps = overlapUtil.calculateOverlaps(options.tgg.flattenedTGG());

		basicShortcutRules = basicOverlaps.stream() //
				.map(overlap -> new ShortcutRule(overlap, options)) //
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

		if (basicShortcutPatterns.containsKey(repairType))
			return basicShortcutPatterns.get(repairType).get(applMatch.getRuleName());
		return Collections.emptyList();
	}

	@Override
	public LocalPatternSearch getPatternSearch(OperationalShortcutRule opShortcutRule) {
		return opShortcutRule2patternMatcher.get(opShortcutRule);
	}

	private void persistShortcutRules() {
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
