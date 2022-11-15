package org.emoflon.ibex.tgg.runtime.strategies.modules;

import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.util.config.IbexOptions;

public abstract class MatchConsumer {

	protected final IbexOptions options;
	protected final Set<PatternType> patternSet;

	public MatchConsumer(IbexOptions options) {
		this.options = options;
		this.patternSet = null;
		registerAtMatchDistributor(options.matchDistributor());
	}

	public MatchConsumer(IbexOptions options, Set<PatternType> patternSet) {
		this.options = options;
		this.patternSet = patternSet;
		registerAtMatchDistributor(options.matchDistributor());
	}

	protected abstract void registerAtMatchDistributor(MatchDistributor matchDistributor);

}
