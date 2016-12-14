package org.emoflon.ibex.tgg.operational.csp.constraints;

import java.util.regex.Pattern;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class Concat extends RuntimeTGGAttributeConstraint {

	/**
	 * concat(":", a, b, c)
	 * 
	 * a . ":" . b = c
	 * 
	 */
	@Override
	public void solve() {
		if (variables.size() != 4)
			throw new RuntimeException("The CSP -CONCAT- needs exactly four variables");

		RuntimeTGGAttributeConstraintVariable separator = variables.get(0);
		RuntimeTGGAttributeConstraintVariable a = variables.get(1);
		RuntimeTGGAttributeConstraintVariable b = variables.get(2);
		RuntimeTGGAttributeConstraintVariable c = variables.get(3);

		String bindingStates = getBindingStates(separator, a, b, c);

		switch (bindingStates) {
		case "BBBB": {
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BBBF": {
			c.bindToValue((String) a.getValue() + separator.getValue() + b.getValue());
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BBFB": {
			String[] split = ((String) c.getValue()).split(Pattern.quote((String) separator.getValue()));
			if (split.length != 2) {
				setSatisfied(false);
			} else {
				b.bindToValue(split[1]);
				setSatisfied(checkAllValues(separator, a, b, c));
			}
			return;
		}

		case "BFBB": {
			String[] split = c.getValue().toString().split(Pattern.quote((String) separator.getValue()));
			a.bindToValue(split[0]);
			setSatisfied(checkAllValues(separator, a, b, c));
			return;
		}

		case "BFFB": {
			String[] split = c.getValue().toString().split(Pattern.quote((String) separator.getValue()));
			if (split.length == 2) {
				a.bindToValue(split[0]);
				b.bindToValue(split[1]);
				setSatisfied(checkAllValues(separator, a, b, c));
			}
			return;
		}

		// modelgen implementations
		case "BFFF": {
			setSatisfied(true);
			String value1 = (String) generateValue(a.getType());
			String value2 = (String) generateValue(b.getType());
			a.bindToValue(value1);
			b.bindToValue(value2);
			c.bindToValue(value1 + separator.getValue() + value2);
			return;
		}

		case "BBFF": {
			setSatisfied(true);
			String value2 = (String) generateValue(c.getType());
			c.bindToValue((String) a.getValue() + separator.getValue() + value2);
			return;
		}

		case "BFBF": {
			setSatisfied(true);
			String value1 = (String) generateValue(a.getType());
			a.bindToValue(value1);
			c.bindToValue(value1 + separator.getValue() + b.getValue());
			return;
		}

		default:
			throw new UnsupportedOperationException(
					"This case in the constraint has not been implemented yet: " + bindingStates);
		}
	}

	private boolean checkAllValues(RuntimeTGGAttributeConstraintVariable separator,
			RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b,
			RuntimeTGGAttributeConstraintVariable c) {
		return ((String) a.getValue() + separator.getValue() + b.getValue()).equals(c.getValue());
	}
}
