package org.emoflon.ibex.tgg.operational.csp;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

public interface IRuntimeTGGAttrConstrContainer {
	boolean solve();
	void applyCSPValues(ITGGMatch comatch);
}
