package org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan.common;

import java.util.ArrayList;
import java.util.List;

import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Adornment;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Combiner;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.OperationRuntime;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.SearchPlanAlgorithm;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan.Algorithm;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan.WeightedOperation;

public class DefaultAlgorithm<C extends Combiner<C, O>, O extends OperationRuntime> extends Algorithm<C, O> implements SearchPlanAlgorithm<C, O>{
	private WeightedOperationBuilder<O> builder;
	
	public DefaultAlgorithm(WeightedOperationBuilder<O> builder) {
		this.builder = builder;
	}

	public C generateSearchPlan(C combiner, List<O> operations, Adornment inputAdornment) {
		// Build weighted operations
		ArrayList<WeightedOperation<O>> weightedOperations =
			new ArrayList<WeightedOperation<O>>(operations.size());
		for (O operation : operations) {
			// Prepare past and present masks
			Adornment freeMask = operation.getFreeMask(inputAdornment);
			Adornment boundMask = operation.getBoundMask(inputAdornment);
			float weight = builder.getWeight(operation);
			WeightedOperation<O> weightedOperation = createOperation(operation, boundMask, freeMask, weight); 
			if (weightedOperation != null) {
				weightedOperations.add(weightedOperation);
			}
		}
		
		return generatePlan(combiner, weightedOperations, inputAdornment);
		
	}
	
}
