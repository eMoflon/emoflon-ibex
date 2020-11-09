package org.emoflon.ibex.gt.arithmetic;

import java.util.OptionalDouble;
import java.util.Random;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.emoflon.ibex.gt.api.GraphTransformationMatch;
import org.emoflon.ibex.gt.api.GraphTransformationPattern;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType;

/**
 * class for all probabilities that do not depend on parameters
 */
public class StaticProbability<M extends GraphTransformationMatch<M, P>, P extends GraphTransformationPattern<M, P>> extends Probability<M, P> {
	
	double mean, sd;
	IBeXDistributionType distribution;
	Random rnd;
	OptionalDouble parameter;
	
	public StaticProbability(final GraphTransformationInterpreter interpreter, double mean, double sd, IBeXDistributionType distribution, OptionalDouble parameter) {
		super(interpreter);
		this.mean = mean;
		this.sd = sd;
		this.distribution = distribution;
		this.parameter = parameter;
	}
	
	@Override
	public double getProbability(M match) {
		return getProbability();
	}
	
	@Override
	public double getProbabilityGeneric(GraphTransformationMatch<?, ?> match) {
		return getProbability();
	}
	
	/**
	 * calculates the probability based on the normal distribution
	 * 
	 * @return the probability based on the normal distribution
	 */
	private double getNormalDistribution() {
		if(parameter.isPresent()) {
			NormalDistribution normal = new NormalDistribution(mean, sd);
			return normal.cumulativeProbability(parameter.getAsDouble());			
		}
		//else gets a random number on the normal distribution
		double probability;
		do {
			probability = mean + rnd.nextGaussian()*sd;
		}while(probability > 1.0 || probability < 0.0);
		return probability;		
	}
	
	/**
	 * calculates the probability based on the uniform distribution
	 * 
	 * @return the probability based on the uniform distribution
	 */
	private double getUniformDistribution() {
		if(parameter.isPresent()) {
			UniformRealDistribution normal = new UniformRealDistribution(mean, sd);
			return normal.cumulativeProbability(parameter.getAsDouble());			
		}
		//else returns a random number on the distribution
		//mean = minValue; sd = maxValue
		return mean + rnd.nextDouble()*(sd - mean);
	}
	
	private double getExponentialDistribution() {
		if(parameter.isPresent()) {
			ExponentialDistribution exponential = new ExponentialDistribution(mean);
			return exponential.cumulativeProbability(parameter.getAsDouble());
		}
		double probability;
		do {
			probability = Math.log(1 - rnd.nextDouble())/(-mean);
		}while(probability > 1.0 || probability < 0.0);
		return probability;	
	}
	
	@Override
	public double getProbability() {
		rnd = new Random();
		switch(distribution) {
			case STATIC: 		return mean; 
			case NORMAL: 		return getNormalDistribution();
			case UNIFORM: 		return getUniformDistribution();
			case EXPONENTIAL: 	return getExponentialDistribution();
			default: 			return 1.0;
		}
	}

}
