package org.emoflon.ibex.gt.arithmetics;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;

/**
 * Interface which all probability classes must implement to be applicable for the graph transformation
 */
public interface Probability<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>{
	
	/**
	 * calculates the probability that the rule will be applied
	 * 
	 * @param match the match
	 * @return the probability that the rule is applied to the match
	 */
	public double getProbability(M match);
	
	/**
	 * calculates the probability that the rule will be applied; this method should only be used by
	 * static methods, with runtime-depended probabilities it will return 0
	 * 
	 * @return the probability of the static probability; else 0
	 */
	public double getProbability();
}
