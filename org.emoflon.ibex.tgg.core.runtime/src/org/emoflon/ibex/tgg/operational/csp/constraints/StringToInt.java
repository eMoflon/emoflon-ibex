package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class StringToInt extends RuntimeTGGAttributeConstraint {

	Logger logger = Logger.getLogger(StringToInt.class);

	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -STRING_TO_INT- needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable string = variables.get(0);
		RuntimeTGGAttributeConstraintVariable number = variables.get(1);

		String bindingStates = getBindingStates(string, number);

		try {
			if (bindingStates.equals("BB")) {
				if (new Integer((String) string.getValue()).equals(((Integer) number.getValue()).intValue()))
					setSatisfied(true);
				else
					setSatisfied(false);
			} else if (bindingStates.equals("FB")) {
				string.bindToValue(number.getValue().toString());
				setSatisfied(true);
			} else if (bindingStates.equals("BF")) {
				number.bindToValue(new Integer((String) string.getValue()));
				setSatisfied(true);
			}
			// modelgen implementations
			else if (bindingStates.equals("FF")) {
				int newNumber = (int) generateValue(number.getType());
				number.bindToValue(newNumber);
				string.bindToValue(number.getValue().toString());
				setSatisfied(true);
			} else {
				throw new UnsupportedOperationException(
						"This case in the constraint has not been implemented yet: " + bindingStates);
			}
		} catch (NumberFormatException e) {
			logger.warn("NumberFormatException during csp solving");
			setSatisfied(false);
		}
	}

}
