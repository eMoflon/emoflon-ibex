package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

public class INITIAL_FWD extends SYNC {
	public INITIAL_FWD(IbexOptions options) throws IOException {
		super(options);
	}

	@Override
	public Collection<PatternType> getPatternRelevantForCompiler() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.FWD);
		return types;
	}
}
