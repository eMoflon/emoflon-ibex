package org.emoflon.ibex.tgg.operational.csp.constraints.factories;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

public abstract class RuntimeTGGAttrConstraintFactory {

	protected Collection<String> constraints; 
	protected Map<String, Supplier<RuntimeTGGAttributeConstraint>> creators;
	
	public abstract RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name);
	public abstract boolean containsRuntimeTGGAttributeConstraint(String name);

	public Collection<String> getConstraintNames() {
		return constraints;
	}
}
