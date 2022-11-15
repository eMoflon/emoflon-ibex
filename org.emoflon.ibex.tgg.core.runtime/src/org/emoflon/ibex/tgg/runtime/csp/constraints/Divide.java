package org.emoflon.ibex.tgg.runtime.csp.constraints;

import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;

public class Divide extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint divide(a,b,c) a / b = c

	 */
	@Override
	public void solve() {
		if (variables.size() != 3)
			throw new RuntimeException("The CSP -DIVIDE- needs exactly three variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		RuntimeTGGAttributeConstraintVariable c = variables.get(2);

		String bindingStates = getBindingStates(a, b, c);

		if (bindingStates.equals("BBB")) {
			setSatisfied((((Number) a.getValue()).doubleValue()
					/ ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("FBB")) {
			a.bindToValue(((Number) c.getValue()).doubleValue() * ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BFB")) {
			b.bindToValue(((Number) a.getValue()).doubleValue() / ((Number) c.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BBF")) {
			c.bindToValue(((Number) a.getValue()).doubleValue() / ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		}

	}

}
