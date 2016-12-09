package org.emoflon.ibex.tgg.operational.csp.constraints;

import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.emoflon.ibex.tgg.operational.csp.generator.Generator;

public class Sub extends RuntimeTGGAttributeConstraint
{

   /**
    * Constraint sub(a,b,c) a - b = c
    * 
    * @see TGGLanguage.csp.impl.ConstraintImpl#solve()
    */
	@Override
	public void solve() {
		if (variables.size() != 2)
			throw new RuntimeException("The CSP -SUB- needs exactly three variables");

		RuntimeTGGAttributeConstraintVariable a = variables.get(0);
		RuntimeTGGAttributeConstraintVariable b = variables.get(1);
		RuntimeTGGAttributeConstraintVariable c = variables.get(2);

      String bindingStates = getBindingStates(a, b, c);

      if (bindingStates.equals("BBB"))
      {
         // a - b == c
         setSatisfied((((Number) a.getValue()).doubleValue() - ((Number) b.getValue()).doubleValue()) == ((Number) c.getValue()).doubleValue());
      } else if (bindingStates.equals("FBB"))
      {
         // a = c + b
         a.bindToValue(((Number) c.getValue()).doubleValue() + ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BFB"))
      {
         // b = a - c
         b.bindToValue(((Number) a.getValue()).doubleValue() - ((Number) c.getValue()).doubleValue());
         setSatisfied(true);
      } else if (bindingStates.equals("BBF"))
      {
         // c = a - b
         c.bindToValue(((Number) a.getValue()).doubleValue() - ((Number) b.getValue()).doubleValue());
         setSatisfied(true);
      }
      // modelgen implementations
      else if (bindingStates.equals("FFF"))
      {
         int firstNumber = (int) Generator.getNewUniqueNumber();
         int secNumber = (int) Generator.getNewUniqueNumber();
         a.bindToValue(secNumber);
         b.bindToValue(firstNumber);
         c.bindToValue(firstNumber - secNumber);
         setSatisfied(true);
      } else if (bindingStates.equals("BFF"))
      {
         long firstNumber = Generator.getNewUniqueNumber();
         b.bindToValue(firstNumber);
         c.bindToValue(firstNumber - (long) a.getValue());
         setSatisfied(true);
      } else if (bindingStates.equals("FBF"))
      {
         long firstNumber = Generator.getNewUniqueNumber();
         a.bindToValue(firstNumber);
         c.bindToValue(firstNumber - (long) b.getValue());
         setSatisfied(true);
      } else if (bindingStates.equals("FFB"))
      {
         long firstNumber = Generator.getNewUniqueNumber();
         b.bindToValue(firstNumber);
         a.bindToValue((long) c.getValue() + (long) b.getValue());
         setSatisfied(true);
      } else
      {
         throw new UnsupportedOperationException("This case in the constraint has not been implemented yet: " + bindingStates);
      }

   }

}
