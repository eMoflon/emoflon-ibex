package org.emoflon.ibex.tgg.runtime.csp;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;

public interface IRuntimeTGGAttrConstrContainer {
	boolean solve();
	void applyCSPValues(ITGGMatch comatch);
}
