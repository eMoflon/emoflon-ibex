package org.emoflon.ibex.tgg.runtime.csp.constraints;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.runtime.csp.RuntimeTGGAttributeConstraintVariable;

public class SetRandomString extends RuntimeTGGAttributeConstraint {

	Logger logger = Logger.getLogger(SetRandomString.class);

	@Override
	public void solve() {
		if (variables.size() != 1)
			throw new RuntimeException("The CSP -SET_RANDOM_STRING- needs exactly one variables");

		RuntimeTGGAttributeConstraintVariable v0 = variables.get(0);

		String bindingStates = getBindingStates(v0);

		if (bindingStates.equals("B")) {
				setSatisfied(true);
				
		} else if (bindingStates.equals("F")) {
			v0.bindToValue(generateValue(v0.getType()));
			setSatisfied(true);
		}
		 else {
				throw new UnsupportedOperationException(
						"This case in the constraint has not been implemented yet: " + bindingStates);
			}
	}

}
