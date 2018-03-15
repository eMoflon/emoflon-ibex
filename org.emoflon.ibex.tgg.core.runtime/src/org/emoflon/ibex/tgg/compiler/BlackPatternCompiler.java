package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.SearchEdgePattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENForCCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDFusedPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDOptBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDOptFusedPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDFusedPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDOptBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDOptFusedPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDBlackPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import gnu.trove.map.hash.THashMap;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class BlackPatternCompiler {
	private Map<String, Collection<IBlackPattern>> ruleToPatterns;
	private IbexOptions options;
	private Map<String, BlackPatternFactory> factories;

	public BlackPatternCompiler(IbexOptions options) {
		this.options = options;
		factories = new LinkedHashMap<>();
		ruleToPatterns = new LinkedHashMap<>();
	}

	public Map<String, Collection<IBlackPattern>> getRuleToPatternMap() {
		return Collections.unmodifiableMap(ruleToPatterns);
	}

	public void preparePatterns() {
		for (TGGRule rule : options.getConcreteTGGRules()) {
			BlackPatternFactory factory = getFactory(rule);

			// Model generation
			factory.createBlackPattern(GENBlackPattern.class);

			// Consistency checking
			factory.createBlackPattern(CCBlackPattern.class);
			if (rule instanceof TGGComplementRule)
				factory.createBlackPattern(GENForCCPattern.class);

			// Synchronisation
			factory.createBlackPattern(FWDBlackPattern.class);
			factory.createBlackPattern(BWDBlackPattern.class);
			if (rule instanceof TGGComplementRule) {
				factory.createBlackPattern(BWDFusedPattern.class);
				factory.createBlackPattern(FWDFusedPattern.class);
			}
			factory.createBlackPattern(ConsistencyPattern.class);

			// ILP supported transformation
			factory.createBlackPattern(FWDOptBlackPattern.class);
			factory.createBlackPattern(BWDOptBlackPattern.class);
			if (rule instanceof TGGComplementRule) {
				factory.createBlackPattern(BWDOptFusedPattern.class);
				factory.createBlackPattern(FWDOptFusedPattern.class);
			}

			// Create edge patterns for all patterns if required
			for (IBlackPattern pattern : factory.getPatterns()) {
				if (pattern instanceof IbexBasePattern) {
					IbexBasePattern basePattern = (IbexBasePattern) pattern;
					basePattern.getOptimiser().replaceEdges(//
							basePattern, //
							options//
					);
				}
			}

			ruleToPatterns.put(rule.getName(), factory.getPatterns());
		}

		checkForDuplicates();
	}

	private void checkForDuplicates() {
		Collection<String> allNames = ruleToPatterns.values().stream().flatMap(ps -> ps.stream()).map(p -> p.getName())
				.sorted().collect(Collectors.toList());

		Collection<String> duplicates = allNames.stream().filter(p -> Collections.frequency(allNames, p) > 1)
				.collect(Collectors.toSet());

		if (!duplicates.isEmpty())
			throw new IllegalStateException("There are duplicate patterns: " + duplicates);
	}

	public BlackPatternFactory getFactory(TGGRule rule) {
		if (!factories.containsKey(rule.getName()))
			factories.put(rule.getName(), new BlackPatternFactory(rule, this));

		return factories.get(rule.getName());
	}

	public TGGRule getFlattenedVersionOfRule(TGGRule rule) {
		return options.flattenedTGG().getRules().stream().filter(r -> r.getName().equals(rule.getName())).findAny()
				.orElseThrow(IllegalStateException::new);
	}

	public IbexOptions getOptions() {
		return options;
	}
}
