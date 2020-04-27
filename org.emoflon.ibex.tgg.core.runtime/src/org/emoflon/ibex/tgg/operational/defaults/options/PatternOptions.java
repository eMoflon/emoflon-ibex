package org.emoflon.ibex.tgg.operational.defaults.options;

import java.util.function.BiPredicate;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACStrategy;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.TGGRuleNode;

public class PatternOptions extends IbexSubOptions {
	
	private FilterNACStrategy lookAheadStrategy;
	/**
	 * Switch to using edge patterns based on some heuristics (e.g., pattern size).
	 * If this is false (disabled), then edge patterns are never used.
	 */
	private boolean useEdgePatterns;
	private BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity;
	private boolean ignoreDomainConformity;

	public PatternOptions(IbexOptions options) {
		super(options);
		
		lookAheadStrategy = FilterNACStrategy.FILTER_NACS;
		useEdgePatterns = false;
		ignoreInjecitity = (x, y) -> false;
		ignoreDomainConformity = false;
	}
	
	public FilterNACStrategy lookAheadStrategy() {
		return lookAheadStrategy;
	}

	public IbexOptions lookAheadStrategy(FilterNACStrategy lookAheadStrategy) {
		this.lookAheadStrategy = lookAheadStrategy;
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

}
