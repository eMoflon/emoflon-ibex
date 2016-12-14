package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;

public class RuntimeTGGAttributeConstraintFactory {

	public RuntimeTGGAttributeConstraintFactory() {
	}
	
	public RuntimeTGGAttributeConstraint createRuntimeTGGAttributeConstraint(String name) {
		switch(name) {
		case "add": return new Add();
		case "addPrefix": return new AddPrefix();
		case "addSuffix": return new AddSuffix();
		case "concat": return new Concat();
		case "divide": return new Divide();
		case "eq_boolean": return new Eq();
		case "eq_char": return new Eq();
		case "eq_double": return new Eq();
		case "eq_float": return new Eq();
		case "eq_int": return new Eq();
		case "eq_long": return new Eq();
		case "eq_string": return new Eq();
		case "max": return new Max();
		case "multiply": return new Multiply();
		case "setDefaultNumber": return new SetDefaultNumber();
		case "setDefaultString": return new SetDefaultString();
		case "smallerOrEqual": return new SmallerOrEqual();
		case "stringToDouble": return new StringToDouble();
		case "stringToInt": return new StringToInt();
		case "sub": return new Sub();
			default: throw new RuntimeException("CSP not implemented");
		}
	}
}
