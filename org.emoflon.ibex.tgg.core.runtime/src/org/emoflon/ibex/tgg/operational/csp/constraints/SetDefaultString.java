package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class SetDefaultString extends RuntimeTGGAttributeConstraint {
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -SET_DEFAULT_STRING- needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable var_0 = variables.get(0);
		RuntimeTGGAttributeConstraintVariable var_1 = variables.get(1);
		String bindingStates = getBindingStates(var_0, var_1);

		switch (bindingStates) {
		case "BB":
			setSatisfied(true);
			return;
		case "FB":
			var_0.bindToValue(var_1.getValue());
			setSatisfied(true);
			return;

		case "FF":

			var_0.bindToValue(generateValue(var_0.getType()));
			var_1.bindToValue(var_0.getValue());
			setSatisfied(true);
			return;

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}

	}
}