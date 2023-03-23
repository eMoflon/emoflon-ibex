package org.emoflon.ibex.tgg.runtime.strategies.sync;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.strategies.StrategyMode;

public class INITIAL_BWD extends SYNC {

	public INITIAL_BWD(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.BWD);
	}

	@Override
	public StrategyMode getStrategyMode() {
		return StrategyMode.INITIAL_BWD;
	}
}
