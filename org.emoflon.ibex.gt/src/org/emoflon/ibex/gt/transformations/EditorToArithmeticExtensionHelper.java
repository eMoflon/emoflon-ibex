package org.emoflon.ibex.gt.transformations;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.AddExpression;
import org.emoflon.ibex.gt.editor.gT.AddOperator;
import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorIteratorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.ExpExpression;
import org.emoflon.ibex.gt.editor.gT.MinMaxExpression;
import org.emoflon.ibex.gt.editor.gT.MultExpression;
import org.emoflon.ibex.gt.editor.gT.OneParameterArithmetics;
import org.emoflon.ibex.gt.editor.utils.GTArithmeticsCalculatorUtil;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXMatchCount;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXUnaryOperator;

public class EditorToArithmeticExtensionHelper {
	
	/**
	 * transforms an ArithmeticExpression in a GTArithmetics; will try to partially 
	 * parse the expression tree
	 * 
	 * @param expression the ArithmeticExpression
	 * @return the transformed GTArithmetics
	 */
	public static IBeXArithmeticExpression transformToIBeXArithmeticExpression(final TransformationData data, final IBeXPattern ibexPattern, final ArithmeticExpression expression) {
		//if the expression has two parameters and one operator
		if(expression instanceof AddExpression || expression instanceof MultExpression || 
				expression instanceof ExpExpression || expression instanceof MinMaxExpression) {
			IBeXBinaryExpression calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXBinaryExpression();
			if(expression instanceof AddExpression addExpression) {
				/* tries to parse the expression tree; if it is runtime-depended
				 * (with node attributes) it will create a new GTArithmetics node
				 */
				
				try {
					return tryToParseExpression(addExpression);
				} catch(Exception e) {
					calculation.setLeft(transformToIBeXArithmeticExpression(data, ibexPattern, addExpression.getLeft()));
					calculation.setRight(transformToIBeXArithmeticExpression(data, ibexPattern, addExpression.getRight()));								
					if(addExpression.getAddOperator() == AddOperator.ADDITION) {
						calculation.setOperator(IBeXBinaryOperator.ADDITION);
					}else {
						calculation.setOperator(IBeXBinaryOperator.SUBTRACTION);
					}
				}
			}
			if(expression instanceof MultExpression multExpression) {
				try {
					return tryToParseExpression(multExpression);
				} catch(Exception e) {
					calculation.setLeft(transformToIBeXArithmeticExpression(data, ibexPattern, multExpression.getLeft()));
					calculation.setRight(transformToIBeXArithmeticExpression(data, ibexPattern, multExpression.getRight()));								
					switch (multExpression.getMultOperator()) {
						case DIVISION -> 		calculation.setOperator(IBeXBinaryOperator.DIVISION);
						case MODULO -> 			calculation.setOperator(IBeXBinaryOperator.MODULUS);
						case MULTIPLICATION -> 	calculation.setOperator(IBeXBinaryOperator.MULTIPLICATION);
					}	
				}
				
			}
			if(expression instanceof ExpExpression expExpression) {
				try {
					return tryToParseExpression(expExpression);
				} catch(Exception e) {
					calculation.setLeft(transformToIBeXArithmeticExpression(data, ibexPattern, expExpression.getLeft()));
					calculation.setRight(transformToIBeXArithmeticExpression(data, ibexPattern, expExpression.getRight()));								
					calculation.setOperator(IBeXBinaryOperator.EXPONENTIATION);	
				}	
			}
			if(expression instanceof MinMaxExpression minMaxExpression) {
				try {
					return tryToParseExpression(minMaxExpression);
				} catch(Exception e) {
					calculation.setLeft(transformToIBeXArithmeticExpression(data, ibexPattern, minMaxExpression.getLeft()));
					calculation.setRight(transformToIBeXArithmeticExpression(data, ibexPattern, minMaxExpression.getRight()));
					switch (minMaxExpression.getMinMaxOperator()) {
						case MAX -> calculation.setOperator(IBeXBinaryOperator.MAXIMUM);
						case MIN -> calculation.setOperator(IBeXBinaryOperator.MINIMUM);
					}
					
				}
			}
			return calculation;
		}
		//if the expression has only one parameter and operand
		else if(expression instanceof OneParameterArithmetics oneParamArithmetics) {
			try {
				return tryToParseExpression(oneParamArithmetics);
			} catch(Exception e) {
				IBeXUnaryExpression calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXUnaryExpression();
				calculation.setOperand(transformToIBeXArithmeticExpression(data, ibexPattern, oneParamArithmetics.getExpression()));
				switch (oneParamArithmetics.getOperator()) {
					case ABSOLUTE -> 		calculation.setOperator(IBeXUnaryOperator.ABSOLUTE);
					case BRACKET -> 		calculation.setOperator(IBeXUnaryOperator.BRACKET);
					case COS -> 			calculation.setOperator(IBeXUnaryOperator.COS);
					case SIN -> 			calculation.setOperator(IBeXUnaryOperator.SIN);
					case TAN -> 			calculation.setOperator(IBeXUnaryOperator.TAN);
					case EEXPONENTIAL -> 	calculation.setOperator(IBeXUnaryOperator.EEXPONENTIAL);
					case LOGARITHMUS -> 	calculation.setOperator(IBeXUnaryOperator.LOG);
					case NAT_LOG -> 		calculation.setOperator(IBeXUnaryOperator.LG);
					case ROOT -> 			calculation.setOperator(IBeXUnaryOperator.SQRT);
				}
				if(oneParamArithmetics.isNegative()) calculation.setNegative(true);
				else calculation.setNegative(false);
				return calculation;	
			}
			
		}
		//if the parameter is an attribute from a node
		else if(expression instanceof EditorAttributeExpression editorAttrExpression) {
			IBeXArithmeticAttribute calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticAttribute();
			calculation.setType(editorAttrExpression.getNode().getType());
			calculation.setName(editorAttrExpression.getNode().getName());
			calculation.setAttribute(editorAttrExpression.getAttribute());
			return calculation;
		} else if(expression instanceof EditorIteratorAttributeExpression editorIteratorAttrExpression) {
			IBeXArithmeticAttribute calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticAttribute();
			calculation.setType((EClass) editorIteratorAttrExpression.getIterator().getType().getEType());
			calculation.setName(editorIteratorAttrExpression.getIterator().getName());
			calculation.setAttribute(editorIteratorAttrExpression.getAttribute());
			return calculation;
		}
		//if the attribute is a number
		else {
			IBeXArithmeticValueLiteral calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			calculation.setValue(Double.parseDouble(((EditorLiteralExpression)expression).getValue()));
			return calculation;
		}
	}
	
	/*
	 * private method that tries to parse the expression; if not possible, it will throw
	 * an illegalArgumentException
	 */
	private static IBeXArithmeticValueLiteral tryToParseExpression(ArithmeticExpression expression) throws Exception{
			double value = GTArithmeticsCalculatorUtil.getValue(expression);
			IBeXArithmeticValueLiteral number = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			number.setValue(value);
			return number;
	}

}
