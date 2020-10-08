package org.emoflon.ibex.gt.arithmetic;

import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * Interface which all probability classes must implement to be applicable for the graph transformation
 */
public abstract class Probability<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>>{
	
	protected GraphTransformationInterpreter interpreter;
	
	protected Probability(final GraphTransformationInterpreter interpreter) {
		this.interpreter = interpreter;
	}
	
	/**
	 * calculates the probability that the rule will be applied
	 * 
	 * @param match the match
	 * @return the probability that the rule is applied to the match
	 */
	public abstract double getProbability(M match);
	
	public abstract double getProbabilityGeneric(GraphTransformationMatch<?, ?> match);
	
	/**
	 * calculates the probability that the rule will be applied; this method should only be used by
	 * static methods, with runtime-depended probabilities it will return 0
	 * 
	 * @return the probability of the static probability; else 0
	 */
	public abstract double getProbability();
}
