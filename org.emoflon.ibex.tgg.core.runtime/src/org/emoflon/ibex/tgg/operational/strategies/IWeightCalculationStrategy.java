package org.emoflon.ibex.tgg.operational.strategies;

import org.emoflon.ibex.common.engine.IMatch;

/**
 * Interface for WeightCalculation strategies. A strategy can be used to assign
 * a value to a found match. <br>
 * <br>
 * Strategies can be defined using the TGG Weight Language (.tggw -files) which
 * generates implementations of this interface.
 */
public interface IWeightCalculationStrategy {
	/**
	 * Calculate the weight for the given match
	 * 
	 * @param ruleName Name of the rule
	 * @param comatch  The match to weight
	 * @return the calculated weight
	 */
	double calculateWeight(String ruleName, IMatch comatch);
}