package org.emoflon.ibex.tgg.operational.csp.constraints.custom;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

import language.BindingType;

public class UserDefined_setContextAttribute extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint setContextAttribute(v0)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 1)
			throw new RuntimeException("The CSP -SETCONTEXTATTRIBUTE- needs exactly 1 variables");

		RuntimeTGGAttributeConstraintVariable v0 = variables.get(0);
		String bindingStates = getBindingStates(v0);

	  	switch(bindingStates) {
  		case "F": 
  			v0.bindToValue(BindingType.CONTEXT);
  			setSatisfied(true);
  			break;
  		case "B":
  			setSatisfied(BindingType.class.cast(v0.getValue()).equals(BindingType.CONTEXT));
  			break;
  		default:  throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
  		 	}
  	}
}

