package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.constraints.helper.EqValueGenerator;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class Eq_EChar extends RuntimeTGGAttributeConstraint implements EqValueGenerator
{

   /**
    * Constraint eq(a,b)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -EQ_CHAR- needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);

      String bindingStates = getBindingStates(a, b);

      if (bindingStates.equals("BB"))
      {
        a.getValue().equals(b.getValue());
      } else if (bindingStates.equals("BF"))
      {
         b.bindToValue(a.getValue());
         setSatisfied(true);
      } else if (bindingStates.equals("FB"))
      {
         a.bindToValue(b.getValue());
         setSatisfied(true);
      }
      // modelgen implementation
      else if (bindingStates.equals("FF"))
      {
         String type = a.getType();
         Object value = generateValue(type);
         a.bindToValue(value);
         b.bindToValue(value);
         setSatisfied(true);
      } else
      {
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }
   }

   private void compareNumbers(RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b)
   {
      Number anum = (Number) a.getValue();
      Number bnum = (Number) b.getValue();
      setSatisfied(anum.doubleValue() == bnum.doubleValue());
   }

}
