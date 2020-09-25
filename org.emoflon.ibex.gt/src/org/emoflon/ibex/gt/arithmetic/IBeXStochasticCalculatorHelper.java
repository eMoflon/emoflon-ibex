package org.emoflon.ibex.gt.arithmetic;

import java.util.Random;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionRange;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue;



/**
 * Utility class for stochastic methods for the pattern matcher value assignment
 *
 */
public class IBeXStochasticCalculatorHelper {
	
	private static Random rnd = new Random();
	
	/**
	 * gets the new randomly generated value for the parameter
	 * 
	 * @param type the EDataType of the parameter
	 * @param value the value
	 * @param match the match
	 * @return the new randomly generated value for the parameter
	 */
	public static Object getGeneratedValue( final GraphTransformationInterpreter contextInterpreter, final IBeXStochasticAttributeValue value,
			final IMatch match, EDataType type) {
		checkRuntimeStochasticConstraints(contextInterpreter, value, match);
		return getTypedValue(contextInterpreter, value, match, type);
		
	}
	

	/**
	 * gets the random generated number cast in the appropriate data type
	 * 
	 * @param type the EDataType of the parameter for which the value will be generated for
	 * @param value the value
	 * @param match the match
	 * @return
	 */
	private static Object getTypedValue(final GraphTransformationInterpreter interpreter, final IBeXStochasticAttributeValue value,
			final IMatch match, EDataType type) {
		Object object = null;
		double doubleValue;
		long intValue;
		IBeXProbabilityDistribution function = value.getFunction();
		if(type.equals(EcorePackage.Literals.EDOUBLE) || type.equals(EcorePackage.Literals.EFLOAT)) {
			doubleValue = getDoubleStochasticValue(getValue(interpreter, function.getMean(), match),
					getValue(interpreter, function.getStddev(), match),function.getType(), value.getRange());
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Double.toString(doubleValue));
		}
		else{
			intValue = getIntegerStochasticValue((int) Math.ceil(getValue(interpreter, function.getMean(), match)),
					(int) getValue(interpreter, function.getStddev(), match), function.getType(), value.getRange());
			object = type.getEPackage().getEFactoryInstance().createFromString(type, 
					Long.toString(intValue));
		}

		return object;
	}

	/**
	 * gets the calculated value depended of the distribution
	 * 
	 * @param mean the mean
	 * @param sd the sd
	 * @param distribution the distribution of the distribution
	 * @param range the range
	 * @return the calculated value
	 */
	private static double getDoubleStochasticValue(double mean, double sd, IBeXDistributionType distribution,
			IBeXDistributionRange range) {
		double value = 1.0;
		switch(distribution) {
		case NORMAL : 		value = rnd.nextGaussian()*sd + mean;
					  		break;
		case UNIFORM: 		value = mean + rnd.nextDouble()*(sd-mean);
					  		break;
		case EXPONENTIAL: 	value = Math.log(1 - rnd.nextDouble())/(-mean);
							break;
		default:	  		value = mean;
					  		break;
		}
		switch(range) {
		case NEGATIVE_ONLY: return -Math.abs(value);
		case POSITIVE_ONLY: return Math.abs(value);
		default: return value;
		}
	}
	
	/**
	 * gets the calculated value depended of the distribution; creates integer values
	 * 
	 * @param mean the mean
	 * @param sd the sd
	 * @param distribution the distribution of the distribution
	 * @param range the range
	 * @return the calculated value
	 */
	private static long getIntegerStochasticValue(int mean, int sd, 
			IBeXDistributionType distribution, IBeXDistributionRange range) {
		long value = 1;
		switch(distribution) {
		case NORMAL : 		value = (int) (rnd.nextGaussian()*sd + mean);
					  		break;
		case UNIFORM: 		value = mean + rnd.nextInt(sd-mean+1);
					  		break;
		case EXPONENTIAL: 	value = (int) Math.log(1 - rnd.nextDouble())/(-mean);
							break;
		default:	  		value = mean;
					  		break;
		}
		switch(range) {
		case NEGATIVE_ONLY: return -Math.abs(value);
		case POSITIVE_ONLY: return Math.abs(value);
		default: return value;
		}
	}
	/**
	 * checks if the parameters of the value are not breaking stochastic definitions
	 * 
	 * @param value the value
	 * @param match the match
	 */
	private static void checkRuntimeStochasticConstraints(final GraphTransformationInterpreter interpreter, final IBeXStochasticAttributeValue value,
			final IMatch match){
		IBeXProbabilityDistribution function = value.getFunction();
		//checks that the standard deviation of the normaldistribution is positive
		double mean = getValue(interpreter, function.getMean(), match);
		if(function.getType() == IBeXDistributionType.EXPONENTIAL && mean <= 0.0) {
			throw new IllegalArgumentException("the mean of the exponential distribution needs to be positive:"
					+ "the mean of a value generator in " 
					+ match.getPatternName() + " is negative");	
		}
		double sd =getValue(interpreter, function.getStddev(), match);
		if(function.getType() == IBeXDistributionType.NORMAL && sd <= 0.0) {
			throw new IllegalArgumentException("standard deviation of a normal distribution needs"
					+ "to be positive: The standard deviation of a value generator in " 
					+ match.getPatternName() + " is negative");		
		}
		//checks that the minValue is smaller than the maxValue
		if(function.getType() == IBeXDistributionType.UNIFORM && sd - mean < 0) {
			throw new IllegalArgumentException("the minimum value needs to be smaller than the "
					+ "maximum value: The minimum value of a value generator in " 
					+ match.getPatternName() + " is bigger than the maximum value");	
		}
	}
	
	/**
	 * gets the value of the parameter
	 * 
	 * @param attribute the attribute
	 * @param match the match
	 * @return the numeric value of the parameter
	 */
	private static double getValue(final GraphTransformationInterpreter interpreter, final IBeXArithmeticExpression expression, final IMatch match) {
		return RuntimeArithmeticExtensionCalculator.calculateValue(interpreter, expression, match);
	}
}
