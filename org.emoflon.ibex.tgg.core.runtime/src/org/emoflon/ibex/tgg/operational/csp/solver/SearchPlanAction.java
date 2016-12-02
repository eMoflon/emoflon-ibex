package org.emoflon.ibex.tgg.operational.csp.solver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraint;
import org.emoflon.ibex.tgg.operational.csp.RuntimeTGGAttributeConstraintVariable;
import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.plan.Algorithm;
import org.gervarro.democles.plan.WeightedOperation;

import language.csp.CspFactory;
import language.csp.TGGAttributeConstraintAdornment;

public class SearchPlanAction extends Algorithm<SimpleCombiner, RuntimeTGGAttributeConstraint>
{

   private List<RuntimeTGGAttributeConstraintVariable> variables;

   // Unsorted list of our constraints => swap AttributeConstraint with our real Constraint class
   // Sorted list of variables referenced from constraints => swap AttributeVariable with our real Variable class
   public SimpleCombiner sortConstraints(final List<RuntimeTGGAttributeConstraint> constraints, final List<RuntimeTGGAttributeConstraintVariable> variables)
   {
      this.variables = variables;

      // Create Combiner
      SimpleCombiner combiner = new SimpleCombiner();

      // 1. Determine inputAdornment (initial binding information) from sorted (!) list of variables
      Adornment inputAdornment = determineInputAdornment();

      // 2. Create weighted operations, one for each allowed adornment of each constraint
      List<WeightedOperation<RuntimeTGGAttributeConstraint>> weightedOperations = createWeightedOperations(constraints);

      // 3. Call search plan algorithm to sort weighted operations
      SimpleCombiner sc;
      PrintStream out = System.out;
      PrintStream err = System.err;
      System.setOut(new PrintStream(new OutputStream() {
          @Override public void write(int b) throws IOException {}
      }));
      System.setErr(new PrintStream(new OutputStream() {
         @Override public void write(int b) throws IOException {}
     }));
      try {
          sc = generatePlan(combiner, weightedOperations, inputAdornment);
      } finally {
          System.setOut(out);
          System.setErr(err);
      }
      
      return sc;
   }

   private Adornment determineInputAdornment()
   {
      boolean[] bits = new boolean[variables.size()];
      for (int i = 0; i < variables.size(); i++)
      {
         if (variables.get(i).isBound())
         {
            bits[i] = Adornment.B; // Bound <-> false !
         } else
         {
            bits[i] = Adornment.F; // Unbound <-> true !
         }
      }
      Adornment inputAdornment = new Adornment(bits);
      return inputAdornment;
   }

   /**
    * Create weighted operations from constraints
    * 
    * @param constraints
    * @return
    */
   private List<WeightedOperation<RuntimeTGGAttributeConstraint>> createWeightedOperations(final List<RuntimeTGGAttributeConstraint> constraints)
   {
      List<WeightedOperation<RuntimeTGGAttributeConstraint>> result = new ArrayList<WeightedOperation<RuntimeTGGAttributeConstraint>>();
      // for each constraint ...
      for (RuntimeTGGAttributeConstraint constraint : constraints)
      {
         // and each allowed adornment ...
         for (TGGAttributeConstraintAdornment adornment : constraint.getAllowedAdornments())
         {
            result.add(createWeightedOperationForConstraintWithAdornment(constraint, adornment));
         }
      }
      return result;
   }

   private WeightedOperation<RuntimeTGGAttributeConstraint> createWeightedOperationForConstraintWithAdornment(final RuntimeTGGAttributeConstraint constraint, final TGGAttributeConstraintAdornment adornment)
   {
      long frees = adornment.getValue().chars().filter(c -> c == 'F').count();
      float weight = (float) Math.pow(frees, 3);

      return createOperation(constraint, createBoundMask(constraint, adornment), createFreeMask(constraint, adornment), weight);
   }

   private TGGAttributeConstraintAdornment createBoundAdornment(final RuntimeTGGAttributeConstraint constraint)
   {
      final String adornmentValue = StringUtils.repeat("B", constraint.getVariables().size());

      TGGAttributeConstraintAdornment boundAdornment = CspFactory.eINSTANCE.createTGGAttributeConstraintAdornment();
      boundAdornment.setValue(adornmentValue);

      return boundAdornment;
   }

   private Adornment createBoundMask(final RuntimeTGGAttributeConstraint constraint, final TGGAttributeConstraintAdornment adornment)
   {

      boolean[] bits = new boolean[variables.size()];

      for (int i = 0; i < constraint.getVariables().size(); i++)
      {
         RuntimeTGGAttributeConstraintVariable variable = constraint.getVariables().get(i);
         int index = variables.indexOf(variable);
         if (adornment.getValue().charAt(i) == 'B')
         {
            bits[index] = true;
         }
      }

      return new Adornment(bits);
   }

   private Adornment createFreeMask(final RuntimeTGGAttributeConstraint constraint, final TGGAttributeConstraintAdornment adornment)
   {
      boolean[] bits = new boolean[variables.size()];

      for (int i = 0; i < constraint.getVariables().size(); i++)
      {
         RuntimeTGGAttributeConstraintVariable variable = constraint.getVariables().get(i);
         int index = variables.indexOf(variable);
         if (adornment.getValue().charAt(i) == 'F')
         {
            bits[index] = true;
         }
      }

      return new Adornment(bits);
   }

}
