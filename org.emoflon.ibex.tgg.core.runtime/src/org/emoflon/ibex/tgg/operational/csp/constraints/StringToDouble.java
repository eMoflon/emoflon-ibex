package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.apache.log4j.Logger;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class StringToDouble extends RuntimeTGGAttributeConstraint
{

   Logger logger = Logger.getLogger(StringToDouble.class);

   public void solve(RuntimeTGGAttributeConstraintVariable string, RuntimeTGGAttributeConstraintVariable number)
   {
      String bindingStates = getBindingStates(string, number);

      try
      {
         if (bindingStates.equals("BB"))
         {
            if (new Double((String) string.getValue()).equals(((Number) number.getValue()).doubleValue()))
               setSatisfied(true);
            else
               setSatisfied(false);
         } else if (bindingStates.equals("FB"))
         {
            string.bindToValue(number.getValue().toString());
            setSatisfied(true);
         } else if (bindingStates.equals("BF"))
         {
            number.bindToValue(new Double((String) string.getValue()));
            setSatisfied(true);
         }
         // modelgen implementations
         else if (bindingStates.equals("FF"))
         {
            int newNumber = Generator.getNewUniqueNumber();
            number.bindToValue(newNumber);
            string.bindToValue(number.getValue().toString());
            setSatisfied(true);
         } else
         {
            throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
         }
      } catch (NumberFormatException e)
      {
         logger.warn("NumberFormatException during csp solving");
         setSatisfied(false);
      }
   }

}
