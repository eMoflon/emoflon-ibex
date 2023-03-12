package org.emoflon.ibex.tgg.runtime.strategies.modules;

import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;

public abstract class MatchConsumer {

	protected final IbexOptions options;
	protected RuleHandler ruleHandler;
	protected final Set<PatternType> patternSet;

	public MatchConsumer(IbexOptions options) {
		this(options, null);
	}

	public MatchConsumer(IbexOptions options, Set<PatternType> patternSet) {
		this.options = options;
		this.patternSet = patternSet;
		registerAtMatchDistributor(options.matchDistributor());
		ruleHandler = options.tgg.ruleHandler();
	}

	protected abstract void registerAtMatchDistributor(MatchDistributor matchDistributor);

}
