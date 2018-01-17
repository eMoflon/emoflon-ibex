package org.emoflon.ibex.tgg.operational.csp;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public interface IRuntimeTGGAttrConstrContainer {
	boolean solve();
	void applyCSPValues(IMatch comatch);
}
