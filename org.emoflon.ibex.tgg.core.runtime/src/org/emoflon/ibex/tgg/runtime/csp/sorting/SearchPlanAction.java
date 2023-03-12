package org.emoflon.ibex.tgg.runtime.csp.sorting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral;
import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.common.Adornment;
import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.plan.Algorithm;
import org.emoflon.ibex.tgg.runtime.csp.sorting.solver.democles.plan.WeightedOperation;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraint;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintBinding;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGAttributeConstraintParameterValue;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.CSP.TGGLocalVariable;

import com.google.common.collect.Lists;

public class SearchPlanAction extends Algorithm<SimpleCombiner, TGGAttributeConstraint> {

	private final List<TGGAttributeConstraintParameterValue> variables = new ArrayList<>();
	private final List<TGGAttributeConstraint> constraints = new ArrayList<>();
	private final boolean useGenAdornments;
	private final Collection<String> nodesCreatedByPattern = new ArrayList<>();

	/**
	 * @param variables
	 * @param constraints
	 * @param useGenAdornments
	 * @param availableNodes
	 *            Nodes from which bound values for parameters are taken.
	 */
	public SearchPlanAction(List<TGGAttributeConstraintParameterValue> variables, List<TGGAttributeConstraint> constraints,
			boolean useGenAdornments, Collection<IBeXNode> availableNodes) {
		this.variables.addAll(variables);
		this.constraints.addAll(constraints);
		this.useGenAdornments = useGenAdornments;
		this.nodesCreatedByPattern.addAll(availableNodes.stream().map(IBeXNode::getName).collect(Collectors.toList()));
	}

	// Unsorted list of our constraints => return a new List where constraints are
	// sorted according to the search plan
	public List<TGGAttributeConstraint> sortConstraints() {
		if (constraints.isEmpty())
			return Collections.emptyList();

		// 1. Determine inputAdornment (initial binding information) from sorted (!)
		// list of variables
		Adornment inputAdornment = determineInputAdornment();

		// 2. Create weighted operations, one for each allowed adornment of each
		// constraint
		List<WeightedOperation<TGGAttributeConstraint>> weightedOperations = createWeightedOperations(constraints);

		// 3. Call search plan algorithm to sort weighted operations
		try {
			SimpleCombiner sc = generatePlan(new SimpleCombiner(), weightedOperations, inputAdornment);
			List<TGGAttributeConstraint> sortedList = new ArrayList<>();
			Chain<?> c = sc.getRoot();
			while (c != null) {
				sortedList.add((TGGAttributeConstraint) c.getValue());
				c = c.getNext();
			}
			return Lists.reverse(sortedList);
		} catch (Exception e) {
			throw new IllegalStateException(
					constraints.stream().map(c -> c.getDefinition().getName()).collect(Collectors.toList()) + ", "
							+ e.getMessage());
		} 
	}

	private Adornment determineInputAdornment() {
		boolean[] bits = new boolean[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			if (isBoundInPattern(variables.get(i), n -> !nodesCreatedByPattern.contains(n))) {
				bits[i] = Adornment.B; // Bound <-> false !
			} else {
				bits[i] = Adornment.F; // Unbound <-> true !
			}
		}
		Adornment inputAdornment = new Adornment(bits);
		return inputAdornment;
	}

	/**
	 * A variable is bound in a pattern if its value will be fixed when solving
	 * attribute conditions. This is the case for attribute expressions referencing
	 * black nodes (nodes in the pattern), never the case for local variables
	 * (attribute variables), and always the case for constants (literals and
	 * enums).
	 * 
	 * @param variable
	 * @param patternContainsNode
	 * @return
	 */
	public static boolean isBoundInPattern(TGGAttributeConstraintParameterValue variable, Predicate<String> patternContainsNode) {
		if (variable instanceof IBeXAttributeValue attrExpr) {
			String nameOfObj = attrExpr.getNode().getName();
			return patternContainsNode.test(nameOfObj);
		}

		if (variable instanceof TGGLocalVariable)
			return false;

		if (variable instanceof IBeXStringValue)
			return true;
		if (variable instanceof IBeXBooleanValue)
			return true;
		if (variable instanceof DoubleLiteral)
			return true;
		if (variable instanceof IntegerLiteral)
			return true;
		
		if (variable instanceof IBeXEnumValue)
			return true;
			
		throw new IllegalStateException("Unable to handle " + variable);
	}

	/**
	 * Currently, our definition of connectivity coincides with boundness. This
	 * could be relaxed in the future.
	 * 
	 * @param variable
	 * @param patternContainsNode
	 * @return
	 */
	public static boolean isConnectedToPattern(TGGAttributeConstraintParameterValue variable, Predicate<String> patternContainsNode) {
		return isBoundInPattern(variable, patternContainsNode);
	}
	
	/**
	 * Create weighted operations from constraints
	 * 
	 * @param constraints
	 * @return
	 */
	private List<WeightedOperation<TGGAttributeConstraint>> createWeightedOperations(
			final List<TGGAttributeConstraint> constraints) {
		List<WeightedOperation<TGGAttributeConstraint>> result = new ArrayList<WeightedOperation<TGGAttributeConstraint>>();
		// for each constraint ...
		for (TGGAttributeConstraint constraint : constraints) {
			// and each allowed adornment ...
			for (TGGAttributeConstraintBinding adornment : getAllowedAdornmentsForMode(constraint)) {
				result.add(createWeightedOperationForConstraintWithAdornment(constraint, adornment));
			}
		}
		return result;
	}

	private List<TGGAttributeConstraintBinding> getAllowedAdornmentsForMode(TGGAttributeConstraint constraint) {
		if (useGenAdornments)
			return constraint.getDefinition().getGenBindings();
		else
			return constraint.getDefinition().getSyncBindings();
	}

	private WeightedOperation<TGGAttributeConstraint> createWeightedOperationForConstraintWithAdornment(
			final TGGAttributeConstraint constraint, final TGGAttributeConstraintBinding adornment) {
		long frees = adornment.getValue().stream().filter(c -> c.equals("F")).count();
		float weight = (float) Math.pow(frees, 3);

		return createOperation(constraint, createBoundMask(constraint, adornment),
				createFreeMask(constraint, adornment), weight);
	}

	private Adornment createBoundMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintBinding adornment) {
		return createMask(constraint, adornment, "B");
	}

	private Adornment createFreeMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintBinding adornment) {
		return createMask(constraint, adornment, "F");
	}

	private Adornment createMask(final TGGAttributeConstraint constraint,
			final TGGAttributeConstraintBinding adornment, String mode) {
		boolean[] bits = new boolean[variables.size()];

		for (int i = 0; i < constraint.getParameters().size(); i++) {
			TGGAttributeConstraintParameterValue variable = constraint.getParameters().get(i);
			int index = variables.indexOf(variable);
			if (adornment.getValue().get(i).equals(mode)) {
				bits[index] = true;
			}
		}
		return new Adornment(bits);
	}
}
