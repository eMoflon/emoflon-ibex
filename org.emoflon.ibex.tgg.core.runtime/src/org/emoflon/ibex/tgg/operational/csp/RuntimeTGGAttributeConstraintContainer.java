package org.emoflon.ibex.tgg.operational.csp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RuntimeTGGAttributeConstraintContainer {

	private Collection<RuntimeTGGAttributeConstraint> constraints;
	
	public RuntimeTGGAttributeConstraintContainer() {
		constraints = new ArrayList<RuntimeTGGAttributeConstraint>();
	}
	
	public void addConstraint(RuntimeTGGAttributeConstraint constraint) {
		constraints.add(constraint);
	}
	
	public Collection<RuntimeTGGAttributeConstraint> getConstraints() {
		return constraints;
	}
	
	public boolean isSatisfied() {
		return constraints.stream().map(c -> c.isSatisfied()).reduce(true, (b1, b2) -> b1 && b2).booleanValue();
	}
}
