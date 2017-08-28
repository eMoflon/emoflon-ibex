package org.emoflon.ibex.tgg.core.transformation.csp.sorting;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.gervarro.democles.common.Adornment;
import org.gervarro.democles.plan.Algorithm;
import org.gervarro.democles.plan.WeightedOperation;

import com.google.common.collect.Lists;

import language.BindingType;
import language.DomainType;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeVariable;
import language.csp.definition.TGGAttributeConstraintAdornment;

public class SearchPlanAction extends Algorithm<SimpleCombiner, TGGAttributeConstraint> {

	private List<TGGParamValue> variables;

	// Unsorted list of our constraints => return a new List where constraints
	// are sorted according to the search plan
	public List<TGGAttributeConstraint> sortConstraints(final List<TGGAttributeConstraint> constraints,
			final List<TGGParamValue> variables, final CSPSearchPlanMode mode) {
		this.variables = variables;

		// Create Combiner
		SimpleCombiner combiner = new SimpleCombiner();

		// 1. Determine inputAdornment (initial binding information) from sorted
		// (!) list of variables
		Adornment inputAdornment = determineInputAdornment(mode);

		// 2. Create weighted operations, one for each allowed adornment of each
		// constraint
		List<WeightedOperation<TGGAttributeConstraint>> weightedOperations = createWeightedOperations(constraints,
				mode);

		// 3. Call search plan algorithm to sort weighted operations
		SimpleCombiner sc;
		List<TGGAttributeConstraint> sortedList;
		PrintStream out = System.out;
		PrintStream err = System.err;
		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		System.setErr(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) throws IOException {
			}
		}));
		try {
			sc = generatePlan(combiner, weightedOperations, inputAdornment);
			sortedList = new ArrayList<>();
			Chain<?> c = sc.getRoot();
			while(c!= null){
				sortedList.add((TGGAttributeConstraint) c.getValue());
				c = c.getNext();
			}
		} finally {
			System.setOut(out);
			System.setErr(err);
		}
		sortedList = Lists.reverse(sortedList);

		return sortedList;
	}

	private Adornment determineInputAdornment(CSPSearchPlanMode mode) {
		boolean[] bits = new boolean[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			if (isBoundForMode(variables.get(i), mode)) {
				bits[i] = Adornment.B; // Bound <-> false !
			} else {
				bits[i] = Adornment.F; // Unbound <-> true !
			}
		}
		Adornment inputAdornment = new Adornment(bits);
		return inputAdornment;
	}

	private boolean isBoundForMode(TGGParamValue variable, CSPSearchPlanMode mode) {
		if (variable instanceof TGGAttributeExpression) {
			TGGAttributeExpression attrExp = (TGGAttributeExpression) variable;
			if (attrExp.getObjectVar().getBindingType() == BindingType.CONTEXT)
				return true;

			switch (mode) {
			case FWD:
				return attrExp.getObjectVar().getDomainType() == DomainType.SRC;
			case BWD:
				return attrExp.getObjectVar().getDomainType() == DomainType.TRG;
			case CC:
				return true;
			case MODELGEN:
				return false;
			default:
				break;
			}
		}

		if (variable instanceof TGGAttributeVariable)
			return false;

		if (variable instanceof TGGLiteralExpression || variable instanceof TGGEnumExpression)
			return true;

		throw new RuntimeException("Variable Typ is not known");
	}

	/**
	 * Create weighted operations from constraints
	 * 
	 * @param constraints
	 * @return
	 */
	private List<WeightedOperation<TGGAttributeConstraint>> createWeightedOperations(
			final List<TGGAttributeConstraint> constraints, CSPSearchPlanMode mode) {
		List<WeightedOperation<TGGAttributeConstraint>> result = new ArrayList<WeightedOperation<TGGAttributeConstraint>>();
		// for each constraint ...
		for (TGGAttributeConstraint constraint : constraints) {
			// and each allowed adornment ...
			for (TGGAttributeConstraintAdornment adornment : getAllowedAdornmentsForMode(constraint, mode)) {
				result.add(createWeightedOperationForConstraintWithAdornment(constraint, adornment));
			}
		}
		return result;
	}

	private List<TGGAttributeConstraintAdornment> getAllowedAdornmentsForMode(TGGAttributeConstraint constraint,
			CSPSearchPlanMode mode) {
		if (mode == CSPSearchPlanMode.MODELGEN)
			return constraint.getDefinition().getGenAdornments();
		else
			return constraint.getDefinition().getSyncAdornments();
	}

	private WeightedOperation<TGGAttributeConstraint> createWeightedOperationForConstraintWithAdornment(
			final TGGAttributeConstraint constraint, final TGGAttributeConstraintAdornment adornment) {
		long frees = adornment.getValue().stream().filter(c -> c.equals("F")).count();
		float weight = (float) Math.pow(frees, 3);

		return createOperation(constraint, createBoundMask(constraint, adornment),
				createFreeMask(constraint, adornment), weight);
	}

	// private TGGAttributeConstraintAdornment createBoundAdornment(final
	// RuntimeTGGAttributeConstraint constraint)
	// {
	// final String adornmentValue = StringUtils.repeat("B",
	// constraint.getVariables().size());
	//
	// TGGAttributeConstraintAdornment boundAdornment =
	// DefinitionFactory.eINSTANCE.createTGGAttributeConstraintAdornment();
	// for(int i = 0; i < constraint.getVariables().size(); i++){
	// boundAdornment.getValue().add("B");
	// }
	//
	// return boundAdornment;
	// }

	private Adornment createBoundMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintAdornment adornment) {
		return createMask(constraint, adornment, "B");
	}

	private Adornment createFreeMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintAdornment adornment) {
		return createMask(constraint, adornment, "F");
	}

	private Adornment createMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintAdornment adornment, String mode) {
		boolean[] bits = new boolean[variables.size()];

		for (int i = 0; i < constraint.getParameters().size(); i++) {
			TGGParamValue variable = constraint.getParameters().get(i);
			int index = variables.indexOf(variable);
			if (adornment.getValue().get(i).equals(mode)) {
				bits[index] = true;
			}
		}
		return new Adornment(bits);
	}
}
