package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorProbability;
import org.emoflon.ibex.gt.editor.gT.StochasticDistribution;
import org.emoflon.ibex.gt.editor.gT.StochasticFunction;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTProbability;
import StochasticLanguage.GTArithmetics;
import StochasticLanguage.GTNumber;
import StochasticLanguage.GTStochasticDistribution;
import StochasticLanguage.GTStochasticFunction;
import StochasticLanguage.StochasticLanguageFactory;

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
	public static GTProbability transformToGTProbability(final EditorPattern pattern) {
		GTProbability gtProbability = GTLanguageFactory.eINSTANCE.createGTProbability();
		if(pattern.isStochastic()) {
			EditorProbability probability = pattern.getProbability();
			// if probability is a function
			if(probability instanceof StochasticFunction) {
				gtProbability.setFunction(transformStochasticFunction(((StochasticFunction) probability)
						.getFunctionExpression()));
				//if probability is depended on a parameter
				if(((StochasticFunction) probability).getParameter() != null) {
					gtProbability.setParameter(EditorToArithmeticExtensionHelper
							.transformToGTArithmetics(((StochasticFunction) probability).getParameter()));
				}
			}
			//if the probability is an arithmetic expression
			else {				
				gtProbability.setFunction(createStaticProbability(EditorToArithmeticExtensionHelper
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
	public static GTStochasticFunction transformStochasticFunction(final StochasticFunctionExpression function){
		GTStochasticFunction stochasticFunction = StochasticLanguageFactory.eINSTANCE.createGTStochasticFunction();
		stochasticFunction.setDistribution(transformDistribution(function.getDistribution()));
		stochasticFunction.setMean(EditorToArithmeticExtensionHelper
				.transformToGTArithmetics(function.getMean()));
		if(stochasticFunction.getDistribution() != GTStochasticDistribution.EXPONENTIAL) {
			stochasticFunction.setSd(EditorToArithmeticExtensionHelper
					.transformToGTArithmetics(function.getSd()));	
		}else {
			GTNumber sd = StochasticLanguageFactory.eINSTANCE.createGTNumber();
			sd.setNumber(0.0);
			stochasticFunction.setSd(sd);
		}
		return stochasticFunction;
	}

	/**
	 * creates a static probability for the rule
	 * @param parameter the static probability
	 * @param probability the probability of the rule
	 */
	private static GTStochasticFunction createStaticProbability(final GTArithmetics expression) {
		GTStochasticFunction function = StochasticLanguageFactory.eINSTANCE.createGTStochasticFunction();
		function.setMean(expression);
		GTNumber sd = StochasticLanguageFactory.eINSTANCE.createGTNumber();
		sd.setNumber(0.0);
		function.setSd(sd);
		function.setDistribution(GTStochasticDistribution.STATIC);
		return function;
	}
	
	private static GTStochasticDistribution transformDistribution(StochasticDistribution distribution) {
		switch(distribution) {
		case EXPONENTIAL: return GTStochasticDistribution.EXPONENTIAL;
		case NORMAL: return GTStochasticDistribution.NORMAL;
		case UNIFORM: return GTStochasticDistribution.UNIFORM;
		}
		return GTStochasticDistribution.STATIC;
	}
}
