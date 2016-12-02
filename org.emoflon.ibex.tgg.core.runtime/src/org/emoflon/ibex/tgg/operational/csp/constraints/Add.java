package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;


public class Add extends RuntimeTGGAttributeConstraint
{
   /**
    * Constraint add(a,b,c) a + b = c
    */
   public void solve(RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b, RuntimeTGGAttributeConstraintVariable c)
   {
      String bindingStates = getBindingStates(a, b, c);

      if (bindingStates.equals("BBB"))
      {
         // a + b == c
         setSatisfied((((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
      } else if (bindingStates.equals("FBB"))
      {
         // a = c - b
         a.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BFB"))
      {
         // b = c - a
         b.bindToValue(((Number) c.getValue()).doubleValue() - ((Number) a.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BBF"))
      {
         // c = a + b
         c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      }
      // modelgen implementations
      else if (bindingStates.equals("FFB"))
      {
         setSatisfied(true);
         int newNumber = Generator.getNewUniqueNumber();
         a.bindToValue(newNumber);
         b.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) c.getValue()).doubleValue());
      } else if (bindingStates.equals("BFF"))
      {
         setSatisfied(true);
         long newNumber = Generator.getNewUniqueNumber();
         b.bindToValue(newNumber);
         c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
      } else if (bindingStates.equals("FBF"))
      {
         setSatisfied(true);
         long newNumber = Generator.getNewUniqueNumber();
         a.bindToValue(newNumber);
         c.bindToValue(((Number) a.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
      } else
      {
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }

   }

}
