package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENForCCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.GENBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ComplementBWDPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ComplementFWDPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDBlackPattern;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGComplementRule;
import language.TGGRule;

public class BlackPatternCompiler {	
	private Map<String, Collection<IBlackPattern>> ruleToPatterns;
	private IbexOptions options;
	private Map<String, BlackPatternFactory> factories;
		
	public BlackPatternCompiler(IbexOptions options) {
		this.options = options;
		factories = new LinkedHashMap<>();
		ruleToPatterns = new LinkedHashMap<>();
	}
	
	public Map<String, Collection<IBlackPattern>> getRuleToPatternMap(){
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
				factory.createBlackPattern(ComplementBWDPattern.class);
				factory.createBlackPattern(ComplementFWDPattern.class);
			}
			factory.createBlackPattern(ConsistencyPattern.class);
			
			ruleToPatterns.put(rule.getName(), factory.getPatterns());
		}
	
		checkForDuplicates();
	}

	private void checkForDuplicates() {
		Collection<String> allNames = ruleToPatterns.values().stream()
				.flatMap(ps -> ps.stream())
				.map(p -> p.getName())
				.sorted()
				.collect(Collectors.toList());

		Collection<String> duplicates = allNames.stream()
				.filter(p -> Collections.frequency(allNames, p) > 1)	
				.collect(Collectors.toSet());
		
		if(!duplicates.isEmpty())
			throw new IllegalStateException("There are duplicate patterns: " + duplicates);
	}

	public BlackPatternFactory getFactory(TGGRule rule) {
		if(!factories.containsKey(rule.getName()))
			factories.put(rule.getName(), new BlackPatternFactory(rule, this));

		return factories.get(rule.getName());
	}

	public TGGRule getFlattenedVersionOfRule(TGGRule rule) {
		return options.flattenedTGG().getRules().stream()
				   .filter(r -> r.getName().equals(rule.getName()))
				   .findAny()
				   .orElseThrow(IllegalStateException::new);
	}
}
