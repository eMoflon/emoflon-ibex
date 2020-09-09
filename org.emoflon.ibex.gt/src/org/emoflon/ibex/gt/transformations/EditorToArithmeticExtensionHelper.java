package org.emoflon.ibex.gt.transformations;

import org.emoflon.ibex.gt.editor.gT.AddExpression;
import org.emoflon.ibex.gt.editor.gT.AddOperator;
import org.emoflon.ibex.gt.editor.gT.ArithmeticAttribute;
import org.emoflon.ibex.gt.editor.gT.ArithmeticExpression;
import org.emoflon.ibex.gt.editor.gT.ArithmeticNodeAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorCountExpression;
import org.emoflon.ibex.gt.editor.gT.ExpExpression;
import org.emoflon.ibex.gt.editor.gT.MultExpression;
import org.emoflon.ibex.gt.editor.gT.OneParameterArithmetics;
import org.emoflon.ibex.gt.editor.utils.GTArithmeticsCalculatorUtil;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticAttribute;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValueLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXBinaryOperator;
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
	public static IBeXArithmeticExpression transformToGTArithmetics(final ArithmeticExpression expression) {
		//if the expression has two parameters and one operator
		if(expression instanceof AddExpression || expression instanceof MultExpression || 
				expression instanceof ExpExpression) {
			IBeXBinaryExpression calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXBinaryExpression();
			if(expression instanceof AddExpression) {
				/* tries to parse the expression tree; if it is runtime-depended
				 * (with node attributes) it will create a new GTArithmetics node
				 */
				
				try {
					return tryToParseExpression(expression);
				} catch(IllegalArgumentException e) {
					calculation.setLeft(transformToGTArithmetics(((AddExpression) expression).getLeft(), ibexPattern, transformation));
					calculation.setRight(transformToGTArithmetics(((AddExpression) expression).getRight(), ibexPattern, transformation));								
					if(((AddExpression) expression).getAddOperator() == AddOperator.ADDITION) {
						calculation.setOperator(IBeXBinaryOperator.ADDITION);
					}else {
						calculation.setOperator(IBeXBinaryOperator.SUBTRACTION);
					}
				}
			}
			if(expression instanceof MultExpression) {
				try {
					return tryToParseExpression(expression);
				} catch(IllegalArgumentException e) {
					calculation.setLeft(transformToGTArithmetics(((MultExpression) expression).getLeft(), ibexPattern, transformation));
					calculation.setRight(transformToGTArithmetics(((MultExpression) expression).getRight(), ibexPattern, transformation));								
					switch(((MultExpression) expression).getMultOperator()) {
						case DIVISION:  	calculation.setOperator(IBeXBinaryOperator.DIVISION);
											break;
						case MODULO:  		calculation.setOperator(IBeXBinaryOperator.MODULUS);
											break;
						case MULTIPLICATION:calculation.setOperator(IBeXBinaryOperator.MULTIPLICATION);
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
					calculation.setOperator(IBeXBinaryOperator.EXPONENTIATION);	
				}	
			}
			return calculation;
		}
		//if the expression has only one parameter and operand
		else if(expression instanceof OneParameterArithmetics) {
			try {
				return tryToParseExpression(expression);
			} catch(IllegalArgumentException e) {
				IBeXUnaryExpression calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXUnaryExpression();
				calculation.setOperand(transformToGTArithmetics(((OneParameterArithmetics) expression).getExpression()));
				switch(((OneParameterArithmetics) expression).getOperator()) {
					case ABSOLUTE: 	calculation.setOperator(IBeXUnaryOperator.ABSOLUTE);
									break;
					case BRACKET: 	calculation.setOperator(IBeXUnaryOperator.BRACKET);
									break;
					case COS: 		calculation.setOperator(IBeXUnaryOperator.COS);
									break;
					case SIN: 		calculation.setOperator(IBeXUnaryOperator.SIN);
									break;
					case TAN: 		calculation.setOperator(IBeXUnaryOperator.TAN);
									break;
					case EEXPONENTIAL:calculation.setOperator(IBeXUnaryOperator.EEXPONENTIAL);
									break;
					case LOGARITHMUS:calculation.setOperator(IBeXUnaryOperator.LOG);
									break;
					case NAT_LOG: 	calculation.setOperator(IBeXUnaryOperator.LG);
									break;
					case ROOT: 		calculation.setOperator(IBeXUnaryOperator.SQRT);
									break;
				}
				if(((OneParameterArithmetics) expression).isNegative()) calculation.setNegative(true);
				else calculation.setNegative(false);
				return calculation;	
			}
			
		}
		//if the parameter is an attribute from a node
		else if(expression instanceof ArithmeticNodeAttribute) {
			IBeXArithmeticAttribute calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticAttribute();
			calculation.setType(((ArithmeticNodeAttribute) expression).getNode().getType());
			calculation.setName(((ArithmeticNodeAttribute) expression).getNode().getName());
			calculation.setAttribute(((ArithmeticNodeAttribute) expression).getAttribute());
			calculation.setNegative(((ArithmeticNodeAttribute) expression).isNegative());
			return calculation;
		}
		else if(expression instanceof EditorCountExpression) {
			return transformToGTArithmetics((EditorCountExpression)expression, ibexPattern, transformation);
		}
		//if the attribute is a number
		else {
			IBeXArithmeticValueLiteral calculation = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			calculation.setValue(((ArithmeticAttribute) expression).getStaticAttribute());
			return calculation;
		}
	}
	
	/*
	 * private method that tries to parse the expression; if not possible, it will throw
	 * an illegalArgumentException
	 */
	private static IBeXArithmeticValueLiteral tryToParseExpression(ArithmeticExpression expression) throws IllegalArgumentException{
			double value = GTArithmeticsCalculatorUtil.getValue(expression);
			IBeXArithmeticValueLiteral number = IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValueLiteral();
			number.setValue(value);
			return number;
	}
	
	public static GTArithmetics transformToGTArithmetics(final EditorCountExpression expression, final IBeXPattern ibexPattern, final EditorToIBeXPatternTransformation transformation) {
		IBeXMatchCount matchCount = IBeXPatternModelFactory.eINSTANCE.createIBeXMatchCount();
//		TODO?
//		matchCount.setNegative(value);
		matchCount.setOperator(OneParameterOperator.COUNT);
		GTNumber number = SGTPatternModelFactory.eINSTANCE.createGTNumber();
		number.setNumber(0.0);
		matchCount.setValue(number);
		
		IBeXContext invContext = transformation.getPattern(expression.getInvokedPatten().getName());
		if(invContext instanceof IBeXContextAlternatives) {
//			TODO
		} else {
			IBeXContextPattern invPattern = (IBeXContextPattern)invContext;
			IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
			invocation.setPositive(true);

			Map<IBeXNode, IBeXNode> nodeMap = new HashMap<IBeXNode, IBeXNode>();
			for (final IBeXNode nodeInPattern : IBeXPatternUtils.getAllNodes((IBeXContextPattern) ibexPattern)) {
				IBeXPatternUtils.findIBeXNodeWithName(invPattern, nodeInPattern.getName())
						.ifPresent(nodeInInvokedPattern -> nodeMap.put(nodeInPattern, nodeInInvokedPattern));
			}
			invocation.setInvokedPattern(invPattern);
			EditorToIBeXPatternHelper.addNodeMapping(invocation, nodeMap);
			matchCount.setInvocation(invocation);
		}
		return matchCount;
	}
}
