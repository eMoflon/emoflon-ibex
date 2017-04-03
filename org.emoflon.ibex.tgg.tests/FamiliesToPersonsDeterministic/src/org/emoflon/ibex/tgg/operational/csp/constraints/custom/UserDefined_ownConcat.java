package org.emoflon.ibex.tgg.operational.csp.constraints.custom;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.constraints.Concat;

public class UserDefined_ownConcat extends Concat
{

   /**
    * Constraint ownConcat(v0, v1, v2, v3)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 4)
			throw new RuntimeException("The CSP -OWNCONCAT- needs exactly 4 variables");

		RuntimeTGGAttributeConstraintVariable v0 = variables.get(0);
		RuntimeTGGAttributeConstraintVariable v1 = variables.get(1);
		RuntimeTGGAttributeConstraintVariable v2 = variables.get(2);
		RuntimeTGGAttributeConstraintVariable v3 = variables.get(3);
		String bindingStates = getBindingStates(v0, v1, v2, v3);

	  	switch(bindingStates) {
	  		case "BBBB": 
	  		case "BBBF": 
	  		case "BBFB": 
	  		case "BFFB": 
	  		case "BFBB": 
	  		case "BFBF": 
	  		case "BBFF": super.solve(); return;
	  		case "BFFF": v1.bindToValue(""); v2.bindToValue(""); v3.bindToValue(" ");
	  		default:  throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
	  		 	}
	  	}
}

