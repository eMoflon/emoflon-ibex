/*
 * Democles, Declarative Model Query Framework for Monitoring Heterogeneous Embedded Systems
 * Copyright (C) 2010  Gergely Varro
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 * 		Gergely Varro <gervarro@cs.bme.hu> - initial API and implementation and/or initial documentation
 */
package org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.plan;

import java.util.Collections;
import java.util.List;

import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Adornment;
import org.emoflon.ibex.tgg.operational.csp.sorting.solver.democles.common.Combiner;

public class Algorithm<C extends Combiner<C, O>, O> {
	
	public C generatePlan(C combiner, List<WeightedOperation<O>> future, Adornment inputAdornment) {
		List<WeightedOperation<O>> present = Collections.emptyList();
		Collections.sort(future);
		ReachabilityAnalyzer reachabilityAnalyzer =
			new BDDReachabilityAnalyzer<C, O>(future, inputAdornment);
		
		int numberOfFreeVariables = inputAdornment.cardinality();
		int precisionParameter = 1;
		SearchPlan<C,O>[][] optimalSearchPlans =
			createArray(numberOfFreeVariables + 1, precisionParameter);
		// Prepare the initial state
		SearchPlan<C,O> initialPlan = 
			new SearchPlan<C,O>(inputAdornment, 0, 1,combiner, present, future);
		initialPlan.refreshOperationLists();
		optimalSearchPlans[numberOfFreeVariables][0] = initialPlan;
		for (int i = numberOfFreeVariables; i >= 0; i--) {
			for (int j = 0; j < precisionParameter; j++) {
				SearchPlan<C, O> currentPlan = optimalSearchPlans[i][j];
				if (currentPlan != null) {
					for (WeightedOperation<O> operation : currentPlan.getPresentOperations()) {
						SearchPlan<C,O> nextPlan = new SearchPlan<C,O>(
								currentPlan.getAdornment().and(operation.freeMask.not()),
								currentPlan.getCost()+ currentPlan.getProductCost()* operation.weight,
								currentPlan.getProductCost()*operation.weight,
								currentPlan.getRoot().combine(operation.operation),
								currentPlan.getPresentOperations(),
								currentPlan.getFutureOperations());
						int numberOfFreeVariablesInNextPlan = nextPlan.getAdornment().cardinality();
						SearchPlan<C, O> storedWorstPlan = optimalSearchPlans[numberOfFreeVariablesInNextPlan][precisionParameter - 1];
						// If nextPlan is better than the storedWorstPlan, then nextPlan should be stored in the array
						if (storedWorstPlan == null || nextPlan.getCost() < storedWorstPlan.getCost()) {
							// Determine helper indices for the insertion  
							int sameAdornmentIndex = precisionParameter;
							int insertionIndex = precisionParameter;
							for (int k = precisionParameter - 1; k >= 0; k--) {
								SearchPlan<C, O> planInArray = optimalSearchPlans[numberOfFreeVariablesInNextPlan][k];
								if (planInArray != null) {
									if (nextPlan.getCost() < planInArray.getCost()) {
										insertionIndex = k;
									}
									if (nextPlan.getAdornment().equals(planInArray.getAdornment())) {
										sameAdornmentIndex = k;
									}
								} else {
									insertionIndex = k;
								}
							}
							// Insert nextPlan into the appropriate position
							if (sameAdornmentIndex == precisionParameter) {
								// No search plans are stored in the array with the adornment of nextPlan
								if (reachabilityAnalyzer.isReachable(nextPlan.getAdornment())) {
									nextPlan.refreshOperationLists();
									insertShiftRemove(optimalSearchPlans[numberOfFreeVariablesInNextPlan], nextPlan, insertionIndex, precisionParameter - 1);
								}
							} else if (insertionIndex <= sameAdornmentIndex) {
								// nextPlan is better than the search plan with the same adornment
								nextPlan.refreshOperationLists();
								insertShiftRemove(optimalSearchPlans[numberOfFreeVariablesInNextPlan], nextPlan, insertionIndex, sameAdornmentIndex);
							}
						}
					}
				}
			}
		}
		SearchPlan<C, O> bestSearchPlanCandidate = optimalSearchPlans[0][0];
		if (bestSearchPlanCandidate != null) {
			return bestSearchPlanCandidate.getRoot();
		} else {
			throw new RuntimeException("No valid search plan is available");
		}
	}

	@SuppressWarnings("unchecked")
	private final SearchPlan<C,O>[][] createArray(int height, int width) {
		SearchPlan<C, O>[][] result = new SearchPlan[height][];
		for (int i = 0; i < result.length; i++) {
			result[i] = new SearchPlan[width];
		}
		return result;
	}
	
	private final void insertShiftRemove(SearchPlan<C, O>[] column, SearchPlan<C, O> searchPlan, int min, int max) {
		assert min <= max && max < column.length;
		for (int i = max - 1; i >= min; i--) {
			column[i+1] = column[i];
		}
		column[min] = searchPlan;
	}
	
	public final WeightedOperation<O> createOperation(O operation, Adornment boundMask, Adornment freeMask, float weight) {
		WeightedOperation<O> weightedOperation = new WeightedOperation<O>();
		weightedOperation.operation = operation;
		// Past mask: 1 if variable is free, 0 otherwise
		weightedOperation.freeMask = freeMask;
		// Present mask: 1 if variable is bound, 0 otherwise
		weightedOperation.boundMask = boundMask;
		weightedOperation.weight = weight;
		return weightedOperation;
	}
}
