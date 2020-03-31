package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.AddExpression;
import org.emoflon.ibex.gt.editor.gT.AddOperator;
import org.emoflon.ibex.gt.editor.gT.ArithmeticAttribute;
import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticNodeAttribute;
import org.emoflon.ibex.gt.editor.gT.ExpExpression;
import org.emoflon.ibex.gt.editor.gT.MultExpression;
import org.emoflon.ibex.gt.editor.gT.OneParameterArithmetics;
import org.emoflon.ibex.gt.editor.utils.GTArithmeticsCalculatorUtil;

import StochasticLanguage.GTArithmetics;
import StochasticLanguage.GTAttribute;
import StochasticLanguage.GTNumber;
import StochasticLanguage.GTOneParameterCalculation;
import StochasticLanguage.GTTwoParameterCalculation;
import StochasticLanguage.OneParameterOperator;
import StochasticLanguage.StochasticLanguageFactory;
import StochasticLanguage.TwoParameterOperator;

public class EditorToArithmeticExtensionHelper {
	
	/**
	 * transforms an ArithmeticExpression in a GTArithmetics; will try to partially 
	 * parse the expression tree
	 * 
	 * @param expression the ArithmeticExpression
	 * @return the transformed GTArithmetics
	 */
	public static GTArithmetics transformToGTArithmetics(final ArithmeticExpression expression) {
		//if the expression has two parameters and one operator
		if(expression instanceof AddExpression || expression instanceof MultExpression || 
				expression instanceof ExpExpression) {
			GTTwoParameterCalculation calculation = StochasticLanguageFactory.eINSTANCE.createGTTwoParameterCalculation();
			if(expression instanceof AddExpression) {
				/* tries to parse the expression tree; if it is runtime-depended
				 * (with node attributes) it will create a new GTArithmetics node
				 */
				
				try {
					return tryToParseExpression(expression);
				} catch(IllegalArgumentException e) {
					calculation.setLeft(transformToGTArithmetics(((AddExpression) expression).getLeft()));
					calculation.setRight(transformToGTArithmetics(((AddExpression) expression).getRight()));								
					if(((AddExpression) expression).getAddOperator() == AddOperator.ADDITION) {
						calculation.setOperator(TwoParameterOperator.ADDITION);
					}else {
						calculation.setOperator(TwoParameterOperator.SUBTRACTION);
					}
				}
			}
			if(expression instanceof MultExpression) {
				try {
					return tryToParseExpression(expression);
				} catch(IllegalArgumentException e) {
					calculation.setLeft(transformToGTArithmetics(((MultExpression) expression).getLeft()));
					calculation.setRight(transformToGTArithmetics(((MultExpression) expression).getRight()));								
					switch(((MultExpression) expression).getMultOperator()) {
						case DIVISION:  	calculation.setOperator(TwoParameterOperator.DIVISION);
											break;
						case MODULO:  		calculation.setOperator(TwoParameterOperator.MODULO);
											break;
						case MULTIPLICATION:calculation.setOperator(TwoParameterOperator.MULTIPLICATION);
											break;
					}	
				}
				
			}
			if(expression instanceof ExpExpression) {
				try {
					return tryToParseExpression(expression);
				} catch(IllegalArgumentException e) {
					calculation.setLeft(transformToGTArithmetics(((ExpExpression) expression).getLeft()));
					calculation.setRight(transformToGTArithmetics(((ExpExpression) expression).getRight()));								
					calculation.setOperator(TwoParameterOperator.EXPONENTIAL);	
				}	
			}
			return calculation;
		}
		//if the expression has only one parameter and operand
		else if(expression instanceof OneParameterArithmetics) {
			try {
				return tryToParseExpression(expression);
			} catch(IllegalArgumentException e) {
				GTOneParameterCalculation calculation = StochasticLanguageFactory.eINSTANCE.createGTOneParameterCalculation();
				calculation.setValue(transformToGTArithmetics(((OneParameterArithmetics) expression).getExpression()));
				switch(((OneParameterArithmetics) expression).getOperator()) {
					case ABSOLUTE: 	calculation.setOperator(OneParameterOperator.ABSOLUTE);
									break;
					case BRACKET: 	calculation.setOperator(OneParameterOperator.BRACKET);
									break;
					case COS: 		calculation.setOperator(OneParameterOperator.COS);
									break;
					case SIN: 		calculation.setOperator(OneParameterOperator.SIN);
									break;
					case TAN: 		calculation.setOperator(OneParameterOperator.TAN);
									break;
					case EEXPONENTIAL:calculation.setOperator(OneParameterOperator.EEXPONENTIAL);
									break;
					case LOGARITHMUS:calculation.setOperator(OneParameterOperator.LOGARITHMUS);
									break;
					case NAT_LOG: 	calculation.setOperator(OneParameterOperator.NATLOG);
									break;
					case ROOT: 		calculation.setOperator(OneParameterOperator.ROOT);
									break;
				}
				if(((OneParameterArithmetics) expression).isNegative()) calculation.setNegative(true);
				else calculation.setNegative(false);
				return calculation;	
			}
			
		}
		//if the parameter is an attribute from a node
		else if(expression instanceof ArithmeticNodeAttribute) {
			GTAttribute calculation = StochasticLanguageFactory.eINSTANCE.createGTAttribute();
			calculation.setType(((ArithmeticNodeAttribute) expression).getNode().getType());
			calculation.setName(((ArithmeticNodeAttribute) expression).getNode().getName());
			calculation.setAttribute(((ArithmeticNodeAttribute) expression).getAttribute());
			calculation.setNegative(((ArithmeticNodeAttribute) expression).isNegative());
			return calculation;
		}
		//if the attribute is a number
		else {
			GTNumber calculation = StochasticLanguageFactory.eINSTANCE.createGTNumber();
			calculation.setNumber(((ArithmeticAttribute) expression).getStaticAttribute());
			return calculation;
		}
	}
	
	/*
	 * private method that tries to parse the expression; if not possible, it will throw
	 * an illegalArgumentException
	 */
	private static GTNumber tryToParseExpression(ArithmeticExpression expression) throws IllegalArgumentException{
			double value = GTArithmeticsCalculatorUtil.getValue(expression);
			GTNumber number = StochasticLanguageFactory.eINSTANCE.createGTNumber();
			number.setNumber(value);
			return number;
	}
}
