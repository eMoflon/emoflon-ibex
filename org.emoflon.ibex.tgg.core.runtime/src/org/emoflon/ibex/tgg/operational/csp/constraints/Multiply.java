package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;

public class Multiply extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint multiply(a,b,c) a * b = c
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 3)
			throw new RuntimeException("The CSP -MULTIPLY- needs exactly three variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		RuntimeTGGAttributeConstraintVariable c = variables.get(2);

      String bindingStates = getBindingStates(a,b,c);

      if (bindingStates.equals("BBB"))
      {
         setSatisfied((((Number) a.getValue()).doubleValue()*((Number) b.getValue()).doubleValue())==((Number) c.getValue()).doubleValue());
      } else if (bindingStates.equals("FBB"))
      {
         a.bindToValue(((Number) c.getValue()).doubleValue() / ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BFB"))
      {
         b.bindToValue(((Number) c.getValue()).doubleValue() / ((Number) a.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BBF"))
      {
         c.bindToValue(((Number) a.getValue()).doubleValue() * ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      }    

   }

}
