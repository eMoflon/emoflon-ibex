package org.emoflon.ibex.tgg.operational.csp.sorting;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Adornment;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan.Algorithm;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan.WeightedOperation;

import com.google.common.collect.Lists;

import language.TGGRuleNode;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeVariable;
import language.csp.definition.TGGAttributeConstraintAdornment;

public class SearchPlanAction extends Algorithm<SimpleCombiner, TGGAttributeConstraint> {

	private List<TGGParamValue> variables;
	private List<TGGAttributeConstraint> constraints;
	private boolean useGenAdornments;
	private Collection<String> nodesCreatedByPattern;

	public SearchPlanAction(List<TGGParamValue> variables, List<TGGAttributeConstraint> constraints, boolean useGenAdornments, Collection<TGGRuleNode> nodesCreatedByPattern) {
		this.variables = variables;
		this.constraints = constraints;
		this.useGenAdornments = useGenAdornments;
		this.nodesCreatedByPattern = nodesCreatedByPattern.stream()
				.map(TGGRuleNode::getName)
				.collect(Collectors.toList());
	}
	
	// Unsorted list of our constraints => return a new List where constraints are sorted according to the search plan
	public List<TGGAttributeConstraint> sortConstraints() {
		if(constraints.isEmpty())
			return Collections.emptyList();
		
		// 1. Determine inputAdornment (initial binding information) from sorted (!) list of variables
		Adornment inputAdornment = determineInputAdornment();

		// 2. Create weighted operations, one for each allowed adornment of each constraint
		List<WeightedOperation<TGGAttributeConstraint>> weightedOperations = createWeightedOperations(constraints);

		// 3. Call search plan algorithm to sort weighted operations

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
			SimpleCombiner sc = generatePlan(new SimpleCombiner(), weightedOperations, inputAdornment);
			List<TGGAttributeConstraint> sortedList = new ArrayList<>();
			Chain<?> c = sc.getRoot();
			while(c!= null){
				sortedList.add((TGGAttributeConstraint) c.getValue());
				c = c.getNext();
			}
			return Lists.reverse(sortedList);
		} catch(Exception e) {
			throw new IllegalStateException(constraints.stream()
					.map(c -> c.getDefinition().getName())
					.collect(Collectors.toList()) + ", " + e.getMessage());
		} finally {
			System.setOut(out);
			System.setErr(err);
		}
	}

	private Adornment determineInputAdornment() {
		boolean[] bits = new boolean[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			if (isBoundForMode(variables.get(i))) {
				bits[i] = Adornment.B; // Bound <-> false !
			} else {
				bits[i] = Adornment.F; // Unbound <-> true !
			}
		}
		Adornment inputAdornment = new Adornment(bits);
		return inputAdornment;
	}

	private boolean isBoundForMode(TGGParamValue variable) {
		if (variable instanceof TGGAttributeExpression) {
			TGGAttributeExpression attrExp = (TGGAttributeExpression) variable;
			String nameOfObj = attrExp.getObjectVar().getName();
			return !nodesCreatedByPattern.contains(nameOfObj);
		}

		if (variable instanceof TGGAttributeVariable)
			return false;

		if (variable instanceof TGGLiteralExpression || variable instanceof TGGEnumExpression)
			return true;

		throw new IllegalStateException("Unable to handle " + variable);
	}

	/**
	 * Create weighted operations from constraints
	 * 
	 * @param constraints
	 * @return
	 */
	private List<WeightedOperation<TGGAttributeConstraint>> createWeightedOperations(final List<TGGAttributeConstraint> constraints) {
		List<WeightedOperation<TGGAttributeConstraint>> result = new ArrayList<WeightedOperation<TGGAttributeConstraint>>();
		// for each constraint ...
		for (TGGAttributeConstraint constraint : constraints) {
			// and each allowed adornment ...
			for (TGGAttributeConstraintAdornment adornment : getAllowedAdornmentsForMode(constraint)) {
				result.add(createWeightedOperationForConstraintWithAdornment(constraint, adornment));
			}
		}
		return result;
	}

	private List<TGGAttributeConstraintAdornment> getAllowedAdornmentsForMode(TGGAttributeConstraint constraint) {
		if (useGenAdornments)
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
