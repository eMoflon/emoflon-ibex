package org.emoflon.ibex.tgg.operational.strategies.sync;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

public class FWD_OPT_Strategy extends FWD_Strategy {
	@Override
	public boolean isPatternRelevantForInterpreter(String patternName) {
		return patternName.endsWith(PatternSuffixes.FWD_OPT);
	}
}