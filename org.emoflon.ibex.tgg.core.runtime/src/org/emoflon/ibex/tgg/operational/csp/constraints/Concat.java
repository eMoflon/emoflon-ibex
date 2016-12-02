package org.emoflon.ibex.tgg.operational.csp.constraints;

import java.util.regex.Pattern;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class Concat extends RuntimeTGGAttributeConstraint
{

   /**
    * concat(":", a, b, c)
    * 
    * a . ":" . b = c
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
   public void solve(RuntimeTGGAttributeConstraintVariable separator, RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b, RuntimeTGGAttributeConstraintVariable c)
   {
      String bindingStates = getBindingStates(separator, a, b, c);

      switch (bindingStates)
      {
      case "BBBB":
      {
         setSatisfied(checkAllValues(separator, a, b, c));
         return;
      }

      case "BBBF":
      {
         c.bindToValue((String) a.getValue() + separator.getValue() + b.getValue());
         setSatisfied(checkAllValues(separator, a, b, c));
         return;
      }

      case "BBFB":
      {
         String[] split = ((String) c.getValue()).split(Pattern.quote((String) separator.getValue()));
         if (split.length != 2)
         {
            setSatisfied(false);
         } else
         {
            b.bindToValue(split[1]);
            setSatisfied(checkAllValues(separator, a, b, c));
         }
         return;
      }

      case "BFBB":
      {
         String[] split = c.getValue().toString().split(Pattern.quote((String) separator.getValue()));
         a.bindToValue(split[0]);
         setSatisfied(checkAllValues(separator, a, b, c));
         return;
      }

      case "BFFB":
      {
         String[] split = c.getValue().toString().split(Pattern.quote((String) separator.getValue()));
         if (split.length == 2)
         {
            a.bindToValue(split[0]);
            b.bindToValue(split[1]);
            setSatisfied(checkAllValues(separator, a, b, c));
         }
         return;
      }
      
      // modelgen implementations
      case "BFFF":
      {
         setSatisfied(true);
         String value1 = Generator.getNewRandomString(a.getType());
         String value2 = Generator.getNewRandomString(b.getType());
         a.bindToValue(value1);
         b.bindToValue(value2);
         c.bindToValue(value1 + separator.getValue() + value2);
         return;
      }

      case "BBFF":
      {
         setSatisfied(true);
         String value2 = Generator.getNewRandomString(c.getType());
         c.bindToValue((String) a.getValue() + separator.getValue() + value2);
         return;
      }

      case "BFBF":
      {
         setSatisfied(true);
         String value1 = Generator.getNewRandomString(a.getType());
         a.bindToValue(value1);
         c.bindToValue(value1 + separator.getValue() + b.getValue());
         return;
      }

      default:
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }
   }

   private boolean checkAllValues(RuntimeTGGAttributeConstraintVariable separator, RuntimeTGGAttributeConstraintVariable a, RuntimeTGGAttributeConstraintVariable b, RuntimeTGGAttributeConstraintVariable c) {
	   return ((String) a.getValue() + separator.getValue() + b.getValue()).equals(c.getValue());
   }
}
