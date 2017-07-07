package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.cc.CCPattern;
import org.emoflon.ibex.tgg.compiler.patterns.cc.refinement.CCPatternWithRefinements;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.MODELGENPattern;
import org.emoflon.ibex.tgg.compiler.patterns.gen.refinement.MODELGENPatternWithRefinements;
import org.emoflon.ibex.tgg.compiler.patterns.sync.BWDPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.FWDPattern;
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
				factory.create(MODELGENPatternWithRefinements.class);

				// Consistency checking
				factory.create(CCPatternWithRefinements.class);

				// Synchronisation
				// TODO[FStolte] Not yet implemented
				
				ruleToPatterns.put(rule, factory.getPatterns());
			}
		}
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
