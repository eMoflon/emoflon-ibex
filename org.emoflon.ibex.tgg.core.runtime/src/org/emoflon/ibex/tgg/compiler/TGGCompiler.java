package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.cc.refinement.CCWithRefinementsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.MODELGENPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.refinement.MODELGENWithRefinementsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.refinement.BWDWithRefinementsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.refinement.ConsistencyWithRefinementsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.refinement.FWDWithRefinementsPattern;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;

import language.TGGRule;

public class TGGCompiler {	
	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns;
	private IbexOptions options;
	private Map<TGGRule, PatternFactory> factories;
		
	public TGGCompiler(IbexOptions options) {
		this.options = options;
		factories = new LinkedHashMap<>();
		ruleToPatterns = new LinkedHashMap<>();
	}
	
	public Map<TGGRule, Collection<IbexPattern>> getRuleToPatternMap(){
		return Collections.unmodifiableMap(ruleToPatterns);
	}

	public void preparePatterns() {	
		if (options.useFlattenedTGG()) {
			for (TGGRule rule : options.getFlattenedConcreteTGGRules()) {
				PatternFactory factory = getFactory(rule);

				// Model generation
				factory.create(MODELGENPattern.class);
				
				// Consistency checking
				factory.create(CCPattern.class);
				
				// Synchronisation
				factory.create(FWDPattern.class);
				factory.create(BWDPattern.class);
				factory.create(ConsistencyPattern.class);
				
				ruleToPatterns.put(rule, factory.getPatterns());
			}
		} else {
			for (TGGRule rule : options.getConcreteTGGRules()) {
				PatternFactory factory = getFactory(rule);
				
				// Model generation
				factory.create(MODELGENWithRefinementsPattern.class);

				// Consistency checking
				factory.create(CCWithRefinementsPattern.class);

				// Synchronisation
				factory.create(FWDWithRefinementsPattern.class);
				factory.create(BWDWithRefinementsPattern.class);
				factory.create(ConsistencyWithRefinementsPattern.class);
				
				ruleToPatterns.put(rule, factory.getPatterns());
			}
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

	public PatternFactory getFactory(TGGRule rule) {
		return factories.computeIfAbsent(rule, (k) -> new PatternFactory(k, this));
	}

	public TGGRule getFlattenedVersionOfRule(TGGRule rule) {
		return options.flattenedTGG().getRules().stream()
				   .filter(r -> r.getName().equals(rule.getName()))
				   .findAny()
				   .orElseThrow(IllegalStateException::new);
	}
}
