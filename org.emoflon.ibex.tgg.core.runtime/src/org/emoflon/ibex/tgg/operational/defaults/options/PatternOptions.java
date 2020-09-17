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
	private boolean useSrcTrgPattern;
	private boolean useGreenCorrPattern;
	private boolean optimizeSyncPattern;

	public PatternOptions(IbexOptions options) {
		super(options);
		
		lookAheadStrategy = FilterNACStrategy.FILTER_NACS;
		useEdgePatterns = false;
		ignoreInjecitity = (x, y) -> false;
		ignoreDomainConformity = false;
		useSrcTrgPattern = false;
		useGreenCorrPattern = false;
		optimizeSyncPattern = false;
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
	
	public boolean useSrcTrgPattern() {
		return useSrcTrgPattern;
	}

	public IbexOptions useSrcTrgPattern(boolean value) {
		useSrcTrgPattern = value;
		return options;
	}
	
	public boolean useGreenCorrPattern() {
		return useGreenCorrPattern;
	}
	
	public IbexOptions useGreenCorrPattern(boolean value) {
		useGreenCorrPattern = value;
		return options;
	}
	
	public boolean optimizeSyncPattern() {
		return optimizeSyncPattern;
	}

	public IbexOptions optimizeSyncPattern(boolean optimizeSyncPattern) {
		this.optimizeSyncPattern = optimizeSyncPattern;
		return options;
	}

}
