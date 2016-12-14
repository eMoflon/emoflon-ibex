package org.emoflon.ibex.tgg.operational.csp.constraints.custom;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class UserDefined_uppercase extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint uppercase(v0, v1)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -UPPERCASE- needs exactly 2 variables");

		RuntimeTGGAttributeConstraintVariable v0 = variables.get(0);
		RuntimeTGGAttributeConstraintVariable v1 = variables.get(1);
      	String bindingStates = getBindingStates(v0, v1);

	  	switch(bindingStates) {
	  		case "BB": setSatisfied(true); break;
	  		case "BF": v1.bindToValue(((String) v0.getValue()).toUpperCase()); setSatisfied(true); break;
	  		case "FB": v0.bindToValue(((String) v1.getValue()).toUpperCase()); setSatisfied(true); break;
	  		case "FF": v0.bindToValue("test"); v1.bindToValue("TEST"); setSatisfied(true); break;
       		default:  throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      	}
   	}
}

