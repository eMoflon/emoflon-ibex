package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;

import language.TGGRule;

public class TGGCompiler {	
	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns = new LinkedHashMap<>();
	private IbexOptions options;
	private Map<TGGRule, PatternFactory> factories;
		
	public TGGCompiler(IbexOptions options) {
		this.options = options;
		factories = new LinkedHashMap<>();
	}
	
	public Map<TGGRule, Collection<IbexPattern>> getRuleToPatternMap(){
		return ruleToPatterns;
	}

	public void preparePatterns() {	
		if (options.useFlattenedTGG()) {
			for (TGGRule rule : options.flattenedTGG().getRules()) {
				PatternFactory factory = getFactory(rule);

				// Model generation
				factory.createMODELGENPattern();

				// Consistency checking
				factory.createCCPattern();

				// Synchronisation
				factory.createFWDPattern();
				factory.createBWDPattern();
				factory.createConsistencyPattern();

				ruleToPatterns.put(rule, factory.getPatterns());
			}
		} else {
			for (TGGRule rule : options.tgg().getRules()) {
				PatternFactory factory = getFactory(rule);
				
				// Model generation
				factory.createMODELGENPatternWithRefinement();

				// Consistency checking
				factory.createCCPatternWithRefinement();

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
