package org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.plan;

import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.common.Adornment;

public interface ReachabilityAnalyzer {
	public boolean isReachable(Adornment adornment);
}
