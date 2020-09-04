package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorProbability;
import org.emoflon.ibex.gt.editor.gT.StochasticDistribution;
import org.emoflon.ibex.gt.editor.gT.StochasticFunction;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDistributionType;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbability;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXProbabilityDistribution;

/**
 * Utility class for stochastic extension transformation
 */
public class EditorToStochasticExtensionHelper {
	
	/**
	 * transforms the editor probability in a gt probability
	 * 
	 * @param probability the EditorProbability
	 * @return the GTProbability
	 */
	public static IBeXProbability transformToGTProbability(final EditorPattern pattern) {
		IBeXProbability gtProbability = IBeXPatternModelFactory.eINSTANCE.createIBeXProbability();
		if(pattern.isStochastic()) {
			EditorProbability probability = pattern.getProbability();
			// if probability is a function
			if(probability instanceof StochasticFunction) {
				gtProbability.setDistribution(transformStochasticFunction(((StochasticFunction) probability)
						.getFunctionExpression()));
				//if probability is depended on a parameter
				if(((StochasticFunction) probability).getParameter() != null) {
					gtProbability.setParameter(EditorToArithmeticExtensionHelper
							.transformToGTArithmetics(((StochasticFunction) probability).getParameter()));
				}
			}
			//if the probability is an arithmetic expression
			else {				
				gtProbability.setDistribution(createStaticProbability(EditorToArithmeticExtensionHelper
						.transformToGTArithmetics(((ArithmeticExpression) probability))));
			}					
		}
		//if the rule has no probability it will not have a GTProbability
		else return null;
		return gtProbability;
	}

	/**
	 * Transforms the StochasticFunctionExpression in a StochasticFunction
	 * 
	 * @param function
	 * 			the stochasticFunctionExpression
	 * @return the transformed function
	 */
	public static IBeXProbabilityDistribution transformStochasticFunction(final StochasticFunctionExpression function){
		IBeXProbabilityDistribution stochasticFunction = IBeXPatternModelFactory.eINSTANCE.createIBeXProbabilityDistribution();
		stochasticFunction.setType(transformDistribution(function.getDistribution()));
		stochasticFunction.setMean(EditorToArithmeticExtensionHelper
				.transformToGTArithmetics(function.getMean()));
		if(stochasticFunction.getType() != IBeXDistributionType.EXPONENTIAL) {
			stochasticFunction.setStddev(EditorToArithmeticExtensionHelper
					.transformToGTArithmetics(function.getSd()));	
		}else {
			IBeXArithmeticValueLiteral sd = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			sd.setValue(0.0);
			stochasticFunction.setStddev(sd);
		}
		return stochasticFunction;
	}

	/**
	 * creates a static probability for the rule
	 * @param parameter the static probability
	 * @param probability the probability of the rule
	 */
	private static IBeXProbabilityDistribution createStaticProbability(final IBeXArithmeticExpression expression) {
		IBeXProbabilityDistribution function = IBeXPatternModelFactory.eINSTANCE.createIBeXProbabilityDistribution();
		function.setMean(expression);
		IBeXArithmeticValueLiteral sd = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
		sd.setValue(0.0);
		function.setStddev(sd);
		function.setType(IBeXDistributionType.STATIC);
		return function;
	}
	
	private static IBeXDistributionType transformDistribution(StochasticDistribution distribution) {
		switch(distribution) {
		case EXPONENTIAL: return IBeXDistributionType.EXPONENTIAL;
		case NORMAL: return IBeXDistributionType.NORMAL;
		case UNIFORM: return IBeXDistributionType.UNIFORM;
		}
		return IBeXDistributionType.STATIC;
	}
}
