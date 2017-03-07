package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.List;

import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

public abstract class SYNC_Strategy {
	public abstract boolean manipulateSrc();
	public abstract boolean manipulateTrg();
	public abstract List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library);
}
