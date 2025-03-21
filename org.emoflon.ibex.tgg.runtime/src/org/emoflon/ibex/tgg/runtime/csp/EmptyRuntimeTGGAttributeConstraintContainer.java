package org.emoflon.ibex.tgg.runtime.csp;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;

public class EmptyRuntimeTGGAttributeConstraintContainer implements IRuntimeTGGAttrConstrContainer {

	@Override
	public boolean solve() {
		return true;
	}

	@Override
	public void applyCSPValues(ITGGMatch comatch) {
		
	}

}
