package org.emoflon.ibex.tgg.operational.csp.constraints.custom;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.constraints.Add;

public class UserDefined_add_int extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint add_int(v0, v1, v2)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
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
			setSatisfied((((Number) a.getValue()).intValue()
					+ ((Number) b.getValue()).intValue()) == ((Number) c.getValue()).intValue());
		} else if (bindingStates.equals("FBB")) {
			// a = c - b
			a.bindToValue(((Number) c.getValue()).intValue() - ((Number) b.getValue()).intValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BFB")) {
			// b = c - a
			b.bindToValue(((Number) c.getValue()).intValue() - ((Number) a.getValue()).intValue());
			setSatisfied(true);
		} else if (bindingStates.equals("BBF")) {
			// c = a + b
			c.bindToValue(((Number) a.getValue()).intValue() + ((Number) b.getValue()).intValue());
			setSatisfied(true);
		}
		// modelgen implementations
		else if (bindingStates.equals("FFB")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(a.getType());
			a.bindToValue(newNumber);
			b.bindToValue(((Number) a.getValue()).intValue() + ((Number) c.getValue()).intValue());
		} else if (bindingStates.equals("BFF")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(b.getType());
			b.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).intValue() + ((Number) b.getValue()).intValue());
		} else if (bindingStates.equals("FBF")) {
			setSatisfied(true);
			int newNumber = (int) generateValue(a.getType());
			a.bindToValue(newNumber);
			c.bindToValue(((Number) a.getValue()).intValue() + ((Number) b.getValue()).intValue());
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}

