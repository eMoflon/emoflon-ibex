package org.emoflon.ibex.tgg.runtime.csp.constraints;

import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;

public class Max extends RuntimeTGGAttributeConstraint {

	/**
	 * Constraint max(a,b,c) c = max(a,b)
	 * 
	 */
	@Override
	public void solve() {
		if (variables.size() != 3)
			throw new RuntimeException("The CSP -MAX- needs exactly three variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		RuntimeTGGAttributeConstraintVariable maximum = variables.get(2);

		String bindingStates = getBindingStates(a, b, maximum);

		// check
		if (bindingStates.equals("BBB")) {
			setSatisfied(maximum.getValue().equals(max(a, b)));
		}
		// maximum := max(a,b)
		else if (bindingStates.equals("BBF")) {
			maximum.bindToValue(max(a, b));
			setSatisfied(true);
		}
		// maximum = max(a,b) - check if a=maximum and set b to 0...
		else if (bindingStates.equals("BFB")) {
			// is a the maximum?
			if (a.getValue().equals(maximum.getValue())) {
				b.bindToValue(0);
				setSatisfied(true);
			}

		}
	}

	private Number max(RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b) {
		return Math.max(((Number) a.getValue()).doubleValue(), ((Number) b.getValue()).doubleValue());
	}

}
