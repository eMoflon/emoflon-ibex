package org.emoflon.ibex.tgg.operational.csp;

import org.emoflon.ibex.tgg.operational.matches.IMatch;

public class EmptyRuntimeTGGAttributeConstraintContainer implements IRuntimeTGGAttrConstrContainer {

	@Override
	public boolean solve() {
		return true;
	}

	@Override
	public void applyCSPValues(IMatch comatch) {
		
	}

}
