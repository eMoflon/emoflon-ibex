package org.emoflon.ibex.tgg.operational.defaults.options;

import java.util.function.BiPredicate;

import org.emoflon.ibex.tgg.compiler.patterns.ACStrategy;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRuleNode;

public class PatternOptions extends IbexSubOptions {
	
	private ACStrategy acStrategy;
	/**
	 * Switch to using edge patterns based on some heuristics (e.g., pattern size).
	 * If this is false (disabled), then edge patterns are never used.
	 */
	private boolean useEdgePatterns;
	private BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity;
	private boolean ignoreDomainConformity;
	private boolean optimizePattern;
	private boolean useGenPattern;

	public PatternOptions(IbexOptions options) {
		super(options);
		
		acStrategy = ACStrategy.FILTER_NACS;
		useEdgePatterns = false;
		ignoreInjecitity = (x, y) -> false;
		ignoreDomainConformity = false;
		optimizePattern = false;
		useGenPattern = false;
	}
	
	public ACStrategy acStrategy() {
		return acStrategy;
	}

	public IbexOptions acStrategy(ACStrategy acStrategy) {
		this.acStrategy = acStrategy;
		return options;
	}

	public boolean useEdgePatterns() {
		return useEdgePatterns;
	}

	public IbexOptions useEdgePatterns(boolean value) {
		useEdgePatterns = value;
		return options;
	}

	public BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjectivity() {
		return ignoreInjecitity;
	}

	public IbexOptions ignoreInjectivity(BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity) {
		this.ignoreInjecitity = ignoreInjecitity;
		return options;
	}

	public boolean ignoreDomainConformity() {
		return ignoreDomainConformity;
	}

	public IbexOptions ignoreDomainConformity(boolean ignoreDomainConformity) {
		this.ignoreDomainConformity = ignoreDomainConformity;
		return options;
	}
	
	public boolean optimizePattern() {
		return optimizePattern;
	}

	public IbexOptions optimizePattern(boolean optimizePattern) {
		this.optimizePattern = optimizePattern;
		return options;
	}
	
	public boolean useGenPattern() {
		return useGenPattern;
	}

	public IbexOptions useGenPattern(boolean useGenPattern) {
		this.useGenPattern = useGenPattern;
		return options;
	}

}
