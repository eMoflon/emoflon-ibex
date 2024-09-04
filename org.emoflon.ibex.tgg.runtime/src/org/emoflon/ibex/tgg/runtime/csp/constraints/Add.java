package org.emoflon.ibex.tgg.runtime.csp.constraints;

import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;

public class Add extends RuntimeTGGAttributeConstraint {
	/**
	 * Constraint add(a,b,c) a + b = c
	 */
	@Override
	public void solve() {
		if (variables.size() != 3)
			throw new RuntimeException("The CSP -ADD- needs exactly three variables");
		
		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		RuntimeTGGAttributeConstraintVariable c = variables.get(2);
		
		String bindingStates = getBindingStates(a, b, c);
		
		if (bindingStates.equals("BBB")) {
			// a + b == c
			setSatisfied((((Number) a.getValue()).doubleValue()
					+ ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("FBB")) {
			// a = c - b
			a.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BFB")) {
			// b = c - a
			b.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) a.getValue()).doubleValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BBF")) {
			// c = a + b
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
			setSatisfied(true);
		}
		// modelgen implementations
		else if (bindingStates.equals("FFB")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(a.getType());
			a.bindToValue(newNumber);
			b.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) c.getValue()).doubleValue());
		} else if (bindingStates.equals("BFF")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(b.getType());
			b.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
		} else if (bindingStates.equals("FBF")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(a.getType());
			a.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

}
