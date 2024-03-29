package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class INITIAL_BWD extends SYNC {

	public INITIAL_BWD(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	protected Set<PatternType> getRelevantOperationalPatterns() {
		return Collections.singleton(PatternType.BWD);
	}

}
