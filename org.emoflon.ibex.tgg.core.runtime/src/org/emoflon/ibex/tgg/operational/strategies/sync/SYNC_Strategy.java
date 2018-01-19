package org.emoflon.ibex.tgg.operational.strategies.sync;

import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPatternFactory;

public abstract class SYNC_Strategy {
	public abstract boolean isPatternRelevantForInterpreter(String patternName);
	public abstract IGreenPattern revokes(IGreenPatternFactory greenFactory, String patternName, String ruleName);
}
