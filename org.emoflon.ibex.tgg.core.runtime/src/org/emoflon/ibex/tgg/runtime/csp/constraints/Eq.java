package org.emoflon.ibex.tgg.runtime.csp.constraints;

import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;

public class Eq extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint eq(a,b)
	 * 
	 */
	@Override
	public void solve() {

		if (variables.size() != 2)
			throw new RuntimeException("The CSP " + getClass().getName() + " needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);

		String bindingStates = getBindingStates(a, b);

		if (bindingStates.equals("BB")) {
			if(a.getValue() == null)
				setSatisfied(b.getValue() == null);
			else
				setSatisfied(a.getValue().equals(b.getValue()));
		} else if (bindingStates.equals("BF")) {
			b.bindToValue(a.getValue());
			setSatisfied(true);
		} else if (bindingStates.equals("FB")) {
			a.bindToValue(b.getValue());
			setSatisfied(true);
		}
		// modelgen implementation
		else if (bindingStates.equals("FF")) {
			String type = a.getType();
			Object value = generateValue(type);
			a.bindToValue(value);
			b.bindToValue(value);
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}
}
