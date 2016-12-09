package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class SmallerOrEqual extends RuntimeTGGAttributeConstraint {

	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -SMALLER_OR_EQUAL- needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		
		String bindingStates = getBindingStates(a, b);

		if (bindingStates.equals("BB")) {
			setSatisfied(((Number) a.getValue()).doubleValue() <= ((Number) b.getValue()).doubleValue());
		} else if (bindingStates.equals("BF")) {
			b.bindToValue(a.getValue());
			setSatisfied(true);
		} else if (bindingStates.equals("FB")) {
			a.bindToValue(b.getValue());
			setSatisfied(true);
		}
		// modelgen implementations
		else if (bindingStates.equals("FF")) {
			int number = Generator.getNewUniqueNumber();
			a.bindToValue(number);
			b.bindToValue(number);
			setSatisfied(true);
		} else {
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}

}
