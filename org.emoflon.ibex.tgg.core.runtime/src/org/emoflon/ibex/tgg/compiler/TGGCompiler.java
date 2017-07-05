package org.emoflon.ibex.tgg.compiler;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.DECTrackingHelper;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.NoDECsPatterns;
import org.emoflon.ibex.tgg.operational.util.IbexOptions;

import language.DomainType;
import language.TGGRule;

public class TGGCompiler {	
	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns = new LinkedHashMap<>();
	private DECTrackingHelper decTC;
	private IbexOptions options;
	private Map<TGGRule, PatternFactory> factories;
		
	public TGGCompiler(IbexOptions options) {
		this.options = options;
		decTC = new DECTrackingHelper(ruleToPatterns);
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
				// TODO Not yet implemented
				
				ruleToPatterns.put(rule, factory.getPatterns());
			}

		}
		
		
		

//		
//      // add pattern invocations to CCPatterns for rule refinement
//		ruleToPatterns.values().stream()
//				   			   .flatMap(p -> p.stream())
//				   			   .filter(p -> p instanceof CCPattern)
//				   			   .forEach(p -> p.getRule().getRefines().stream()
//				   					   								 .flatMap(r -> ruleToPatterns.get(r).stream())
//				   					   								 .filter(pattern -> pattern instanceof CCPattern)
//				   					   								 .forEach(r -> p.addTGGPositiveInvocation(r)));
//
//		// add no DEC patterns to Src- and TrgPattern, respectively and register them
//		for (TGGRule rule : options.tgg().getRules()) {
//			addFilterACs(rule, DomainType.SRC, p -> p instanceof SrcProtocolAndDECPattern || p instanceof CCPattern);
//			addFilterACs(rule, DomainType.TRG, p -> p instanceof TrgProtocolAndDECPattern || p instanceof CCPattern);
//		}
	}
	
	public PatternFactory getFactory(TGGRule rule) {
		return factories.computeIfAbsent(rule, (k) -> new PatternFactory(k, this));
	}

	private void addFilterACs(TGGRule rule, DomainType domain, Predicate<IbexPattern> isRelevant) {
		List<IbexPattern> relevantPatterns = ruleToPatterns.get(rule)
			.stream()
			.filter(isRelevant)
			.collect(Collectors.toList());
		
		if (!relevantPatterns.isEmpty()) {
			NoDECsPatterns filterACPatterns = new NoDECsPatterns(rule, decTC, domain);
			ruleToPatterns.get(rule).add(filterACPatterns);
			relevantPatterns.forEach(p -> p.addTGGPositiveInvocation(filterACPatterns));
		}
	}

	public TGGRule getFlattenedVersionOfRule(TGGRule rule) {
		return options.flattenedTGG().getRules().stream()
				   .filter(r -> r.getName().equals(rule.getName()))
				   .findAny()
				   .get();
	}
}
