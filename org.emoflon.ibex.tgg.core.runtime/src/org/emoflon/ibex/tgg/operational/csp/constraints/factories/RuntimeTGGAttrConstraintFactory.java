package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

public abstract class RuntimeTGGAttrConstraintFactory {
	public abstract RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name);
	public abstract boolean containsRuntimeTGGAttributeConstraint(String name);

}
