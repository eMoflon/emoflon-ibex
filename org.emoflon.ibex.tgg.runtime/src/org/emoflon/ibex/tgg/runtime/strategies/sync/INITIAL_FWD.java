package org.emoflon.ibex.tgg.runtime.strategies.sync;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.strategies.StrategyMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;

public class INITIAL_FWD extends SYNC {
	public INITIAL_FWD(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.FWD);
	}

	@Override
	public StrategyMode getStrategyMode() {
		return StrategyMode.INITIAL_FWD;
	}
	
	@Override
	public OperationalisationMode currentlyAppliedRuleMode() {
		return OperationalisationMode.FORWARD;
	}
}
