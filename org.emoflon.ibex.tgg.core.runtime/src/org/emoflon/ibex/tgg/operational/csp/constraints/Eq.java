package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class Eq extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint eq(a,b)
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -EQ- needs exactly two variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);

      String bindingStates = getBindingStates(a, b);

      if (bindingStates.equals("BB"))
      {
         if (a.getValue() instanceof Number)
            compareNumbers(a, b);
         else
            setSatisfied(a.getValue().equals(b.getValue()));
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

   private Object generateValue(String type)
   {
      Object value = null;
      
      if(type.equals("String"))
      {
         value = Generator.getNewRandomString(null);     
      }
      else if(type.equals("Integer"))
      {
         value = Integer.valueOf((int) (Math.random() * 1000.0));
      }
      else if(type.equals("Double"))
      {
         value = Double.valueOf((Math.random() * 1000.0));
      }
      else if(type.equals("Boolean"))
      {
         value = Boolean.valueOf((Math.random() > Math.random()));
      }
      else 
         throw new RuntimeException("The type " + type + " is not supported for random value generation in Eq-constraints");
      return value;
   }

   private void compareNumbers(RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b)
   {
      Number anum = (Number) a.getValue();
      Number bnum = (Number) b.getValue();
      setSatisfied(anum.doubleValue() == bnum.doubleValue());
   }

}
