package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public class BWD_Strategy extends SYNC_Strategy {
	
	@Override
	public boolean manipulateSrc() {
		return true;
	}

	@Override
	public boolean manipulateTrg() {
		return false;
	}

	@Override
	public boolean manipulateCorr() {
		return true;
	}
	
	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_BWD();
	}
}
