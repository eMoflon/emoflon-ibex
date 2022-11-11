package org.emoflon.ibex.tgg.util.options;

import java.util.function.BiPredicate;

import org.emoflon.ibex.tgg.compiler.patterns.ACStrategy;
import org.emoflon.ibex.tgg.operational.patterns.GreenPatternFactoryProvider;
import org.emoflon.ibex.util.config.IbexOptions;

import language.TGGRuleNode;

public class PatternOptions extends IbexSubOptions {

	private GreenPatternFactoryProvider greenPatternFactories;
	private ACStrategy acStrategy;
	/**
	 * Switch to using edge patterns based on some heuristics (e.g., pattern size). If this is false
	 * (disabled), then edge patterns are never used.
	 */
	private boolean useEdgePatterns;
	private BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjectivity;
	private boolean ignoreDomainConformity;
	private boolean relaxDomainConformity;
	private boolean optimizePattern;
	private boolean useGenPattern;
	private boolean parallelizeMatchProcessing;
	private boolean optimizeCSPs;

	public PatternOptions(IbexOptions options) {
		super(options);

		greenPatternFactories = new GreenPatternFactoryProvider(options);
		acStrategy = ACStrategy.FILTER_NACS;
		useEdgePatterns = false;
		ignoreInjectivity = (x, y) -> false;
		ignoreDomainConformity = false;
		relaxDomainConformity = false;
		optimizePattern = false;
		useGenPattern = false;
		parallelizeMatchProcessing = true;
		optimizeCSPs = false;
	}

	public GreenPatternFactoryProvider greenPatternFactories() {
		return greenPatternFactories;
	}

	public IbexOptions greenPatternFactories(GreenPatternFactoryProvider greenPatternFactories) {
		this.greenPatternFactories = greenPatternFactories;
		return options;
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
		return ignoreInjectivity;
	}

	public IbexOptions ignoreInjectivity(BiPredicate<TGGRuleNode, TGGRuleNode> ignoreInjecitity) {
		this.ignoreInjectivity = ignoreInjecitity;
		return options;
	}

	public boolean ignoreDomainConformity() {
		return ignoreDomainConformity;
	}

	public IbexOptions ignoreDomainConformity(boolean ignoreDomainConformity) {
		this.ignoreDomainConformity = ignoreDomainConformity;
		return options;
	}

	public boolean relaxDomainConformity() {
		return relaxDomainConformity;
	}

	public IbexOptions relaxDomainConformity(boolean relaxDomainConformity) {
		this.relaxDomainConformity = relaxDomainConformity;
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

	public boolean parallelizeMatchProcessing() {
		return parallelizeMatchProcessing;
	}

	public IbexOptions parallelizeMatchProcessing(boolean parallelizeMatchProcessing) {
		this.parallelizeMatchProcessing = parallelizeMatchProcessing;
		return options;
	}

	public boolean optimizeCSPs() {
		return optimizeCSPs;
	}

	public IbexOptions optimizeCSPs(boolean optimizeCSPs) {
		this.optimizeCSPs = optimizeCSPs;
		return options;
	}

}
